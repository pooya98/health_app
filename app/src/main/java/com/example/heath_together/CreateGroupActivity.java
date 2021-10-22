package com.example.heath_together;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.heath_together.Dialog.LoadingDialog;
import com.example.heath_together.FirebaseInit.firebaseinit;
import com.example.heath_together.Object.DTO.GroupListItem;
import com.example.heath_together.Object.DTO.UserItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class CreateGroupActivity extends AppCompatActivity {

    String TAG = "CreateGroupActivity";

    EditText EditText_GroupName;
    EditText EditText_GroupMaxLimit;
    EditText EditText_GroupIntro;
    EditText EditText_GroupTag;

    RadioButton radioButton_Public, radioButton_Private;
    // 그룹 태그

    Button Button_DoCreateGroup;
    LoadingDialog customLoadingDialog;

    String User_Uid;
    String User_Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);


        EditText_GroupName = (EditText) findViewById(R.id.CreateGroup_GroupName);
        EditText_GroupMaxLimit = (EditText) findViewById(R.id.CreateGroup_GroupMaxLimit);
        radioButton_Public = (RadioButton) findViewById(R.id.CreateGroup_Radio_public);
        radioButton_Private = (RadioButton) findViewById(R.id.CreateGroup_Radio_private);
        EditText_GroupIntro = (EditText) findViewById(R.id.CreateGroup_GroupIntro);
        EditText_GroupTag = (EditText) findViewById(R.id.CreateGroup_GroupTag);
        Button_DoCreateGroup = (Button) findViewById(R.id.CreateGroup_DoCreateGroup);

        //로딩창 객체 생성
        customLoadingDialog = new LoadingDialog(this);

        //로딩창을 투명하게
        customLoadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        customLoadingDialog.setCancelable(false);

        SharedPreferences UserInfo = getSharedPreferences("UserInfo", MODE_PRIVATE);

        if (UserInfo.getString("userUid", "") != null) {
            if (UserInfo.getString("userUid", "").length() > 0) {
                User_Uid = UserInfo.getString("userUid", "");
                User_Name = UserInfo.getString("userName", "");
            }
        }


        Button_DoCreateGroup.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

                String group_id = User_Uid + new SimpleDateFormat ( "yyyyMMddHHmmss").format(new Date());

                // <그룹 객체 생성>
                GroupListItem InsertGroupInfo = new GroupListItem();

                InsertGroupInfo.setGroupId(group_id);
                InsertGroupInfo.setLeaderName(User_Name);
                InsertGroupInfo.setLeaderUid(User_Uid);
                InsertGroupInfo.setGroupName(EditText_GroupName.getText().toString());
                InsertGroupInfo.setMem_num(1);
                InsertGroupInfo.setMem_limit(Integer.parseInt(EditText_GroupMaxLimit.getText().toString()));
                InsertGroupInfo.setGroupIntro(EditText_GroupIntro.getText().toString());
                InsertGroupInfo.setGroupTag(EditText_GroupTag.getText().toString());

                if (radioButton_Public.isChecked()) {
                    InsertGroupInfo.setGroupOpen(true);
                } else {
                    InsertGroupInfo.setGroupOpen(false);
                }
                // </그룹 객체 생성 완료>

                // <멤버 리스트 생성>
                ArrayList<String> member_list = new ArrayList<String>();
                member_list.add(User_Uid);

                // <트랜잭션 시작>
                // FirebaseFirestore db = FirebaseFirestore.getInstance();
                final DocumentReference sfDocRef = firebaseinit.firebaseFirestore.collection("memberGroups").document(User_Uid);

                firebaseinit.firebaseFirestore.runTransaction(new Transaction.Function<Void>() {
                    @Override
                    public Void apply(Transaction transaction) throws FirebaseFirestoreException {

                        Map<String, Object> docData = new HashMap<>();
                        docData.put("memberlist", Arrays.asList(User_Uid));

                        firebaseinit.firebaseFirestore.collection("groups").document(group_id).set(InsertGroupInfo);
                        firebaseinit.firebaseFirestore.collection("groupMembers").document(group_id).set(docData);

                        DocumentSnapshot snapshot = transaction.get(sfDocRef);

                        ArrayList<String> newArray = (ArrayList<String>)snapshot.getData().get("grouplist");

                        if(newArray == null){
                            Map<String, Object> newMap = snapshot.getData();
                            newArray = new ArrayList<String>();
                            newArray.add(group_id);
                            newMap.put("grouplist", newArray);
                            transaction.update(sfDocRef, newMap);
                        }else{
                            newArray.add(group_id);
                            transaction.update(sfDocRef, "grouplist", newArray);
                        }

                        // Success
                        return null;
                    }
                }).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Transaction success!");
                        Toast.makeText(CreateGroupActivity.this, "Transaction success!", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Transaction failure.", e);
                        Toast.makeText(CreateGroupActivity.this, "Transaction failure!", Toast.LENGTH_SHORT).show();
                    }
                });





                // <트랜잭션 종료>


            }
        });
    }
}
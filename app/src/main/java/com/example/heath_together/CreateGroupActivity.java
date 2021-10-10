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
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateGroupActivity extends AppCompatActivity {

    String TAG = "CreateGroupActivity";

    EditText EditText_GroupName;
    EditText EditText_GroupMaxLimit;
    EditText EditText_GroupIntro;

    RadioButton radioButton_Public, radioButton_Private;
    // 그룹 태그

    Button Button_DoCreateGroup;
    LoadingDialog customLoadingDialog;

    String User_Uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);


        EditText_GroupName = (EditText) findViewById(R.id.CreateGroup_GroupName);
        EditText_GroupMaxLimit = (EditText) findViewById(R.id.CreateGroup_GroupMaxLimit);
        radioButton_Public = (RadioButton) findViewById(R.id.CreateGroup_Radio_public);
        radioButton_Private = (RadioButton) findViewById(R.id.CreateGroup_Radio_private);
        EditText_GroupIntro = (EditText) findViewById(R.id.CreateGroup_GroupIntro);
        Button_DoCreateGroup = (Button) findViewById(R.id.CreateGroup_DoCreateGroup);

        //로딩창 객체 생성
        customLoadingDialog = new LoadingDialog(this);

        //로딩창을 투명하게
        customLoadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        customLoadingDialog.setCancelable(false);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        SharedPreferences UserInfo = getSharedPreferences("UserInfo", MODE_PRIVATE);

        if (UserInfo.getString("userUid", "") != null) {
            if (UserInfo.getString("userUid", "").length() > 0) {
                User_Uid = UserInfo.getString("userUid", "");
            }
        }


        Button_DoCreateGroup.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

                String groupName = EditText_GroupName.getText().toString();
                int groupMaxLimit = Integer.parseInt(EditText_GroupMaxLimit.getText().toString());
                String GroupAccess;

                if (radioButton_Public.isChecked()) {
                    GroupAccess = "Public";
                } else {
                    GroupAccess = "Private";
                }
                String groupIntro = EditText_GroupIntro.getText().toString();


                FirebaseFirestore db = FirebaseFirestore.getInstance();

                GroupListItem input_GroupItem = new GroupListItem(groupName, "웨이트", "강승우", User_Uid, 1, groupMaxLimit);
                db.collection("groups").document(new SimpleDateFormat("yyyy-MM-dd+HH:mm:ss").format(new Date()) + user.getUid()).set(input_GroupItem)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                customLoadingDialog.dismiss();
                                Log.d(TAG, "DocumentSnapshot successfully written!");
                                Log.d(TAG, "createUserWithEmail:success");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                customLoadingDialog.dismiss();
                                user.delete()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Log.d(TAG, "User account deleted.");
                                                    Toast.makeText(CreateGroupActivity.this, "정보 등록 오류",
                                                            Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }
                        });

                DocumentReference washingtonRef = db.collection("usergroups").document(User_Uid);
                Log.d("찾았냐?", washingtonRef.toString());
            }
        });
    }
}
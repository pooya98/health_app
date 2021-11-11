package com.example.heath_together;



import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.heath_together.Adapter.SearchGAdapter;
import com.example.heath_together.FirebaseInit.firebaseinit;
import com.example.heath_together.Object.DTO.GroupListItem;
import com.example.heath_together.R;
import com.example.heath_together.UserInfo.UserInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupJoin extends Fragment {


    String TAG;
    private String groupName,leaderName,groupTag,groupId;
    private TextView groupN, groupT,leaderN,leaderN2;

    private Button joinButton;

    private String user_id = UserInfo.user_Id;

    public GroupJoin(String groupName,String leaderName,String groupTag,String groupId){
        this.leaderName = leaderName;
        this.groupName = groupName;
        this.groupTag = groupTag;
        this.groupId = groupId;
    }



    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.group_join_real, container, false);












        groupN = view.findViewById(R.id.group_name);
        leaderN = view.findViewById(R.id.leader_name);
        leaderN2 = view.findViewById(R.id.leader_name2);
        groupT = view.findViewById(R.id.group_tag);

        groupN.setText(groupName);
        leaderN2.setText(leaderName);
        leaderN.setText(leaderName);
        groupT.setText(groupTag);

        joinButton = view.findViewById(R.id.groupJoinButton);

        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final DocumentReference sfDocRef = firebaseinit.firebaseFirestore.collection("memberGroups").document(UserInfo.user_Id);
                final DocumentReference sfDocRef2 = firebaseinit.firebaseFirestore.collection("groupMembers").document(groupId);
                final DocumentReference countDocRef = firebaseinit.firebaseFirestore.collection("groups").document(groupId);

                firebaseinit.firebaseFirestore.runTransaction(new Transaction.Function<Void>() {
                    @Override
                    public Void apply(Transaction transaction) throws FirebaseFirestoreException {




                        DocumentSnapshot snapshot = transaction.get(sfDocRef);
                        DocumentSnapshot snapshot2 = transaction.get(sfDocRef2);



                        ArrayList<String> newArray = (ArrayList<String>)snapshot.getData().get("grouplist");
                        ArrayList<String> newArray2 = (ArrayList<String>)snapshot2.getData().get("memberlist");


                        Map<String, Object> docData = new HashMap<>();


                        newArray.add(groupId);
                        newArray2.add(UserInfo.user_Id);
                        int newCount = newArray2.size();
                        docData.put("mem_num",newCount);
                        countDocRef.update(docData);// 인원수 조정.

                        transaction.update(sfDocRef,"grouplist",newArray);
                        transaction.update(sfDocRef2,"memberlist",newArray2);



                        return null;
                    }
                }).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Transaction success!");

                        Toast.makeText(v.getContext(), "가입이 완료되었습니다" ,
                                Toast.LENGTH_SHORT).show();
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        fragmentManager.beginTransaction().remove(GroupJoin.this).commit();
                        fragmentManager.popBackStack();


//                        Toast.makeText(CreateGroupActivity.this, "Transaction success!", Toast.LENGTH_SHORT).show();
//                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Transaction failure.", e);
//                        Toast.makeText(CreateGroupActivity.this, "Tr  ansaction failure!", Toast.LENGTH_SHORT).show();


                    }
                });

                // groupMebers 에 멤버 추가.






            }
        });

        return view;
    }

}

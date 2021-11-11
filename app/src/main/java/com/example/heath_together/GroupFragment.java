package com.example.heath_together;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.heath_together.Adapter.MemberItemAdapter;
import com.example.heath_together.Adapter.RecyclerViewItemAdapter;
import com.example.heath_together.FirebaseInit.firebaseinit;
import com.example.heath_together.Object.DTO.AccountListItem;
import com.example.heath_together.Object.DTO.GroupListItem;
import com.example.heath_together.Object.DTO.UserItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class GroupFragment  extends Fragment {
    private View view;

    private TextView GroupName;
    private TextView BoxGroupName;
    private TextView BoxGroupLeaderName;
    private TextView BoxGroupMemberInfo;
    private TextView BoxGroupTag;

    String member_info;
    String group_id;
    String group_name;
    String leader_name;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.group_fragment, container, false);

        if (getArguments() != null) {
            group_id = getArguments().getString("GroupId");
            group_name = getArguments().getString("GroupName");
            leader_name = getArguments().getString("LeaderName");
            member_info = getArguments().getString("MemberInfo");
            System.out.println("봐라봐라" + group_id);

        }else{
            System.out.println("잃어버렸어요 ㅠㅠ");
        }

        BoxGroupMemberInfo = view.findViewById(R.id.GroupFragment_BoxGroupMemInfo);
        BoxGroupLeaderName = view.findViewById(R.id.GroupFragment_BoxGroupLeaderName);
        BoxGroupName = view.findViewById(R.id.GroupFragment_BoxGroupName);

        BoxGroupMemberInfo.setText(member_info);
        BoxGroupLeaderName.setText(leader_name);
        BoxGroupName.setText(group_name);



        // FirebaseFirestore db = FirebaseFirestore.getInstance();

        ArrayList<AccountListItem> list = new ArrayList<>();


        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        RecyclerView recyclerView = view.findViewById(R.id.groupFragmnet_recycler) ;

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),3);
        recyclerView.setLayoutManager(gridLayoutManager) ;
        MemberItemAdapter adapter = new MemberItemAdapter(list) ;


        DocumentReference docRef = firebaseinit.firebaseFirestore.collection("groupMembers").document(group_id);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();

                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());

                        ArrayList<String> memberlist = (ArrayList<String>)document.get("memberlist");
                        DocumentReference docRef_inner;
                        for (String memberId : memberlist){
                            docRef_inner = firebaseinit.firebaseFirestore.collection("users").document(memberId);
                            docRef_inner.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    AccountListItem item = documentSnapshot.toObject(AccountListItem.class);
                                    list.add(item);
                                    recyclerView.setAdapter(adapter);
                                }
                            });
                        }
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

        return view;
    }
}

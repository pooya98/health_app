package com.example.heath_together;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.heath_together.Adapter.ListItemAdapter;
import com.example.heath_together.FirebaseInit.firebaseinit;
import com.example.heath_together.Object.DTO.GroupListItem;
import com.example.heath_together.Object.DTO.UserItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.MODE_PRIVATE;

public class Main2 extends Fragment {

    private static String TAG = "Main2";

    private View view;
    private Context context;
    MainActivity mainActivity;

    UserItem userItem;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity)getActivity();
        Log.d("---onAttach---", "실행");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mainActivity = null;
        Log.d("---onDetach---", "실행");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("---onStart---", "실행");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main2, container, false);
        context = container.getContext();

        String User_Uid ="";
        String User_Name ="";

        SharedPreferences UserInfo = getActivity().getSharedPreferences("UserInfo", MODE_PRIVATE);

        if (UserInfo.getString("userUid", "") != null) {
            if (UserInfo.getString("userUid", "").length() > 0) {
                User_Uid = UserInfo.getString("userUid", "");
                User_Name = UserInfo.getString("UserName", "");
            }
        }

        ListView listView = (ListView)view.findViewById(R.id.listView_Group);
        ListItemAdapter adapter = new ListItemAdapter();
        listView.setAdapter(adapter);
        listView.setFocusable(false);

        ImageButton ImageButton_CreateGroup = (ImageButton)view.findViewById(R.id.Main2_CreateGroup);

        //FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //FirebaseFirestore db = FirebaseFirestore.getInstance();

        //DocumentReference docRef = db.collection("memberGroups").document(User_Uid);
        DocumentReference docRef = firebaseinit.firebaseFirestore.collection("memberGroups").document(User_Uid);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();

                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());

                        ArrayList<String> grouplst = (ArrayList<String>)document.get("grouplist");
                        DocumentReference docRef_inner;
                        for (String groupId : grouplst){
                            docRef_inner = firebaseinit.firebaseFirestore.collection("groups").document(groupId);
                            docRef_inner.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    GroupListItem item = documentSnapshot.toObject(GroupListItem.class);
                                    adapter.addItem(item);
                                    adapter.notifyDataSetChanged();
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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Bundle bundle = new Bundle();
                bundle.putString("GroupId", adapter.getItem(position).getGroupId());

                Fragment groupFragment = new GroupFragment();
                groupFragment.setArguments(bundle);
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_frame, groupFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        ImageButton_CreateGroup.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CreateGroupActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}

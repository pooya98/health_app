package com.example.heath_together;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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

import com.bumptech.glide.Glide;
import com.example.heath_together.Adapter.ListItemAdapter;
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

import de.hdodenhof.circleimageview.CircleImageView;

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
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mainActivity = null;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main2, container, false);
        context = container.getContext();

        ListView listView = (ListView)view.findViewById(R.id.listView_Group);
        ListItemAdapter adapter = new ListItemAdapter();

        TextView TextView_Name = (TextView)view.findViewById(R.id.Main2_ProfileName);
        TextView TextView_Email = (TextView)view.findViewById(R.id.Main2_ProfileEmail);
        TextView TextView_UID = (TextView)view.findViewById(R.id.Main2_ProfileUID);

        ImageButton ImageButton_Setting = (ImageButton)view.findViewById(R.id.Main2_Setting);


        CircleImageView circleImageView_ProfilePhoto = (CircleImageView)view.findViewById(R.id.Main2_ProfilePhoto);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        if(user != null && db != null) {
            DocumentReference docRef = db.collection("users").document(user.getUid());

            docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    UserItem userItem = documentSnapshot.toObject(UserItem.class);

                    TextView_Name.setText(userItem.getUserName());
                    TextView_Email.setText(userItem.getUserEmail());
                    TextView_UID.setText(userItem.getUid());
                }
            });
        }

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        storageRef.child("userProfilePhoto/"+user.getUid()+".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.e("확","인");
                Glide.with(getActivity().getApplicationContext())
                        .load(uri)
                        .into(circleImageView_ProfilePhoto);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("노","확인");
            }
        });


        adapter.addItem(new GroupListItem("옥곡크루", "웨이트", "강승우", 4, 25));
        adapter.addItem(new GroupListItem("머슐랭", "다이어트", "금윤수", 100, 100));
        adapter.addItem(new GroupListItem("머슐랭", "다이어트", "금윤수", 100, 100));
        adapter.addItem(new GroupListItem("머슐랭", "다이어트", "금윤수", 100, 100));
        adapter.addItem(new GroupListItem("머슐랭", "다이어트", "금윤수", 100, 100));
        adapter.addItem(new GroupListItem("머슐랭", "다이어트", "금윤수", 100, 100));
        adapter.addItem(new GroupListItem("머슐랭", "다이어트", "금윤수", 100, 100));

        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                mainActivity.onChangeFragment(0);
            }
        });

        ImageButton_Setting.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ProfileSettingActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}

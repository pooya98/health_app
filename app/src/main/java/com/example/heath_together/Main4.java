package com.example.heath_together;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.example.heath_together.FirebaseInit.firebaseinit;
import com.example.heath_together.Object.DTO.ExerciseReadyListItem;
import com.example.heath_together.UserInfo.UserInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Main4 extends Fragment implements View.OnClickListener {

    private View view;

    CircleImageView profile_Image;
    TextView profile_Name;

    RadioButton radioButton_Calendar;
    RadioButton radioButton_List;
    RadioButton radioButton_Exercise;
    RadioButton radioButton_Static;
    String profileId;
    String profileUserId;
    public Main4(String userId){

        this.profileUserId= userId;

    }
    public Main4(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main4, container, false);

        profile_Image = view.findViewById(R.id.main4_profile);
        profile_Name = view.findViewById(R.id.main4_profileName);

        radioButton_Calendar = (RadioButton)view.findViewById(R.id.Main4_Radio_Calendar);
        radioButton_List = (RadioButton)view.findViewById(R.id.Main4_Radio_List);
        radioButton_Exercise = (RadioButton)view.findViewById(R.id.Main4_Radio_Exercise);
        radioButton_Static = (RadioButton)view.findViewById(R.id.Main4_Radio_Static);

        radioButton_Calendar.setOnClickListener(this);
        radioButton_List.setOnClickListener(this);
        radioButton_Exercise.setOnClickListener(this);
        radioButton_Static.setOnClickListener(this);

        if(profileUserId==null){
            profileId=UserInfo.user_Id;
        }else{                                                 //내 계정, 상대 계정 프로필 구분.
            profileId=profileUserId;
        }

        setChildFragment(new Main4_1(profileUserId));

        //프로필 사진 ,이름 가져오기
        DocumentReference docRef =  firebaseinit.firebaseFirestore.collection("users").document(profileId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                Log.d("Task", String.valueOf(task));
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
//                    Log.d("document", String.valueOf(document));
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        Map<String, Object> result = document.getData();
                       String userProfileUri = (String)result.get("profileUri");
                       String userName = (String)result.get("userName");

                        if (userProfileUri!=null) {
                            Glide.with(view).load(userProfileUri).into(profile_Image);
                        }else{
                            Glide.with(view).load(R.drawable.profile).into(profile_Image);
                        }

                       profile_Name.setText(userName);


//                        System.out.println(">>>>" + list);
//                        for(Map<String, Object> i : list){
//                            System.out.println(">>>>" + i.get("name"));
//
//                            adapter_stageExercise.addItem(new ExerciseReadyListItem((String)i.get("id"), (String)i.get("name"), (boolean)i.get("flag_count"), (boolean)i.get("flag_time"), (boolean)i.get("flag_weight") ));
//
//                        }
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

    @Override
    public void onClick(View view) {
        int _id = view.getId();

        Fragment fg;

        switch (_id){
            case R.id.Main4_Radio_Calendar:
                fg = new Main4_1(profileUserId);
                setChildFragment(fg);
                break;
            case R.id.Main4_Radio_List:
                fg = new Main4_2(profileUserId);
                setChildFragment(fg);
                break;
            case R.id.Main4_Radio_Exercise:
                fg = new Main4_3(profileUserId);
                setChildFragment(fg);
                break;
            case R.id.Main4_Radio_Static:
                fg = new Main4_4(profileUserId);
                setChildFragment(fg);
                break;
        }
    }

    private void setChildFragment(Fragment child) {
        FragmentTransaction childFt = getChildFragmentManager().beginTransaction();

        if (!child.isAdded()) {
            childFt.replace(R.id.main4_frame, child);
            childFt.addToBackStack(null);
            childFt.commit();
        }
    }
}

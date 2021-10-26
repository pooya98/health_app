package com.example.heath_together;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.heath_together.Adapter.ExerciseCompleteItemAdapter;
import com.example.heath_together.Adapter.ExerciseReadyItemAdapter;
import com.example.heath_together.Adapter.ListItemAdapter;
import com.example.heath_together.FirebaseInit.firebaseinit;
import com.example.heath_together.Object.DTO.ExerciseCompleteListItem;
import com.example.heath_together.Object.DTO.ExerciseReadyListItem;
import com.example.heath_together.Object.DTO.GroupListItem;
import com.example.heath_together.Object.DTO.HealthItem;
import com.example.heath_together.UserInfo.UserInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firestore.v1.ArrayValueOrBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main1 extends Fragment {

    private View view;
    ExerciseReadyItemAdapter adapter_stageExercise;

    @Override
    public void onStart() {
        super.onStart();

        DocumentReference docRef = firebaseinit.firebaseFirestore.collection("stageExercise").document(UserInfo.user_Id);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        Map<String, Object> result = document.getData();
                        ArrayList<Map<String, Object>> list = (ArrayList<Map<String, Object>>)result.get("stagedExerciseList");

                        System.out.println(">>>>" + list);
                        for(Map<String, Object> i : list){
                            System.out.println(">>>>" + i.get("name"));
                            adapter_stageExercise.addItem(new ExerciseReadyListItem((String)i.get("name"), (boolean)i.get("flag_count"), (boolean)i.get("flag_time"), (boolean)i.get("flag_weight") ));
                            adapter_stageExercise.notifyDataSetChanged();
                        }

                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main1, container, false);

        Button Button_Exercise_StartEnd = (Button)view.findViewById(R.id.Main1_StartEnd_Button);
        Button Button_Add_Exercise = (Button)view.findViewById(R.id.Main1_Button_AddExercise);

        LinearLayout LinearLayout_StageExercise = (LinearLayout)view.findViewById(R.id.Main1_LinearLayout_StageExercise);

        ListView listView_CompleteExercise = (ListView)view.findViewById(R.id.Main1_ListView_CompleteExercise);
        ListView listView_StageExercise = (ListView)view.findViewById(R.id.Main1_ListView_StageExercise);
        ExerciseCompleteItemAdapter adapter_completeExercise = new ExerciseCompleteItemAdapter();
        adapter_stageExercise = new ExerciseReadyItemAdapter();

//        adapter_completeExercise.addItem(new ExerciseCompleteListItem("벤치프레스","3set"));
//        adapter_completeExercise.addItem(new ExerciseCompleteListItem("데드리프트", "3set"));
//        adapter_completeExercise.addItem(new ExerciseCompleteListItem("스쿼트", "3set"));
//        adapter_completeExercise.addItem(new ExerciseCompleteListItem("런지", "3set"));
        listView_CompleteExercise.setAdapter(adapter_completeExercise);


//        adapter_stageExercise.addItem(new ExerciseReadyListItem("벤치프레스"));
//        adapter_stageExercise.addItem(new ExerciseReadyListItem("데드리프트"));
//        adapter_stageExercise.addItem(new ExerciseReadyListItem("스쿼트"));
//        adapter_stageExercise.addItem(new ExerciseReadyListItem("덤벨로우"));
//        adapter_stageExercise.addItem(new ExerciseReadyListItem("런지"));
//        adapter_stageExercise.addItem(new ExerciseReadyListItem("삽겹살"));
//        adapter_stageExercise.addItem(new ExerciseReadyListItem("소주"));
//        adapter_stageExercise.addItem(new ExerciseReadyListItem("라면"));
        listView_StageExercise.setAdapter(adapter_stageExercise);


        Button_Exercise_StartEnd.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(Button_Exercise_StartEnd.getText().toString().equals("운동 시작")){
                    Button_Exercise_StartEnd.setText("운동 종료");
                    LinearLayout_StageExercise.setVisibility(View.VISIBLE);
                    Button_Add_Exercise.setVisibility(View.VISIBLE);
                }
                else{
                    Button_Exercise_StartEnd.setText("운동 시작");
                    LinearLayout_StageExercise.setVisibility(View.GONE);
                    Button_Add_Exercise.setVisibility(View.GONE);
                }
            }
        });

        Button_Add_Exercise.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                Fragment fragment_Main1_1 = new Main1_1();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_frame, fragment_Main1_1)
                        .addToBackStack(null)
                        .commit();
            }
        });
        return view;
    }
}

package com.example.heath_together;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.heath_together.Adapter.HealthItemListMyPageAdapter;
import com.example.heath_together.Adapter.HealthItemMyPageAdapter;
import com.example.heath_together.FirebaseInit.firebaseinit;
import com.example.heath_together.Object.DTO.ExerciseReadyListItem;
import com.example.heath_together.Object.DTO.HealthItem;
import com.example.heath_together.UserInfo.UserInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class main4_2_CreateList extends Fragment {

    private View view;
    private FloatingActionButton exercisePlusButton;
    private EditText createList_Name;
    private Button CreateList_DoProfileList;
    HealthItemListMyPageAdapter healthItemListMyPageAdapter;


    ArrayList<HealthItem> static_list = new ArrayList<HealthItem>();

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
    }

    @Override
    public void onStart(){
        super.onStart();
//        loadReadyExercise();
        DocumentReference docRef = firebaseinit.firebaseFirestore.collection("myPageReadyExerciseList").document(UserInfo.user_Id);
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
                        ArrayList<Map<String, Object>> list = (ArrayList<Map<String, Object>>)result.get("readyExerciseList");
                        Log.d(TAG, "test today list");
                        if(list != null){
                            System.out.println(">>>>" + list);
                            for(Map<String, Object> i : list){
                                System.out.println(">>>>" + i.get("name"));

                                healthItemListMyPageAdapter.addItem(new HealthItem((String)i.get("id"), (String)i.get("name"), (String)i.get("type"), (boolean)i.get("flag_count"), (boolean)i.get("flag_time"), (boolean)i.get("flag_weight") ));

                            }
                            healthItemListMyPageAdapter.notifyDataSetChanged();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "main4_2 onCreateView");
        view = inflater.inflate(R.layout.main4_2_create_list, container, false);


        CreateList_DoProfileList = view.findViewById(R.id.CreateList_DoProfileList);
        exercisePlusButton = view.findViewById(R.id.exercise_plus_button);
        ListView listView = view.findViewById(R.id.CreatList_ListView);

        healthItemListMyPageAdapter = new HealthItemListMyPageAdapter();
        listView.setAdapter(healthItemListMyPageAdapter);

        CreateList_DoProfileList.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                
                clear_fb();

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().remove(main4_2_CreateList.this).commit();
                fragmentManager.popBackStack();
            }
        });


        exercisePlusButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                Fragment fragment_Main4_2_CreateListList = new main4_2_CreateListList();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_frame, fragment_Main4_2_CreateListList)
                        .addToBackStack(null)
                        .commit();
            }
        });


        return view;
    }

    private void clear_fb(){
        firebaseinit.firebaseFirestore.collection("myPageReadyExerciseList").document(UserInfo.user_Id)
                .update("readyExerciseList", FieldValue.delete())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "delete success");
                    }
                });
    }
}

package com.example.heath_together;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.heath_together.Adapter.ExerciseReadyItemAdapter;
import com.example.heath_together.Adapter.HealthItemAdapter;
import com.example.heath_together.Adapter.HealthItemListMyPageAdapter;
import com.example.heath_together.Adapter.HealthItemMyPageAdapter;
import com.example.heath_together.Adapter.HealthListItemAdapter;
import com.example.heath_together.FirebaseInit.firebaseinit;
import com.example.heath_together.Object.DTO.HealthItem;
import com.example.heath_together.Object.DTO.HealthListItem;
import com.example.heath_together.UserInfo.UserInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class main4_2_CreateListList extends Fragment {

    private View view;
    public static Button countButton;
    private Context context;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private boolean isOpen= false;
    Animation cOpen, cClose,rotateForward, rotateBackward;

    private HealthItemMyPageAdapter healthItemMypageAdapter;
    private HealthItemListMyPageAdapter healthItemListMyPageAdapter;
    private HealthListItemAdapter healthListItemAdapter;

    private FloatingActionButton categoriButton ;
    private FloatingActionButton chestButton ;
    private FloatingActionButton shoulderButton ;
    private FloatingActionButton legButton ;
    private FloatingActionButton backButton ;
    private FloatingActionButton guitarButton;
    ExerciseReadyItemAdapter adapter_stageExercise;

    private HealthItemListMyPageAdapter myPageAdapter;

    ArrayList<HealthItem> add_list;



    List<HealthItem> healthItemList = new ArrayList<HealthItem>();
    List<HealthListItem> healthListList = new ArrayList<HealthListItem>();

    private ClickCallbackListener callbackListener = new ClickCallbackListener() {
        @Override

        public void callBack(ArrayList<HealthItem> list) {
            add_list = list;
        }
    };

    @Override
    public void onStart(){
        super.onStart();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "main4_2 onCreateView");
        view = inflater.inflate(R.layout.main4_2_create_list_list, container, false);

        isOpen=false;
        healthItemList.clear();
        healthListList.clear();
        fillHealthItemList("chest");

        countButton = view.findViewById(R.id.countButton);

        categoriButton = view.findViewById(R.id.changeCategori2);
        chestButton = view.findViewById(R.id.changeChest);
        shoulderButton = view.findViewById(R.id.changeShoulder);
        backButton = view.findViewById(R.id.changeBack);
        legButton = view.findViewById(R.id.changeLeg);
        guitarButton = view.findViewById(R.id.changeGuitar);

        cOpen = AnimationUtils.loadAnimation(getContext(),R.anim.c_open);
        cClose = AnimationUtils.loadAnimation(getContext(),R.anim.c_close);

        rotateBackward = AnimationUtils.loadAnimation(getContext(),R.anim.rotate_backward);
        rotateForward = AnimationUtils.loadAnimation(getContext(),R.anim.rotate_forward);
        setUpHealthReCyclerView();

        add_list = new ArrayList<HealthItem>();


        shoulderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                healthItemList.clear();
                fillHealthItemList("shoulder");
                setUpHealthReCyclerView();
            }
        });
        legButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                healthItemList.clear();
                fillHealthItemList("leg");
                setUpHealthReCyclerView();
            }
        });
        chestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                healthItemList.clear();
                fillHealthItemList("chest");
                setUpHealthReCyclerView();
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                healthItemList.clear();
                fillHealthItemList("back");
                setUpHealthReCyclerView();
            }
        });
        guitarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                healthItemList.clear();
                fillHealthItemList("guitar");
                setUpHealthReCyclerView();
            }
        });

        countButton = view.findViewById(R.id.countButton);

        countButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(add_list.size() != 0){
                    SavedReadyExercise();
                }

//
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                fragmentManager.beginTransaction().remove(main4_2_CreateListList.this).commit();
//                fragmentManager.popBackStack();
            }
        });

        return view;
    }

    private void setUpHealthReCyclerView(){

        countButton.setText("리스트에 담을 종목을 고르세요!");
        healthItemMypageAdapter.mSelectedItems =new SparseBooleanArray(0);
        healthItemMypageAdapter = new HealthItemMyPageAdapter(healthItemList);
        healthItemMypageAdapter.setCallbackListener(callbackListener);

        recyclerView = view.findViewById(R.id.health_list) ;
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager) ;
        recyclerView.setAdapter(healthItemMypageAdapter) ;

        categoriButton.setVisibility(View.VISIBLE);
        categoriButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                animateCategori();

            }
        });

    }


    private void animateCategori(){
        if(isOpen){

            categoriButton.startAnimation(rotateForward);
            chestButton.startAnimation(cClose);
            shoulderButton.startAnimation(cClose);
            legButton.startAnimation(cClose);
            guitarButton.startAnimation(cClose);
            backButton.startAnimation(cClose);

            backButton.setClickable(false);
            chestButton.setClickable(false);
            shoulderButton.setClickable(false);
            guitarButton.setClickable(false);
            legButton.setClickable(false);
            isOpen=false;
        }
        else{
            categoriButton.startAnimation(rotateBackward);
            chestButton.startAnimation(cOpen);
            shoulderButton.startAnimation(cOpen);
            legButton.startAnimation(cOpen);
            guitarButton.startAnimation(cOpen);
            backButton.startAnimation(cOpen);

            backButton.setClickable(true);
            chestButton.setClickable(true);
            shoulderButton.setClickable(true);
            guitarButton.setClickable(true);
            legButton.setClickable(true);
            isOpen=true;
        }
    }

    private void fillHealthItemList(String type){
        firebaseinit.firebaseFirestore.collection("exercise")
                .whereEqualTo("type", type)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());

                                Map<String, Object> received_data = document.getData();

                                HealthItem new_item = new HealthItem(
                                        (String) received_data.get("id"),
                                        (String) received_data.get("name"),
                                        (String) received_data.get("type"),
                                        (boolean) received_data.get("flag_count"),
                                        (boolean) received_data.get("flag_time"),
                                        (boolean) received_data.get("flag_weight")
                                );
                                System.out.println(">>>>>2" + document.getId());
                                healthItemList.add(new_item);
                                healthItemMypageAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private void SavedReadyExercise(){

        Map<String, Object> docData = new HashMap<>();



        DocumentReference sfDocRef = firebaseinit.firebaseFirestore.collection("myPageReadyExerciseList").document(UserInfo.user_Id);


        sfDocRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    Log.d(TAG, "task result : " + document.getData());

                    if (document.exists()){
                        Map<String, Object> result = document.getData();
                        ArrayList<Map<String, Object>> list = (ArrayList<Map<String, Object>>)result.get("readyExerciseList");
                        if (list != null) {
                            for(Map<String, Object> i : list) {
                                boolean overlap = true;
                                for (HealthItem item : add_list){
                                    String name = item.getName();
                                    if(name.equals(i.get("name"))){
                                        overlap = false;
                                    }
                                }
                                if (overlap){
                                    HealthItem new_item = new HealthItem(
                                            (String) i.get("id"),
                                            (String) i.get("name"),
                                            (String) i.get("type"),
                                            (boolean) i.get("flag_count"),
                                            (boolean) i.get("flag_time"),
                                            (boolean) i.get("flag_weight")
                                    );
                                    System.out.println(">>>>>2" + document.getId());

                                    add_list.add(new_item);}
                            }
                        }

                        docData.put("readyExerciseList", add_list);
                        firebaseinit.firebaseFirestore.collection("myPageReadyExerciseList").document(UserInfo.user_Id)
                                .set(docData)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "DocumentSnapshot successfully written!");
                                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                        fragmentManager.beginTransaction().remove(main4_2_CreateListList.this).commit();
                                        fragmentManager.popBackStack();

                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w(TAG, "Error writing document", e);
                                    }
                                });
                    } else {
                        Log.d(TAG, "No such document");
                        docData.put("readyExerciseList", add_list);

                        firebaseinit.firebaseFirestore.collection("myPageReadyExerciseList").document(UserInfo.user_Id)
                                .set(docData)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "DocumentSnapshot successfully written!");
                                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                        fragmentManager.beginTransaction().remove(main4_2_CreateListList.this).commit();
                                        fragmentManager.popBackStack();

                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w(TAG, "Error writing document", e);
                                    }
                                });
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }
}

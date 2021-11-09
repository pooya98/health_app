package com.example.heath_together.Adapter;

import static android.content.ContentValues.TAG;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.FragmentManager;

import com.example.heath_together.FirebaseInit.firebaseinit;
import com.example.heath_together.Main1;
import com.example.heath_together.Main1_1;
import com.example.heath_together.Object.DTO.ExerciseCompleteListItem;
import com.example.heath_together.Object.DTO.ExerciseReadyListItem;
import com.example.heath_together.Object.DTO.ExerciseRecord;
import com.example.heath_together.Object.DTO.HealthItem;
import com.example.heath_together.R;
import com.example.heath_together.UserInfo.UserInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ExerciseReadyItemAdapter extends BaseAdapter {

    Dialog dialog;
    ArrayList<HealthItem> add_list = new ArrayList<HealthItem>();
    ExerciseCompleteItemAdapter exercise_complete_items;


    //add static
    ArrayList<ExerciseReadyListItem> stageExercise_items = new ArrayList<ExerciseReadyListItem>();
    Context context;

    public ExerciseReadyItemAdapter(ExerciseCompleteItemAdapter adapter_completeExercise) {
        exercise_complete_items = adapter_completeExercise;
    }

    @Override
    public int getCount() {
        return stageExercise_items.size();
    }

    @Override
    public Object getItem(int position) {
        return stageExercise_items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        context = viewGroup.getContext();
        ExerciseReadyListItem exerciseListItem = stageExercise_items.get(position);

        if(view == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.exercise_ready_item, viewGroup, false);
        }

        TextView TextView_ExerciseNmae = view.findViewById(R.id.ExerciseReadyListItem_ExerciseName);
        ImageButton ImageButton_SetButton = view.findViewById(R.id.ExerciseReadyListItem_setButton);
        ImageButton ImageButton_AddButton = view.findViewById(R.id.ExerciseReadyListItem_addButton);

        TextView_ExerciseNmae.setText(exerciseListItem.getExerciseName());

        ImageButton_SetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupForSet(v, position);
            }
        });
        ImageButton_AddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupForAdd(v, exerciseListItem);
            }
        });

        return view;
    }

    public void addItem(ExerciseReadyListItem item) {
        stageExercise_items.add(item);
    }

    private void showPopupForSet(View v, final int position) {
        PopupMenu popup = new PopupMenu(v.getContext(), v);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                DocumentReference docRef = firebaseinit.firebaseFirestore.collection("stageExercise").document(UserInfo .user_Id);
                Map<String, Object> docData = new HashMap<>();
                switch (menuItem.getItemId()) {
                    case R.id.Exercise_Ready_Action_ShowInfo:

                        dialog = new Dialog(v.getContext());       // Dialog 초기화
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
                        dialog.setContentView(R.layout.exercise_info_dialog);

                        showDialog01();

                        return true;
                    case R.id.Exercise_Ready_Action_Delete:
                        Toast.makeText(v.getContext(), "삭제" + position,
                                Toast.LENGTH_SHORT).show();
                        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if(task.isSuccessful()){
                                    DocumentSnapshot document = task.getResult();

                                    if (document.exists()){
                                        Map<String, Object> result = document.getData();
                                        ArrayList<Map<String, Object>> list = (ArrayList<Map<String, Object>>)result.get("stagedExerciseList");
                                        list.remove(position);
                                        stageExercise_items.remove(position);
//                                    test section
                                        for(Map<String, Object> i : list) {
                                            HealthItem new_item = new HealthItem(
                                                    document.getId(),
                                                    (String) i.get("name"),
                                                    (String) i.get("type"),
                                                    (boolean) i.get("flag_count"),
                                                    (boolean) i.get("flag_time"),
                                                    (boolean) i.get("flag_weight")
                                            );

                                            System.out.println(">>>>>2" + document.getId());
                                            add_list.add(new_item);}
//                                    test end
                                        docData.put("stagedExerciseList", add_list);
                                        firebaseinit.firebaseFirestore.collection("stageExercise").document(UserInfo.user_Id)
                                                .set(docData)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Log.d(TAG, "DocumentSnapshot successfully written!");
                                                        notifyDataSetChanged();
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
                                    }
                                } else {
                                    Log.d(TAG, "get failed with ", task.getException());
                                }
                            }
                        });

                        return true;
                    default:
                        return false;
                }
            }
        });

        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.exercise_ready_list_menu, popup.getMenu());
        notifyDataSetChanged();
        popup.show();
    }

    private void showPopupForAdd(View v, final ExerciseReadyListItem exerciseListItem) {

        dialog = new Dialog(v.getContext());       // Dialog 초기화
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 제거
        dialog.setContentView(R.layout.exercise_add_dialog);

        showDialog02(exerciseListItem);
    }

    public void showDialog01(){
        dialog.show(); // 다이얼로그 띄우기

        /* 이 함수 안에 원하는 디자인과 기능을 구현하면 된다. */

        // 위젯 연결 방식은 각자 취향대로~
        // '아래 아니오 버튼'처럼 일반적인 방법대로 연결하면 재사용에 용이하고,
        // '아래 네 버튼'처럼 바로 연결하면 일회성으로 사용하기 편함.
        // *주의할 점: findViewById()를 쓸 때는 -> 앞에 반드시 다이얼로그 이름을 붙여야 한다.

        // 아니오 버튼
        Button noBtn = dialog.findViewById(R.id.noBtn);
        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 원하는 기능 구현
                dialog.dismiss(); // 다이얼로그 닫기
            }
        });
    }

    public void showDialog02(ExerciseReadyListItem exerciseListItem){
        dialog.show(); // 다이얼로그 띄우기

        /* 이 함수 안에 원하는 디자인과 기능을 구현하면 된다. */

        // 위젯 연결 방식은 각자 취향대로~
        // '아래 아니오 버튼'처럼 일반적인 방법대로 연결하면 재사용에 용이하고,
        // '아래 네 버튼'처럼 바로 연결하면 일회성으로 사용하기 편함.
        // *주의할 점: findViewById()를 쓸 때는 -> 앞에 반드시 다이얼로그 이름을 붙여야 한다.

        // 아니오 버튼

        LinearLayout linearLayout_count = dialog.findViewById(R.id.ExerciseAddDialog_count);
        LinearLayout linearLayout_time = dialog.findViewById(R.id.ExerciseAddDialog_time);
        LinearLayout linearLayout_weight = dialog.findViewById(R.id.ExerciseAddDialog_weight);

        EditText count_text = dialog.findViewById(R.id.ExerciseAddDialog_count_text);
        EditText time_text = dialog.findViewById(R.id.ExerciseAddDialog_time_text);
        EditText weight_text = dialog.findViewById(R.id.ExerciseAddDialog_weight_text);

        if(!exerciseListItem.isFlag_count())
            linearLayout_count.setVisibility(View.GONE);

        if(!exerciseListItem.isFlag_time())
            linearLayout_time.setVisibility(View.GONE);

        if(!exerciseListItem.isFlag_weight())
            linearLayout_weight.setVisibility(View.GONE);



        Button Button_save = dialog.findViewById(R.id.ExerciseAddDialog_SaveButton);

        Button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "This action on Btn_save");
                if(exerciseListItem.isFlag_count()) {

                    Log.d(TAG, "count : " + count_text.getText().toString());
                }
                if(exerciseListItem.isFlag_time()) {

                    Log.d(TAG, "time : " + time_text.getText().toString());
                }
                if(exerciseListItem.isFlag_weight()) {

                    Log.d(TAG, "weight : " + weight_text.getText().toString());
                }
                Log.d(TAG, "This action on Btn_save end");

                ExerciseRecord newRecord = new ExerciseRecord();

//                List<ExerciseCompleteListItem> list = exercise_complete_items.exercise_items;
                if (check_complete_exercise(exerciseListItem.getExerciseName(), exercise_complete_items.exercise_items)){
                    for(ExerciseCompleteListItem item : exercise_complete_items.exercise_items){
                        if (exerciseListItem.getExerciseName().equals(item.getExerciseName())){
                            int set = Integer.parseInt(item.getSet()) + 1;

                            item.setSet(String.valueOf(set));

                        }
                    }
                } else {
                    exercise_complete_items.addItem(new ExerciseCompleteListItem(exerciseListItem.getExerciseName(), "1"));
                }

                exercise_complete_items.refresh();
                //firebaseinit.firebaseFirestore.collection(new SimpleDateFormat( "yyyyMMdd").format(new Date())).document(UserInfo.user_Id).set(city);
                renewal_complete_exercise(exerciseListItem.getExerciseName());
                renewal_calendar(exercise_complete_items.exercise_items);
                // 원하는 기능 구현
                dialog.dismiss(); // 다이얼로그 닫기
            }
        });
    }

    public boolean check_complete_exercise(String name, List<ExerciseCompleteListItem> listItem){
        for(ExerciseCompleteListItem item : listItem){
            if (item.getExerciseName().equals(name)){
                return true;
            }
        }
        return false;
    }

    public void renewal_complete_exercise(String exerciseName) {

        Map<String, Object> docData = new HashMap<>();

        DocumentReference docRef = firebaseinit.firebaseFirestore.collection("completeExercises").document(UserInfo.user_Id);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();

                    Map<String, Object> result = document.getData();

                    if (document.exists()) {
                        if (result.containsKey(exerciseName)){
                            Map<String, Object> set = (Map<String, Object>) result.get(exerciseName);
                            String setNum = (String) set.get("set");
                            int setUpdate = Integer.parseInt(setNum) + 1;
                            Log.d(TAG, "Type Casting int : " + setNum);
                            docData.put(exerciseName, new ExerciseCompleteListItem(exerciseName, String.valueOf(setUpdate)) );
                        } else {
                            docData.put(exerciseName, new ExerciseCompleteListItem(exerciseName, "1"));
                        }

                        firebaseinit.firebaseFirestore.collection("completeExercises").document(UserInfo.user_Id)
                                .update(docData)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "DocumentSnapshot successfully written!");
                                        notifyDataSetChanged();
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
                        firebaseinit.firebaseFirestore.collection("completeExercises").document(UserInfo.user_Id)
                                .set(docData)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {

                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                    }
                                });
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }
    // 캘린더 갱신
    public void renewal_calendar(ArrayList<ExerciseCompleteListItem> exercise_items) {
        Log.d(TAG, "call exercise complete list item : " + exercise_items);
        DocumentReference docRef = firebaseinit.firebaseFirestore.collection("calendar").document(UserInfo .user_Id);
        Map<String, Object> docData = new HashMap<>();

        Calendar calendar  = Calendar.getInstance();

//        String year = String.valueOf(calendar.get(Calendar.YEAR));
//        String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
//        String day = String.valueOf(calendar.get(Calendar.DATE));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
        //todayDate
        String today = sdf.format(calendar.getTime()).toString();

        docData.put(today, exercise_items);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()){
                        firebaseinit.firebaseFirestore.collection("calendar").document(UserInfo.user_Id)
                                .update(today, exercise_items)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {

                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                    }
                                });

                    } else {
                        Log.d(TAG, "No such document");
                        firebaseinit.firebaseFirestore.collection("calendar").document(UserInfo.user_Id)
                                .set(docData)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {

                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

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

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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;

import com.example.heath_together.FirebaseInit.firebaseinit;
import com.example.heath_together.Object.DTO.ExerciseCompleteListItem;
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
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ExerciseCompleteItemAdapter extends BaseAdapter {

    ArrayList<ExerciseCompleteListItem> exercise_items = new ArrayList<ExerciseCompleteListItem>();
    Context context;
    Dialog dialog;


    @Override
    public int getCount() {
        return exercise_items.size();
    }

    @Override
    public Object getItem(int position) {
        return exercise_items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        context = viewGroup.getContext();
        ExerciseCompleteListItem exerciseListItem = exercise_items.get(position);

        if(view == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.exercise_complete_item, viewGroup, false);
        }

        TextView TextView_ExerciseNmae = view.findViewById(R.id.ExerciseCompleteListItem_ExerciseName);
        TextView TextView_ExerciseSetNum = view.findViewById(R.id.ExerciseCompleteListItem_SetNum);
        ImageButton ImageButton_SetButton = view.findViewById(R.id.ExerciseCompleteListItem_setButton);

        TextView_ExerciseNmae.setText(exerciseListItem.getExerciseName());
        TextView_ExerciseSetNum.setText(String.valueOf(exerciseListItem.getSet()) + "set");

        ImageButton_SetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupForSet(v, position);
            }
        });

        return view;
    }

    public void addItem(ExerciseCompleteListItem item) {

        exercise_items.add(item);
    }
    public void refresh() {
        notifyDataSetChanged();
    }

    private void showPopupForSet(View v, final int position) {
        PopupMenu popup = new PopupMenu(v.getContext(), v);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                DocumentReference docRef = firebaseinit.firebaseFirestore.collection("completeExercises").document(UserInfo.user_Id);
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
                        String delete_exercise = exercise_items.get(position).getExerciseName();
//                        Log.d(TAG, "get exercise 02:20 : " + delete_exercise);
                        exercise_items.remove(position);
                        notifyDataSetChanged();
                        for (ExerciseCompleteListItem listItem : exercise_items){
                            ExerciseCompleteListItem item = new ExerciseCompleteListItem(
                                    listItem.getExerciseName(),
                                    listItem.getSet()
                            );
                            docData.put(listItem.getExerciseName(), item);
                        }
                        firebaseinit.firebaseFirestore.collection("completeExercises").document(UserInfo.user_Id)
                                .set(docData)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "DocumentSnapshot successfully written!");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w(TAG, "Error writing document", e);
                                    }
                                });

                        renewal_calendar(exercise_items);
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

    public void renewal_calendar(ArrayList<ExerciseCompleteListItem> exercise_items) {

        Calendar calendar = Calendar.getInstance();

//        int Year = calendar.get(Calendar.YEAR);
//        int month = calendar.get(Calendar.MONTH) + 1;
//        int day = calendar.get(Calendar.DATE);
//
//        String today = Year + "-" + month + "-" + day;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);


        //todayDate
        String today = sdf.format(calendar.getTime()).toString();

        firebaseinit.firebaseFirestore.collection("calendar").document(UserInfo.user_Id)
                .update(today, exercise_items)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                    }
                });
    }
}

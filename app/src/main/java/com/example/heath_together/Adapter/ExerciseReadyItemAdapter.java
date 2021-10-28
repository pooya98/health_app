package com.example.heath_together.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.PopupMenu;

import com.example.heath_together.FirebaseInit.firebaseinit;
import com.example.heath_together.Object.DTO.ExerciseReadyListItem;
import com.example.heath_together.Object.DTO.ExerciseRecord;
import com.example.heath_together.R;
import com.example.heath_together.UserInfo.UserInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ExerciseReadyItemAdapter extends BaseAdapter {

    Dialog dialog;

    ArrayList<ExerciseReadyListItem> stageExercise_items = new ArrayList<ExerciseReadyListItem>();
    Context context;

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
                        return true;
                    default:
                        return false;
                }
            }
        });

        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.exercise_ready_list_menu, popup.getMenu());
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



                ExerciseRecord newRecord = new ExerciseRecord();

                //firebaseinit.firebaseFirestore.collection(new SimpleDateFormat( "yyyyMMdd").format(new Date())).document(UserInfo.user_Id).set(city);

                // 원하는 기능 구현
                dialog.dismiss(); // 다이얼로그 닫기
            }
        });

    }
}

package com.example.heath_together;


import android.content.Context;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;


import com.example.heath_together.Adapter.ProfileListViewAdapter;
import com.example.heath_together.Object.DTO.ProfileListItem;
import com.example.heath_together.R;
import com.example.heath_together.UserInfo.UserInfo;

import androidx.fragment.app.Fragment;



public class Main4_3 extends Fragment {

    private View view;
    private Context context;
    private Button createExerciseButton;

    String profileId;
    String profileUserId;
    public Main4_3(String userId){

        this.profileUserId= userId;

    }
    public Main4_3(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main4_3, container, false);

        if(profileUserId==null){
            profileId= UserInfo.user_Id;
        }else{                                                 //내 계정, 상대 계정 프로필 구분.
            profileId=profileUserId;
        }


        createExerciseButton = view.findViewById(R.id.create_exercise_button);

        createExerciseButton.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Main4_3_CreateExercise.class);
                startActivity(intent);
            }
        });


        return view;

    }
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);

        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}
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
import com.example.heath_together.UserInfo.UserInfo;


public class Main4_2 extends Fragment {

    private View view;
    private Context context;


    String profileId;
    String profileUserId;
    public Main4_2(String userId){

        this.profileUserId= userId;

    }
    public Main4_2(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main4_2, container, false);

        if(profileUserId==null){
            profileId= UserInfo.user_Id;
        }else{                                                 //내 계정, 상대 계정 프로필 구분.
            profileId=profileUserId;
        }


        ListView listView = (ListView)view.findViewById(R.id.profile_listView);
        Button btn_create_list = (Button)view.findViewById(R.id.profile_btn_list);

        ProfileListViewAdapter adapter = new ProfileListViewAdapter();

        adapter.addItem(new ProfileListItem("김계란의 가슴운동", "2"));
        adapter.addItem(new ProfileListItem("김종국의 하체운동", "3"));
        listView.setAdapter(adapter);
        setListViewHeightBasedOnChildren(listView);

        btn_create_list.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), main4_2_CreateList.class);
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
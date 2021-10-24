package com.example.heath_together;

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


public class Main4_3 extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fv = inflater.inflate(R.layout.main4_3, container, false);

        ListView listView = (ListView) fv.findViewById(R.id.main4_3_list);
        Button create_btn = (Button) fv.findViewById(R.id.main4_3_createExercise);

        ProfileListViewAdapter adapter = new ProfileListViewAdapter();

        adapter.addItem(new ProfileListItem("김계란의 가슴운동", "2"));
        adapter.addItem(new ProfileListItem("김종국의 하체운동", "3"));
        listView.setAdapter(adapter);
        setListViewHeightBasedOnChildren(listView);

        create_btn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Main4_3_CreateExercise.class);
                startActivity(intent);
            }
        });


        return fv;
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
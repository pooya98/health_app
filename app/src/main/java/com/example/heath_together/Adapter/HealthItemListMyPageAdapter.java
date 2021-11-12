package com.example.heath_together.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.heath_together.Object.DTO.ExerciseCompleteListItem;
import com.example.heath_together.Object.DTO.HealthItem;
import com.example.heath_together.R;

import java.util.ArrayList;
import java.util.List;

public class HealthItemListMyPageAdapter extends BaseAdapter {
    ArrayList<HealthItem> healthItems = new ArrayList<HealthItem>();
    Context context;
    Dialog dialog;

    @Override
    public int getCount() {
        return healthItems.size();
    }

    @Override
    public Object getItem(int i) {
        return healthItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        context = viewGroup.getContext();
        HealthItem exerciseItem = healthItems.get(position);

        if(view == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.main4_2_readylist, viewGroup, false);
        }

        TextView TextView_ExerciseNmae = view.findViewById(R.id.ExerciseCompleteListItem_ExerciseName);
        TextView TextView_ExerciseSetNum = view.findViewById(R.id.ExerciseCompleteListItem_SetNum);

        TextView_ExerciseNmae.setText(exerciseItem.getName());
        TextView_ExerciseSetNum.setText("set");
        return view;
    }

    public void addItem(HealthItem item){
        healthItems.add(item);
    }
}

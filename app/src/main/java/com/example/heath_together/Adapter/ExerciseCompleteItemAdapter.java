package com.example.heath_together.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.heath_together.Object.DTO.ExerciseCompleteListItem;
import com.example.heath_together.R;

import java.util.ArrayList;

public class ExerciseCompleteItemAdapter extends BaseAdapter {

    ArrayList<ExerciseCompleteListItem> exercise_items = new ArrayList<ExerciseCompleteListItem>();
    Context context;

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

        TextView_ExerciseNmae.setText(exerciseListItem.getExerciseName());
        TextView_ExerciseSetNum.setText(exerciseListItem.getSet());

        return view;
    }

    public void addItem(ExerciseCompleteListItem item) {
        exercise_items.add(item);
    }
}

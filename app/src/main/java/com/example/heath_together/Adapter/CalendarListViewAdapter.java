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
import com.example.heath_together.R;
import com.example.heath_together.UserInfo.UserInfo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CalendarListViewAdapter extends BaseAdapter {

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
            view = inflater.inflate(R.layout.main4_1_calendarlist, viewGroup, false);
        }

        TextView TextView_ExerciseNmae = view.findViewById(R.id.Main4_1_calendar_ExerciseName);
        TextView TextView_ExerciseSetNum = view.findViewById(R.id.Main4_1_calendar_SetNum);

        TextView_ExerciseNmae.setText(exerciseListItem.getExerciseName());
        TextView_ExerciseSetNum.setText(exerciseListItem.getSet() + "set");


        return view;
    }
    public void resetItme() { exercise_items.clear(); }
    public void addItem(ExerciseCompleteListItem item) {
        exercise_items.add(item);
    }
    public void refresh() {
        notifyDataSetChanged();
    }
}

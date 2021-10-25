package com.example.heath_together.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.heath_together.Object.DTO.GroupListItem;
import com.example.heath_together.Object.DTO.ProfileListItem;
import com.example.heath_together.R;

import java.security.acl.Group;
import java.util.ArrayList;

public class ProfileListViewAdapter extends BaseAdapter {
    ArrayList<ProfileListItem> profile_items = new ArrayList<ProfileListItem>();
    Context context;


    @Override
    public int getCount() { return profile_items.size(); }

    @Override
    public Object getItem(int position) {
        return profile_items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup)
    {
        context = viewGroup.getContext();
        ProfileListItem profileListItem = profile_items.get(position);

        if(view == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.profile_list_menu, viewGroup, false);
        }

        TextView exercise_title = view.findViewById(R.id.exercise_title);
        TextView exercise_count = view.findViewById(R.id.exercise_count);

        exercise_title.setText(profileListItem.getExercise_title());
        exercise_count.setText(profileListItem.getExercise_count());

        return view;
    }
    public void resetItme() { profile_items.clear(); }
    public void addItem(ProfileListItem item) { profile_items.add(item); }
}

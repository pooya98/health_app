package com.example.heath_together.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.heath_together.Object.DTO.GroupListItem;
import com.example.heath_together.R;

import java.util.ArrayList;

public class ListItemAdapter extends BaseAdapter {



    ArrayList<GroupListItem> group_items = new ArrayList<GroupListItem>();
    Context context;

    @Override
    public int getCount() {
        return group_items.size();
    }

    @Override
    public Object getItem(int position) {
        return group_items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        context = viewGroup.getContext();
        GroupListItem groupListItem = group_items.get(position);

        if(view == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.group_item, viewGroup, false);
        }

        TextView nameText = view.findViewById(R.id.group_name);
        TextView typeText = view.findViewById(R.id.groupListItem_groupType);
        TextView leaderNameText = view.findViewById(R.id.groupListItem_groupLeaderName);
        TextView memInfoText = view.findViewById(R.id.groupListItem_MemInfo);

        nameText.setText(groupListItem.getGroupName());
        typeText.setText(groupListItem.getGroupType());
        leaderNameText.setText(groupListItem.getLeaderName());
        memInfoText.setText(groupListItem.getMem_num() + "/" + groupListItem.getMem_limit());

        return view;
    }

    public void addItem(GroupListItem item) {
        group_items.add(item);
    }
}

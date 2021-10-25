package com.example.heath_together.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
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
    public GroupListItem getItem(int position) {
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

        TextView GroupName = view.findViewById(R.id.groupListItem_GroupName);
        TextView GroupLeaderName = view.findViewById(R.id.groupListItem_GroupLeaderName);
        TextView MemInfo = view.findViewById(R.id.groupListItem_MemInfo);
        //TextView GroupTag = view.findViewById(R.id.groupListItem_GroupTag);
        ImageButton groupListItem_setButton = view.findViewById(R.id.groupListItem_setButton);

        groupListItem_setButton.setFocusable(false);

        GroupName.setText(groupListItem.getGroupName());
        GroupLeaderName.setText(groupListItem.getLeaderName());
        MemInfo.setText(groupListItem.getMem_num() + "/" + groupListItem.getMem_limit());
        //GroupTag.setText(groupListItem.getGroupTag());


        return view;
    }

    public void addItem(GroupListItem item) {
        group_items.add(item);
    }
}

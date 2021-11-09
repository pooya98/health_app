package com.example.heath_together.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.heath_together.GroupJoin;
import com.example.heath_together.MainActivity;
import com.example.heath_together.Object.DTO.GroupListItem;
import com.example.heath_together.R;

import java.util.ArrayList;
import java.util.List;

public class SearchGAdapter extends RecyclerView.Adapter<SearchGAdapter.MyViewHolder> {
     MainActivity mainActivity;

    private ArrayList<GroupListItem> group_items = new ArrayList<GroupListItem>();

    Context context;


    public class MyViewHolder extends RecyclerView.ViewHolder{

        LinearLayout linearLayout_searchGroup;
        TextView GroupName;
        TextView GroupLeaderName;
        TextView MemInfo;
        ImageButton groupListItem_setButton;




        public MyViewHolder(View view) {

            super(view);
            GroupName = view.findViewById(R.id.groupListItem_GroupName);
            GroupLeaderName = view.findViewById(R.id.groupListItem_GroupLeaderName);
            MemInfo = view.findViewById(R.id.groupListItem_MemInfo);

            groupListItem_setButton = view.findViewById(R.id.groupListItem_setButton);

            groupListItem_setButton.setFocusable(false);

            linearLayout_searchGroup = view.findViewById(R.id.groupItem);


        }

    }

    public SearchGAdapter(ArrayList<GroupListItem> group_items){

        this.group_items = group_items;

    }

    @Override
    public int getItemCount() {
        return group_items.size();
    }

    public void addItem(GroupListItem item) {
        group_items.add(item);
    }



    @NonNull
    @Override
    public SearchGAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_item,parent,false);
        SearchGAdapter.MyViewHolder holder = new SearchGAdapter.MyViewHolder(view);

        return holder ;
    }

    public GroupListItem getItem(int position) {
        return group_items.get(position);
    }


    public long getItemId(int position) {
        return position;
    }


    @Override
    public void onBindViewHolder(@NonNull SearchGAdapter.MyViewHolder holder, int position) {


        GroupListItem groupListItem = group_items.get(position);

        // 그룹 가입화면------------------------------------------
       String groupN = group_items.get(position).getGroupName();
       String leaderN = group_items.get(position).getLeaderName();
       String groupT = group_items.get(position).getGroupTag();
       String groupId = group_items.get(position).getGroupId();
        //---------------------------------------------------

       holder.GroupName.setText(groupListItem.getGroupName());
       holder.GroupLeaderName.setText(groupListItem.getLeaderName());
       holder.MemInfo.setText(groupListItem.getMem_num() + "/" + groupListItem.getMem_limit());
       holder.itemView.setOnClickListener(new View.OnClickListener(){

           public void onClick(View v){


               AppCompatActivity activity = (AppCompatActivity)v.getContext();
               GroupJoin groupJoin = new GroupJoin(groupN,leaderN,groupT,groupId );
               activity.getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,groupJoin).addToBackStack(null).commit();
           }
        });


    }











}

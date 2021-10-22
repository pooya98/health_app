package com.example.heath_together.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
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

public class SearchGAdapter extends RecyclerView.Adapter<SearchGAdapter.MyViewHolder> implements Filterable{
     MainActivity mainActivity;

    private List<GroupListItem> groupList ;
    private List<GroupListItem> groupListFull ; //search


    Context context;
    public SearchGAdapter(List<GroupListItem> groupList,Context context){
        this.groupList = groupList;
        groupListFull = new ArrayList<>(groupList);//search

        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_item,parent,false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder ;
    }

    public Object getItem(int position) {
        return groupList.get(position);
    }
    public long getItemId(int position) {
        return position;
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // 그룹 가입화면------------------------------------------
       String groupN = groupList.get(position).getGroupName();
       String leaderN = groupList.get(position).getLeaderName();
       String groupT = groupList.get(position).getGroupTag();
        //---------------------------------------------------
       holder.nameText.setText(groupList.get(position).getGroupName());
       holder.typeText.setText(groupList.get(position).getGroupTag());
       holder.leaderNameText.setText(groupList.get(position).getLeaderName());
       holder.memInfoText.setText(groupList.get(position).getMem_num() + "/" + groupList.get(position).getMem_limit());


       holder.itemView.setOnClickListener(new View.OnClickListener(){

           public void onClick(View v){


               AppCompatActivity activity = (AppCompatActivity)v.getContext();
               GroupJoin groupJoin = new GroupJoin(groupN,leaderN,groupT );
               activity.getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,groupJoin).addToBackStack(null).commit();
           }
        });


    }



    @Override
    public int getItemCount() {
        return groupList.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

     private Filter exampleFilter = new Filter(){
        @Override
        protected FilterResults performFiltering(CharSequence constraint){
            List<GroupListItem> filteredList = new ArrayList<>();
            if(constraint == null || constraint.length()==0) {
                filteredList.addAll(groupListFull);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();

                for(GroupListItem item:groupListFull){
                    if(item.getGroupName().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results){
            groupList.clear();
            groupList.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };



    public class MyViewHolder extends RecyclerView.ViewHolder{


        TextView nameText;
        TextView typeText;
        TextView leaderNameText;
        TextView memInfoText;

        public MyViewHolder(View view) {

            super(view);
            nameText = view.findViewById(R.id.group_name);//그룹이름
//            typeText = view.findViewById(R.id.groupListItem_GroupTag);//그룹 해쉬태그.
            leaderNameText = view.findViewById(R.id.groupListItem_GroupLeaderName);//그룹장 이름
            memInfoText = view.findViewById(R.id.groupListItem_MemInfo);




        }

    }

    public void addItem(GroupListItem item) {
        groupList.add(item);
    }
}

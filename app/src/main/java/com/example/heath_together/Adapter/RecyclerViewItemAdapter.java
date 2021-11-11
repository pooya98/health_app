package com.example.heath_together.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.heath_together.GroupJoin;
import com.example.heath_together.Main4;
import com.example.heath_together.Object.DTO.AccountListItem;
import com.example.heath_together.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewItemAdapter extends RecyclerView.Adapter<RecyclerViewItemAdapter.ViewHolder>  {



    private ArrayList<AccountListItem> accountList ;



    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView1;
        CircleImageView profileView;
        LinearLayout LinearLayout_member;

        ViewHolder(View itemView) {
            super(itemView);

            // 뷰 객체에 대한 참조. (hold strong reference)
            textView1 = itemView.findViewById(R.id.RecyclerviewItem_HealthName) ;
            profileView = itemView.findViewById(R.id.Profile_Setting_Image);
            LinearLayout_member = itemView.findViewById(R.id.memberLayout);

        }
    }

    // 생성자에서 데이터 리스트 객체를 전달받음.
    public RecyclerViewItemAdapter(ArrayList<AccountListItem> accountList) {
        this.accountList= accountList;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public RecyclerViewItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.recyclerview_item, parent, false) ;
        RecyclerViewItemAdapter.ViewHolder vh = new RecyclerViewItemAdapter.ViewHolder(view) ;

        return vh ;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(RecyclerViewItemAdapter.ViewHolder holder, int position) {
        holder.textView1.setText(accountList.get(position).getUserName());
        String userUid = accountList.get(position).getUid();
        String imageUrl = accountList.get(position).getProfileUri();
        if (imageUrl!=null) {
            Glide.with(holder.itemView).load(imageUrl).into(holder.profileView);
        }else{
            Glide.with(holder.itemView).load(R.drawable.profile).into(holder.profileView);
        }
        //public void on Click =>아이템이 눌렸을때 프로필로 입장하는 함수.

        holder.itemView.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){


                AppCompatActivity activity = (AppCompatActivity)v.getContext();
                Main4 main4 = new Main4(userUid);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,main4).addToBackStack(null).commit();
            }
        });


    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return accountList.size() ;
    }

    public void addItem(AccountListItem item) {
        accountList.add(item);
    }
}
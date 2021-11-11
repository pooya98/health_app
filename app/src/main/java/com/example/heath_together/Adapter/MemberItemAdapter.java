package com.example.heath_together.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.heath_together.Main4;
import com.example.heath_together.Object.DTO.AccountListItem;
import com.example.heath_together.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MemberItemAdapter extends RecyclerView.Adapter<MemberItemAdapter.ViewHolder> {

    private ArrayList<AccountListItem> mData = null ;

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView1;
        LinearLayout LinearLayout_member;
        CircleImageView profileView;

        ViewHolder(View itemView) {
            super(itemView);

            // 뷰 객체에 대한 참조. (hold strong reference)
            textView1 = itemView.findViewById(R.id.RecyclerviewItem_HealthName) ;
            LinearLayout_member = itemView.findViewById(R.id.memberLayout);
            profileView = itemView.findViewById(R.id.Profile_Setting_Image);

        }
    }

    // 생성자에서 데이터 리스트 객체를 전달받음.
    public MemberItemAdapter(ArrayList<AccountListItem> list) {
        mData = list ;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public MemberItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.recyclerview_item, parent, false) ;
        MemberItemAdapter.ViewHolder vh = new MemberItemAdapter.ViewHolder(view) ;

        return vh ;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(MemberItemAdapter.ViewHolder holder, int position) {

        String text = mData.get(position).getUserName() ;
        String userUid = mData.get(position).getUid();

        holder.textView1.setText(text) ;

        String imageUrl = mData.get(position).getProfileUri();
        if (imageUrl!=null) {
            Glide.with(holder.itemView).load(imageUrl).into(holder.profileView);
        }else{
            Glide.with(holder.itemView).load(R.drawable.profile).into(holder.profileView);
        }

        holder.LinearLayout_member.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity)v.getContext();
                Main4 main4 = new Main4(userUid);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,main4).addToBackStack(null).commit();

                /*
                Intent intent = new Intent(v.getContext(), StoryActivity.class);
                intent.putExtra("number", position);
                intent.putExtra("title", mData.get(position));
                v.getContext().startActivity(intent);
                Toast.makeText(v.getContext(), "클릭 되었습니다.", Toast.LENGTH_SHORT).show();*/
            }
        });

    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size() ;
    }
}
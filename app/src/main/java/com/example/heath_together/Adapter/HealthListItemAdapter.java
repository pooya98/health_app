package com.example.heath_together.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.heath_together.Object.DTO.HealthListItem;
import com.example.heath_together.R;

import java.util.List;

public class HealthListItemAdapter extends RecyclerView.Adapter<HealthListItemAdapter.ViewHolder>  {



    private List<HealthListItem> HealthListList ;


    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView1;
        TextView textView2;
        LinearLayout LinearLayout_health;

        ViewHolder(View itemView) {
            super(itemView);

            // 뷰 객체에 대한 참조. (hold strong reference)
            textView1 = itemView.findViewById(R.id.healthList_listName) ;
            textView2 = itemView.findViewById(R.id.health_count) ;
            LinearLayout_health = itemView.findViewById(R.id.healthListLayout);

        }
    }

    // 생성자에서 데이터 리스트 객체를 전달받음.
    public HealthListItemAdapter(List<HealthListItem> HealthListList) {
        this.HealthListList= HealthListList;

    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public HealthListItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.health_list_item, parent, false) ;
        HealthListItemAdapter.ViewHolder vh = new HealthListItemAdapter.ViewHolder(view) ;

        return vh ;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(HealthListItemAdapter.ViewHolder holder, int position) {
        holder.textView1.setText(HealthListList.get(position).getHealthListName());
        holder.textView2.setText(String.valueOf(HealthListList.get(position).getHealthCount()));
        //public void on Click =>아이템이 눌렸을때 프로필로 입장하는 함수.


    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return HealthListList.size() ;
    }



    public void addItem(HealthListItem item) {
        HealthListList.add(item);
    }
}
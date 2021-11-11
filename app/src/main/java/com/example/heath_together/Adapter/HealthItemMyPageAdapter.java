package com.example.heath_together.Adapter;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.heath_together.ClickCallbackListener;
import com.example.heath_together.Main1_1;
import com.example.heath_together.Main4_1;
import com.example.heath_together.Object.DTO.HealthItem;
import com.example.heath_together.R;
import com.example.heath_together.main4_2_CreateListList;

import java.util.ArrayList;
import java.util.List;

public class HealthItemMyPageAdapter extends RecyclerView.Adapter<HealthItemMyPageAdapter.ViewHolder>  {

    public static SparseBooleanArray mSelectedItems = new SparseBooleanArray(0);//position별 선택상태를 저장하는 구조
    private int k;
    private main4_2_CreateListList main;

    ArrayList<HealthItem> add_list = new ArrayList<HealthItem>();
    private ClickCallbackListener callbackListener;



    private List<HealthItem> healthList ;

    public HealthItemMyPageAdapter() {
    }

    public void setCallbackListener(ClickCallbackListener callbackListener) {
        this.callbackListener = callbackListener;
        Log.d(TAG, "health MyPage Adapter callbackListener : " + callbackListener);
    }


    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView1;
        LinearLayout LinearLayout_health;

        ViewHolder(View itemView) {
            super(itemView);

            // 뷰 객체에 대한 참조. (hold strong reference)
            textView1 = itemView.findViewById(R.id.HeathItem_HealthName) ;
            LinearLayout_health = itemView.findViewById(R.id.healthLayout);



            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    int position = getAdapterPosition();
                    toggleItemSelected(position);

                    k = mSelectedItems.size();
                    main.countButton.setText("+ "+k);

                }
            });

        }
    }

    // 생성자에서 데이터 리스트 객체를 전달받음.
    public HealthItemMyPageAdapter(List<HealthItem> healthList) {
        this.healthList= healthList;

    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public HealthItemMyPageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.health_item, parent, false) ;
        HealthItemMyPageAdapter.ViewHolder vh = new HealthItemMyPageAdapter.ViewHolder(view) ;

        return vh ;
    }

    @Override
    public void onBindViewHolder(HealthItemMyPageAdapter.ViewHolder holder, int position) {

        holder.textView1.setText(healthList.get(position).getName());


        holder.itemView.setSelected(isItemSelected(position));

    }

    @Override
    public int getItemCount() {
        return healthList.size() ;
    }



    public void addItem(HealthItem item) {
        healthList.add(item);
    }

    public void setData(List<HealthItem> data){
        healthList = data;
        notifyDataSetChanged();
    }

    public List<HealthItem> getAllData(){
        return healthList;
    }

    private void toggleItemSelected(int position){
        Log.d("toggle", add_list.toString());
        Log.d("toggle2", String.valueOf(healthList));
        if(mSelectedItems.get(position,false)==true){
            mSelectedItems.delete(position);
            notifyItemChanged(position);
            add_list.remove(healthList.get(position));
            callbackListener.callBack(add_list);
        } else {
            mSelectedItems.put(position,true);
            notifyItemChanged(position);
            add_list.add(healthList.get(position));
            callbackListener.callBack(add_list);
        }
    }

    private boolean isItemSelected(int position){
        return mSelectedItems.get(position,false);
    }
}

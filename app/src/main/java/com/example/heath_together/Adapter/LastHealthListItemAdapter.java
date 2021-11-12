package com.example.heath_together.Adapter;

import static android.content.ContentValues.TAG;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.heath_together.ClickCallbackListener;
import com.example.heath_together.FirebaseInit.firebaseinit;
import com.example.heath_together.Main1;
import com.example.heath_together.Main1_1;
import com.example.heath_together.Main4;
import com.example.heath_together.Object.DTO.HealthItem;
import com.example.heath_together.Object.DTO.ProfileListItem;
import com.example.heath_together.R;
import com.example.heath_together.UserInfo.UserInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LastHealthListItemAdapter extends RecyclerView.Adapter<LastHealthListItemAdapter.ViewHolder>  {


    ArrayList<HealthItem> add_list = new ArrayList<HealthItem>() ;
    ArrayList<ProfileListItem> profile_items = new ArrayList<ProfileListItem>();
    String key;

    Context context;
    Dialog dialog;
    private ClickCallbackListener callbackListener;




    public void setCallbackListener(ClickCallbackListener callbackListener) {
        this.callbackListener = callbackListener;
    }


    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView exercise_title;
        TextView exercise_count;
        ImageButton exercise_setButton;
        LinearLayout LinearLayout_health;

        ViewHolder(View itemView) {
            super(itemView);

            // 뷰 객체에 대한 참조. (hold strong reference)
             exercise_title = itemView.findViewById(R.id.exercise_title);
             exercise_count = itemView.findViewById(R.id.exercise_count);
           exercise_setButton = itemView.findViewById(R.id.imageButton);



//            itemView.setOnClickListener(new View.OnClickListener(){
//                @Override
//                public void onClick(View v){
//                    int position = getAdapterPosition();
//                    toggleItemSelected(position);
//
//                    k = mSelectedItems.size();
//                    main.countButton.setText(k+"개 담기");
//
//                }
//            });

        }
    }

    // 생성자에서 데이터 리스트 객체를 전달받음.
    public LastHealthListItemAdapter(ArrayList<ProfileListItem> profile_items) {
        this.profile_items= profile_items;
        notifyDataSetChanged();

    }

    public LastHealthListItemAdapter() {

    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public LastHealthListItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.profile_list_menu, parent, false) ;
        LastHealthListItemAdapter.ViewHolder vh = new LastHealthListItemAdapter.ViewHolder(view) ;

        return vh ;
    }

    @Override
    public void onBindViewHolder(LastHealthListItemAdapter.ViewHolder holder, int position) {

        holder.exercise_title.setText(profile_items.get(position).getExercise_title());
        holder.exercise_count.setText("운동 개수 : "+profile_items.get(position).getExercise_count());
        key = profile_items.get(position).getExercise_title();

        holder.exercise_setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupForSet(v);
            }
        });



    }

    @Override
    public int getItemCount() {
        return profile_items.size() ;
    }



    public void addItem(ProfileListItem item) {
        profile_items.add(item);
    }

    public void setData(ArrayList<ProfileListItem> data){
        profile_items = data;
        notifyDataSetChanged();
    }


    private void showPopupForSet(View v) {
        PopupMenu popup = new PopupMenu(v.getContext(), v);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.Get_List:
                        Map<String, Object> docData = new HashMap<>();
                        add_list = new ArrayList<HealthItem>();


                        DocumentReference sfDocRef = firebaseinit.firebaseFirestore.collection("stageExercise").document(UserInfo.user_Id);

                        sfDocRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if(task.isSuccessful()){
                                    DocumentSnapshot document = task.getResult();
                                    Log.d(TAG, "task result : " + document.getData());

                                    if (document.exists()){
                                        Map<String, Object> result = document.getData();
//                                        ArrayList<Map<String, Object>> list = (ArrayList<Map<String, Object>>)result.get("stagedExerciseList");

                                        DocumentReference docRef = firebaseinit.firebaseFirestore.collection("myPageCreateExerciseList").document(UserInfo.user_Id);
                                        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if(task.isSuccessful()){
                                                    DocumentSnapshot document = task.getResult();
                                                    if(document.exists()){
                                                        Map<String, Object> result = document.getData();
                                                        for(String key : result.keySet()){
                                                            ArrayList<HealthItem> keyValue = (ArrayList<HealthItem>) result.get(key);
                                                            Log.d(TAG, "monster : " + key + keyValue.size());
                                                            add_list.add(new HealthItem(key));


                                                        }

                                                    } else {
                                                        Log.d(TAG, "No such document");
                                                    }
                                                } else {
                                                    Log.d(TAG, "get failed with ", task.getException());
                                                }
                                            }
                                        });



                                        ArrayList<Map<String, Object>> list = (ArrayList<Map<String, Object>>)result.get("stagedExerciseList");
                                        if(list != null) {///////////////////////////////
                                            for(Map<String, Object> i : list) {
                                                boolean overlap = true;
                                                for (HealthItem item : add_list){
                                                    String name = item.getName();
                                                    if(name.equals(i.get("name"))){
                                                        overlap = false;
                                                    }
                                                }
                                                if (overlap){
                                                    HealthItem new_item = new HealthItem(
                                                            document.getId(),
                                                            (String) i.get("name"),
                                                            (String) i.get("type"),
                                                            (boolean) i.get("flag_count"),
                                                            (boolean) i.get("flag_time"),
                                                            (boolean) i.get("flag_weight")
                                                    );
                                                    System.out.println(">>>>>2" + document.getId());

                                                    add_list.add(new_item);}
                                            }
                                        }
                                        Log.d(TAG, "search add_list : " + add_list );
                                        docData.put("stagedExerciseList", add_list);
                                        firebaseinit.firebaseFirestore.collection("stageExercise").document(UserInfo.user_Id)
                                                .set(docData)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Log.d(TAG, "DocumentSnapshot successfully written!");
                                                        AppCompatActivity activity = (AppCompatActivity)v.getContext();
                                                        Main1 main1 = new Main1();
                                                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,main1).addToBackStack(null).commit();

                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Log.w(TAG, "Error writing document", e);
                                                    }
                                                });
                                    } else {
                                        Log.d(TAG, "No such document");
                                        docData.put("stagedExerciseList", add_list);

                                        firebaseinit.firebaseFirestore.collection("stageExercise").document(UserInfo.user_Id)
                                                .set(docData)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Log.d(TAG, "DocumentSnapshot successfully written!");
                                                        AppCompatActivity activity = (AppCompatActivity)v.getContext();
                                                        Main1 main1 = new Main1();
                                                        activity.getSupportFragmentManager().beginTransaction().replace(R.id.main_frame,main1).addToBackStack(null).commit();

                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Log.w(TAG, "Error writing document", e);
                                                    }
                                                });
                                    }
                                } else {
                                    Log.d(TAG, "get failed with ", task.getException());
                                }
                            }
                        });



                        return true;

                    default:
                        return false;
                }
            }
        });

        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.get_list, popup.getMenu());
        notifyDataSetChanged();
        popup.show();
    }

}
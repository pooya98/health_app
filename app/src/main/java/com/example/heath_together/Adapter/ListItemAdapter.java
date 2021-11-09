package com.example.heath_together.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;

import com.example.heath_together.FirebaseInit.firebaseinit;
import com.example.heath_together.Main2;
import com.example.heath_together.Object.DTO.GroupListItem;
import com.example.heath_together.R;
import com.example.heath_together.UserInfo.UserInfo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListItemAdapter extends BaseAdapter {

    String TAG;



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

        groupListItem_setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupForSet(v, position);

            }
        });

        GroupName.setText(groupListItem.getGroupName());
        GroupLeaderName.setText(groupListItem.getLeaderName());
        MemInfo.setText(groupListItem.getMem_num() + "/" + groupListItem.getMem_limit());
        //GroupTag.setText(groupListItem.getGroupTag());



        return view;
    }

    public void addItem(GroupListItem item) {
        group_items.add(item);
    }

    public void deleteAllItem(){ group_items.clear();}


    private void showPopupForSet(View v, final int position) {
        PopupMenu popup = new PopupMenu(v.getContext(), v);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.Group_Delete_Action:


                        String groupId = group_items.get(position).getGroupId();

                        final DocumentReference sfDocRef = firebaseinit.firebaseFirestore.collection("memberGroups").document(UserInfo.user_Id);
                        final DocumentReference sfDocRef2 = firebaseinit.firebaseFirestore.collection("groupMembers").document(groupId);
                        final DocumentReference groupsDocRef = firebaseinit.firebaseFirestore.collection("groups").document(groupId);

                        firebaseinit.firebaseFirestore.runTransaction(new Transaction.Function<Void>() {
                            @Override
                            public Void apply(Transaction transaction) throws FirebaseFirestoreException {




                                DocumentSnapshot snapshot = transaction.get(sfDocRef);
                                DocumentSnapshot snapshot2 = transaction.get(sfDocRef2);


                                ArrayList<String> newArray = (ArrayList<String>)snapshot.getData().get("grouplist");
                                ArrayList<String> newArray2 = (ArrayList<String>)snapshot2.getData().get("memberlist");

                                Map<String, Object> docData = new HashMap<>();



                                newArray.remove(groupId);
                                newArray2.remove(UserInfo.user_Id);
                                int newCount = newArray2.size();
                                if(newCount <= 0){ // 그룹 삭제하기.
                                    docData.put("groupId", FieldValue.delete());
                                    docData.put("groupIntro", FieldValue.delete());
                                    docData.put("groupName", FieldValue.delete());
                                    docData.put("groupOpen", FieldValue.delete());
                                    docData.put("groupTag", FieldValue.delete());
                                    docData.put("leaderName", FieldValue.delete());
                                    docData.put("leaderUid", FieldValue.delete());
                                    docData.put("mem_limit", FieldValue.delete());
                                    docData.put("mem_num", FieldValue.delete());

                                    groupsDocRef.delete()
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Log.d(TAG,"DocumentSnapshot Successfully deleted");
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.w(TAG,"Error deleting document", e);
                                        }
                                    });


                                }else {
                                    docData.put("mem_num", newCount);
                                }

                                groupsDocRef.update(docData);// 인원수 빼기.

                                transaction.update(sfDocRef,"grouplist",newArray);
                                transaction.update(sfDocRef2,"memberlist",newArray2);


                                return null;
                            }
                        }).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "Transaction success!");


                                Toast.makeText(v.getContext(), "삭제" + position,
                                        Toast.LENGTH_SHORT).show();
                                group_items.remove(position);
                                notifyDataSetChanged();
//                        Toast.makeText(CreateGroupActivity.this, "Transaction success!", Toast.LENGTH_SHORT).show();
//                        finish();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Transaction failure.", e);
//                        Toast.makeText(CreateGroupActivity.this, "Transaction failure!", Toast.LENGTH_SHORT).show();


                            }
                        });

                        return true;

                    default:
                        return false;
                }
            }
        });

        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.group_delete_menu, popup.getMenu());
        popup.show();
    }





}

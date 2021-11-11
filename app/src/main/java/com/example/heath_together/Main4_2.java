package com.example.heath_together;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import com.example.heath_together.Adapter.ProfileListViewAdapter;
import com.example.heath_together.FirebaseInit.firebaseinit;
import com.example.heath_together.Object.DTO.ProfileListItem;
import com.example.heath_together.UserInfo.UserInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;


public class Main4_2 extends Fragment {

    private View view;
    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "main4_2 onCreate");
    }
    @Override
    public void onStart(){
        super.onStart();
        Log.d(TAG, "main4_2 onStart");
        DocumentReference docRef = firebaseinit.firebaseFirestore.collection("myPageExerciseList").document(UserInfo.user_Id);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()){

                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "main4_2 onCreateView");
        view = inflater.inflate(R.layout.main4_2, container, false);

        ListView listView = (ListView)view.findViewById(R.id.profile_listView);
        Button btn_create_list = (Button)view.findViewById(R.id.profile_btn_list);

        ProfileListViewAdapter adapter = new ProfileListViewAdapter();

        adapter.addItem(new ProfileListItem("김계란의 가슴운동", "2"));
        adapter.addItem(new ProfileListItem("김종국의 하체운동", "3"));
        listView.setAdapter(adapter);
        setListViewHeightBasedOnChildren(listView);

        btn_create_list.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(getContext(), main4_2_CreateList.class);
//                startActivity(intent);
                Fragment fragment_Main4_2_CreateList = new main4_2_CreateList();
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.main_frame, fragment_Main4_2_CreateList)
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);

        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}
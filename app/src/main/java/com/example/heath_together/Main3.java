package com.example.heath_together;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.heath_together.Adapter.ListItemAdapter;
import com.example.heath_together.Adapter.RecyclerViewItemAdapter;
import com.example.heath_together.Adapter.SearchGAdapter;
import com.example.heath_together.FirebaseInit.firebaseinit;
import com.example.heath_together.Object.DTO.AccountListItem;
import com.example.heath_together.Object.DTO.GroupListItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.security.acl.Group;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Main3 extends Fragment {
    private static String TAG = "Main3";
    //GroupScActivitiy
    private Context context;
    private RecyclerView recyclerView;
    private String text; //검색 내용 입력받는 Text
    private String newText2;


    private RecyclerView.LayoutManager layoutManager;
    private String result;
    private View view;
    private Button moveButton;






    private ImageButton searchButton;
    private EditText searchText;

    SearchGAdapter groupAdapter;
    RecyclerViewItemAdapter accountAdapter;


    ArrayList<AccountListItem> accountList = new ArrayList<>();
    ArrayList<GroupListItem> grouplist = new ArrayList<>();
    MainActivity mainActivity;

    int moveBut_flag = 1;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivity = (MainActivity) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mainActivity = null;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main3, container, false);
        moveBut_flag=1;
        grouplist.clear();
//        newText2 = null; //검색 연결 초기화.
//        groupList.clear();
//        fillGroupList();
//        accountList.clear();
//        fillAccountList();\

        ArrayList<GroupListItem> list = new ArrayList<GroupListItem>();





//        Log.d(TAG, "onCreateView" + groupList.toString());


//        setUpReCyclerView();
        setHasOptionsMenu(true);
        context = container.getContext();

        searchText = view.findViewById(R.id.search_text);
        searchButton=view.findViewById(R.id.search_button_main3);
        moveButton = view.findViewById(R.id.changeAcButton);


        recyclerView = view.findViewById(R.id.group_list);   // group aacount 둘다 보여주는 리사이클러뷰 -> group_list
        recyclerView.setHasFixedSize(true);



        searchButton.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View view){

                      accountList.clear();
                      grouplist.clear();
                     text = searchText.getText().toString();
                if(moveBut_flag==1){
                    //그룹 검색

                    groupSearchFirebase();




//                    recyclerView.findViewById(R.id.group_list);

                }
                else{





                    accountSearchFirebase();



                    //계정 검색


                }







            }

        });

        moveButton.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View view) {
                if(moveBut_flag==1){
                    moveBut_flag=0;
                    moveButton.setText("계정 |");

                }
                else{
                    moveBut_flag=1;

                    moveButton.setText("그룹 |");
                }

            }
        });

        return view;
    }//onCreateView






    public void groupSearchFirebase(){

        firebaseinit.firebaseFirestore.collection("groups")
                .orderBy("groupName").startAt(text).endAt(text+"\uf8ff")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                GroupListItem item = document.toObject(GroupListItem.class);

                                grouplist.add(item);

                                groupAdapter = new SearchGAdapter(grouplist);
                                recyclerView.setAdapter(groupAdapter);
                                recyclerView.setFocusable(false);

                            }
                            layoutManager = new LinearLayoutManager(getActivity());
                            recyclerView.setLayoutManager(layoutManager);

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

    }

    public void accountSearchFirebase(){

        firebaseinit.firebaseFirestore.collection("users")
                .orderBy("userName").startAt(text).endAt(text+"\uf8ff")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                AccountListItem item = document.toObject(AccountListItem.class);

                                accountList.add(item);

                                accountAdapter = new RecyclerViewItemAdapter(accountList);
                                recyclerView.setAdapter(accountAdapter);
                                recyclerView.setFocusable(false);

                            }
                            layoutManager = new GridLayoutManager(getActivity(),3);
                            recyclerView.setLayoutManager(layoutManager);

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

    }









}


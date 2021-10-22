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
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.heath_together.Adapter.RecyclerViewItemAdapter;
import com.example.heath_together.Adapter.SearchGAdapter;
import com.example.heath_together.Object.DTO.AccountListItem;
import com.example.heath_together.Object.DTO.GroupListItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main3 extends Fragment implements SearchView.OnQueryTextListener{
    private static String TAG = "Main3";
    //GroupScActivitiy
    private Context context;
    private SearchView searchView;
    private RecyclerView recyclerView;

    private String newText2;


    private RecyclerViewItemAdapter adapter;
    private SearchGAdapter searchGAdapter;                                          // 1.
    private RecyclerView.LayoutManager layoutManager;
    private String result;
    private View view;
    private FloatingActionButton moveButton;

    List<GroupListItem> groupList = new ArrayList<GroupListItem>();
    List<AccountListItem> accountList = new ArrayList<AccountListItem>();
    ArrayList<String> list = new ArrayList<>();
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
        newText2 = null; //검색 연결 초기화.
        groupList.clear();
        fillGroupList();
        accountList.clear();
        fillAccountList();


        Log.d(TAG, "onCreateView" + groupList.toString());


        setUpReCyclerView();
        setHasOptionsMenu(true);
        context = container.getContext();


        moveButton = view.findViewById(R.id.changeAcButton);




        moveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(moveBut_flag==1){
                    moveBut_flag=0;
                    moveButton.setImageResource(R.drawable.ic_baseline_location_searching_24);

                }
                else{
                    moveBut_flag=1;
                    moveButton.setImageResource(R.drawable.ic_baseline_person_search_24);
                }
                setUpReCyclerView();
            }
        });

        return view;
    } //onCreateView

    private void setUpReCyclerView() {
        if(moveBut_flag==1) {
            //리사이클러뷰에 그룹들을 나열




            searchGAdapter = new SearchGAdapter(groupList, getActivity());
            searchGAdapter.getFilter().filter(newText2);

            recyclerView = view.findViewById(R.id.group_list);  //Main3 서칭 그룹리스트                               2.
            recyclerView.setHasFixedSize(true); //뭔지 모르겠음.
            layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(searchGAdapter);
            groupList.clear();
            fillGroupList();


        }else{

            //리사이클러뷰에 계정들을 나열

            adapter = new RecyclerViewItemAdapter(accountList) ;
            adapter.getFilter().filter(newText2);

            recyclerView = view.findViewById(R.id.group_list) ;
            recyclerView.setHasFixedSize(true);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),3);
            recyclerView.setLayoutManager(gridLayoutManager) ;
            recyclerView.setAdapter(adapter) ;

            accountList.clear();
            fillAccountList();

            groupList.clear();
            fillGroupList();





        }



    }


    public void fillGroupList() {


        GroupListItem p0 = new GroupListItem("oke곡크루", "웨이트", "강승우","d", 4, 25);
        GroupListItem p1 = new GroupListItem("머슐랭", "다이어트", "금윤수","d", 100, 100);
        GroupListItem p2 = new GroupListItem("mer슐랭", "다이어트", "금윤수","d", 100, 100);
        GroupListItem p3 = new GroupListItem("머슐랭", "다이어트", "금윤수","D", 100, 100);
        GroupListItem p4 = new GroupListItem("머슐랭", "다이어트", "금윤수","d", 100, 100);




        groupList.addAll(Arrays.asList(new GroupListItem[]{p0, p1, p2, p3, p4}));
    }

    public void fillAccountList(){

        AccountListItem a0 = new AccountListItem("금윤수");
        AccountListItem a1 = new AccountListItem("김상근");
        AccountListItem a2 = new AccountListItem("mer훈석");
        AccountListItem a3 = new AccountListItem("강승우");

        accountList.addAll(Arrays.asList(new AccountListItem[]{a0,a1,a2,a3}));

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();

        inflater.inflate(R.menu.search_menu, menu);
        MenuItem item = menu.findItem(R.id.app_bar_search);

        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItem.SHOW_AS_ACTION_IF_ROOM);

        searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(this);


        accountList.clear();
        fillAccountList();


    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if(moveBut_flag==1){

            searchGAdapter.getFilter().filter(newText);

        }
        else{



            adapter.getFilter().filter(newText);
        }
        newText2 = newText;



        return false;
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }


}


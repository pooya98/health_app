package com.example.heath_together;

import android.content.Context;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.heath_together.Adapter.HealthItemAdapter;
import com.example.heath_together.Adapter.HealthListItemAdapter;
import com.example.heath_together.Object.DTO.HealthItem;
import com.example.heath_together.Object.DTO.HealthListItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main1_1 extends Fragment {

    private View view;
    private Context context;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private boolean isOpen= false;
    Animation cOpen, cClose,rotateForward, rotateBackward;


    private HealthItemAdapter healthItemAdapter;
    private HealthListItemAdapter healthListItemAdapter;

    public static Button countButton;
    private  Button healthButton;
    private Button healthListButton;

    private FloatingActionButton healthPlusButton;
    private FloatingActionButton categoriButton ;
    private FloatingActionButton chestButton ;
    private FloatingActionButton shoulderButton ;
    private FloatingActionButton legButton ;
    private FloatingActionButton backButton ;
    private FloatingActionButton guitarButton;




    List<HealthItem> healthItemList = new ArrayList<HealthItem>();
    List<HealthListItem> healthListList = new ArrayList<HealthListItem>();



    public Main1_1() {
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main1_1, container, false);

        isOpen=false;
        healthItemList.clear();
        healthListList.clear();
        fillChestItemList();
        fillHealthListList();


        healthPlusButton = view.findViewById(R.id.healthPlus);
        countButton = view.findViewById(R.id.countButton);

        categoriButton = view.findViewById(R.id.changeCategori);
        chestButton = view.findViewById(R.id.changeChest);
        shoulderButton = view.findViewById(R.id.changeShoulder);
        backButton = view.findViewById(R.id.changeBack);
        legButton = view.findViewById(R.id.changeLeg);
        guitarButton = view.findViewById(R.id.changeGuitar);

        cOpen = AnimationUtils.loadAnimation(getContext(),R.anim.c_open);
        cClose = AnimationUtils.loadAnimation(getContext(),R.anim.c_close);

        rotateBackward = AnimationUtils.loadAnimation(getContext(),R.anim.rotate_backward);
        rotateForward = AnimationUtils.loadAnimation(getContext(),R.anim.rotate_forward);
        setUpHealthReCyclerView();

        shoulderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                healthItemList.clear();
                fillShoulderItemList();
                setUpHealthReCyclerView();
            }
        });
        legButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                healthItemList.clear();
                fillLegItemList();
                setUpHealthReCyclerView();
            }
        });
        chestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                healthItemList.clear();
                fillChestItemList();
                setUpHealthReCyclerView();
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                healthItemList.clear();
                fillBackItemList();
                setUpHealthReCyclerView();
            }
        });
        guitarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                healthItemList.clear();
                fillGuitarItemList();
                setUpHealthReCyclerView();
            }
        });

        healthButton = view.findViewById(R.id.healthButton);
        healthButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                setUpHealthReCyclerView();
            }
        });


        healthListButton = view.findViewById(R.id.healthListButton);
        healthListButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                setUpHealthListReCyclerView();
            }
        });


        return view;
    }


    private void setUpHealthReCyclerView(){

        countButton.setText("+");
        healthItemAdapter.mSelectedItems =new SparseBooleanArray(0);
        healthItemAdapter = new HealthItemAdapter(healthItemList) ;
        recyclerView = view.findViewById(R.id.health_list) ;
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager) ;
        recyclerView.setAdapter(healthItemAdapter) ;

        healthPlusButton.setVisibility(View.VISIBLE);
        categoriButton.setVisibility(View.VISIBLE);
        categoriButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                animateCategori();

            }
        });

    }

    private void setUpHealthListReCyclerView(){

        healthPlusButton.setVisibility(View.INVISIBLE);
        categoriButton.setVisibility(View.INVISIBLE);
        chestButton.startAnimation(cClose);
        shoulderButton.startAnimation(cClose);
        legButton.startAnimation(cClose);
        guitarButton.startAnimation(cClose);
        backButton.startAnimation(cClose);

        backButton.setClickable(false);
        chestButton.setClickable(false);
        shoulderButton.setClickable(false);
        guitarButton.setClickable(false);
        legButton.setClickable(false);
        isOpen=false;




        healthListItemAdapter = new HealthListItemAdapter(healthListList) ;
        recyclerView = view.findViewById(R.id.health_list) ;
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager) ;
        recyclerView.setAdapter(healthListItemAdapter) ;



    }


    public void fillChestItemList(){

        HealthItem a0 = new HealthItem("벤치 프레스");
        HealthItem a1 = new HealthItem("덤벨 프레스");
        HealthItem a2 = new HealthItem("중량 푸쉬업");
        HealthItem a3 = new HealthItem("중량 딥스");
        HealthItem a4 = new HealthItem("강승우식 벤치프레스");
        HealthItem a5 = new HealthItem("금윤수식 벤치프레스");
        HealthItem a6 = new HealthItem("맥주 마시고싶다");
        HealthItem a7 = new HealthItem("치킨 버어억");


        healthItemList.addAll(Arrays.asList(new HealthItem[]{a0,a1,a2,a3,a4,a5,a6,a7}));

    }

    public void fillShoulderItemList(){

        HealthItem a0 = new HealthItem("숄더 프레스");
        HealthItem a1 = new HealthItem("바벨 프레스");
        HealthItem a2 = new HealthItem("밀리터리 프레스");
        HealthItem a3 = new HealthItem("사이드 레터럴 레이즈");
        HealthItem a4 = new HealthItem("프론트 레이즈");
        HealthItem a5 = new HealthItem("강승우식 어깨 박살내기");
        HealthItem a6 = new HealthItem("어깨 죽여버려");
        HealthItem a7 = new HealthItem("뜨으아앗");


        healthItemList.addAll(Arrays.asList(new HealthItem[]{a0,a1,a2,a3,a4,a5,a6,a7}));

    }

    public void fillLegItemList(){

        HealthItem a0 = new HealthItem("스쿼트");
        HealthItem a1 = new HealthItem("레그 익스텐션");
        HealthItem a2 = new HealthItem("레그 컬");
        HealthItem a3 = new HealthItem("레그 프레스");
        HealthItem a4 = new HealthItem("닭다리 먹고싶다");
        HealthItem a5 = new HealthItem("치킨치킨 음 치킨");
        HealthItem a6 = new HealthItem("눈이 감긴다");


        healthItemList.addAll(Arrays.asList(new HealthItem[]{a0,a1,a2,a3,a4,a5,a6}));

    }


    public void fillBackItemList(){

        HealthItem a0 = new HealthItem("중량 턱걸이");
        HealthItem a1 = new HealthItem("바벨 로우");
        HealthItem a2 = new HealthItem("덤벨 로우");
        HealthItem a3 = new HealthItem("랫 풀 다운");
        HealthItem a4 = new HealthItem("데드 리프트");
        HealthItem a5 = new HealthItem("나도 운동하고싶다");
        HealthItem a6 = new HealthItem("시험 언제끝날까");


        healthItemList.addAll(Arrays.asList(new HealthItem[]{a0,a1,a2,a3,a4,a5,a6}));

    }

    public void fillGuitarItemList(){

        HealthItem a0 = new HealthItem("행잉 레그레이즈");
        HealthItem a1 = new HealthItem("레그레이즈");
        HealthItem a2 = new HealthItem("금윤수식 복근운동");


        healthItemList.addAll(Arrays.asList(new HealthItem[]{a0,a1,a2}));

    }






















    public void fillHealthListList(){

        HealthListItem a0 = new HealthListItem("가슴 폭발 루틴",4);
        HealthListItem a1 = new HealthListItem("어깨 박살 루틴",6);
        HealthListItem a2 = new HealthListItem("복근 초토화 루틴",3);
        HealthListItem a3 = new HealthListItem("하체 분쇄 루틴",9);
        HealthListItem a4 = new HealthListItem("전신 마취 루틴",10);
        HealthListItem a5 = new HealthListItem("아무거나 루틴",11);



        healthListList.addAll(Arrays.asList(new HealthListItem[]{a0,a1,a2,a3,a4,a5}));

    }
    private void animateCategori(){
        if(isOpen){

            categoriButton.startAnimation(rotateForward);
            chestButton.startAnimation(cClose);
            shoulderButton.startAnimation(cClose);
            legButton.startAnimation(cClose);
            guitarButton.startAnimation(cClose);
            backButton.startAnimation(cClose);

            backButton.setClickable(false);
            chestButton.setClickable(false);
            shoulderButton.setClickable(false);
            guitarButton.setClickable(false);
            legButton.setClickable(false);
            isOpen=false;
        }
        else{
            categoriButton.startAnimation(rotateBackward);
            chestButton.startAnimation(cOpen);
            shoulderButton.startAnimation(cOpen);
            legButton.startAnimation(cOpen);
            guitarButton.startAnimation(cOpen);
            backButton.startAnimation(cOpen);

            backButton.setClickable(true);
            chestButton.setClickable(true);
            shoulderButton.setClickable(true);
            guitarButton.setClickable(true);
            legButton.setClickable(true);
            isOpen=true;
        }



    }
}

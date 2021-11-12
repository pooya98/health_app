package com.example.heath_together;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.example.heath_together.UserInfo.UserInfo;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


public class Main4_4 extends Fragment {

    String[] items = {"가슴", "등", "어깨", "하체", "기타"};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    String profileId;
    String profileUserId;
    public Main4_4(String userId){

        this.profileUserId= userId;

    }
    public Main4_4(){

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fv = inflater.inflate(R.layout.main4_4, container, false);

        if(profileUserId==null){
            profileId= UserInfo.user_Id;
        }else{                                                 //내 계정, 상대 계정 프로필 구분.
            profileId=profileUserId;
        }

        Spinner spinner = fv.findViewById(R.id.spin2);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);



        BarChart chart = fv.findViewById(R.id.barchart);
        ArrayList NoOfEmp = new ArrayList();
        NoOfEmp.add(new BarEntry(50f, 0));
        NoOfEmp.add(new BarEntry(100f, 1));
        NoOfEmp.add(new BarEntry(113f, 2));
        NoOfEmp.add(new BarEntry(120f, 3));
        NoOfEmp.add(new BarEntry(139f, 4));
        NoOfEmp.add(new BarEntry(147f, 5));
        NoOfEmp.add(new BarEntry(151f, 6));
        NoOfEmp.add(new BarEntry(165f, 7));
        NoOfEmp.add(new BarEntry(178f, 8));
        NoOfEmp.add(new BarEntry(195f, 9));
        ArrayList year = new ArrayList();
        year.add("1월"); year.add("2월");
        year.add("3월"); year.add("4월");
        year.add("5월"); year.add("6월");
        year.add("7월"); year.add("8월");
        year.add("9월"); year.add("10월");
        BarDataSet bardataset = new BarDataSet(NoOfEmp, "운동량(set수)");
        chart.animateY(3000);
        BarData data = new BarData(year, bardataset);   // MPAndroidChart v3.X 오류 발생
        bardataset.setColors(ColorTemplate.LIBERTY_COLORS);
        chart.setData(data);

        Button button = (Button)fv.findViewById(R.id.monthButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                ArrayList NoOfEmp = new ArrayList();
                NoOfEmp.add(new BarEntry(50f, 0));
                NoOfEmp.add(new BarEntry(100f, 1));
                NoOfEmp.add(new BarEntry(113f, 2));
                NoOfEmp.add(new BarEntry(120f, 3));
                NoOfEmp.add(new BarEntry(139f, 4));
                NoOfEmp.add(new BarEntry(147f, 5));
                NoOfEmp.add(new BarEntry(151f, 6));
                NoOfEmp.add(new BarEntry(165f, 7));
                NoOfEmp.add(new BarEntry(178f, 8));
                NoOfEmp.add(new BarEntry(195f, 9));
                ArrayList year = new ArrayList();
                year.add("1월"); year.add("2월");
                year.add("3월"); year.add("4월");
                year.add("5월"); year.add("6월");
                year.add("7월"); year.add("8월");
                year.add("9월"); year.add("10월");
                BarDataSet bardataset = new BarDataSet(NoOfEmp, "운동량(set수)");
                chart.animateY(3000);
                BarData data = new BarData(year, bardataset);   // MPAndroidChart v3.X 오류 발생
                bardataset.setColors(ColorTemplate.LIBERTY_COLORS);
                chart.setData(data);
            }
        });

        button = (Button)fv.findViewById(R.id.yearButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                ArrayList NoOfEmp = new ArrayList();
                NoOfEmp.add(new BarEntry(430f, 0));
                NoOfEmp.add(new BarEntry(150f, 1));
                NoOfEmp.add(new BarEntry(300f, 2));
                NoOfEmp.add(new BarEntry(530f, 3));
                NoOfEmp.add(new BarEntry(180f, 4));
                NoOfEmp.add(new BarEntry(430f, 5));
                NoOfEmp.add(new BarEntry(123f, 6));
                NoOfEmp.add(new BarEntry(165f, 7));
                NoOfEmp.add(new BarEntry(206f, 8));
                NoOfEmp.add(new BarEntry(500f, 9));
                ArrayList year = new ArrayList();
                year.add("2012"); year.add("2013");
                year.add("2014"); year.add("2015");
                year.add("2016"); year.add("2017");
                year.add("2018"); year.add("2019");
                year.add("2020"); year.add("2021");
                BarDataSet bardataset = new BarDataSet(NoOfEmp, "운동량(set수)");
                chart.animateY(3000);
                BarData data = new BarData(year, bardataset);   // MPAndroidChart v3.X 오류 발생
                bardataset.setColors(ColorTemplate.LIBERTY_COLORS);
                chart.setData(data);
            }
        });


        return fv;
    }
}
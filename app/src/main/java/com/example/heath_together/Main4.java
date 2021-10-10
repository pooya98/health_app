package com.example.heath_together;

import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class Main4 extends Fragment implements View.OnClickListener {

    private View view;

    RadioButton radioButton_Calendar;
    RadioButton radioButton_List;
    RadioButton radioButton_Exercise;
    RadioButton radioButton_Static;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main4, container, false);

        radioButton_Calendar = (RadioButton)view.findViewById(R.id.Main4_Radio_Calendar);
        radioButton_List = (RadioButton)view.findViewById(R.id.Main4_Radio_List);
        radioButton_Exercise = (RadioButton)view.findViewById(R.id.Main4_Radio_Exercise);
        radioButton_Static = (RadioButton)view.findViewById(R.id.Main4_Radio_Static);

        radioButton_Calendar.setOnClickListener(this);
        radioButton_List.setOnClickListener(this);
        radioButton_Exercise.setOnClickListener(this);
        radioButton_Static.setOnClickListener(this);

        setChildFragment(new Main4_1());

        return view;
    }

    @Override
    public void onClick(View view) {
        int _id = view.getId();

        Fragment fg;

        switch (_id){
            case R.id.Main4_Radio_Calendar:
                fg = new Main4_1();
                setChildFragment(fg);
                break;
            case R.id.Main4_Radio_List:
                fg = new Main4_2();
                setChildFragment(fg);
                break;
            case R.id.Main4_Radio_Exercise:
                fg = new Main4_3();
                setChildFragment(fg);
                break;
            case R.id.Main4_Radio_Static:
                fg = new Main4_4();
                setChildFragment(fg);
                break;
        }
    }

    private void setChildFragment(Fragment child) {
        FragmentTransaction childFt = getChildFragmentManager().beginTransaction();

        if (!child.isAdded()) {
            childFt.replace(R.id.main4_frame, child);
            childFt.addToBackStack(null);
            childFt.commit();
        }
    }
}

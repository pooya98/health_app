package com.example.heath_together;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;


public class Main4_3 extends Fragment {

    private View view;
    private Context context;
    private Button createExerciseButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.main4_3, container, false);

        createExerciseButton = view.findViewById(R.id.create_exercise_button);

        createExerciseButton.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), main4_3_CreateExercise.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
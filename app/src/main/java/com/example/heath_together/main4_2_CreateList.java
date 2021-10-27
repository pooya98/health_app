package com.example.heath_together;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class main4_2_CreateList extends AppCompatActivity {

    private FloatingActionButton exercisePlusButton;
    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.main4_2_create_list);

        exercisePlusButton = findViewById(R.id.exercise_plus_button);

        exercisePlusButton.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(main4_2_CreateList.this, main4_2_CreateListList.class);
                startActivity(intent);
            }
        });




        //listview


    }
}

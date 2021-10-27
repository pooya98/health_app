package com.example.heath_together;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class main4_3_CreateExercise extends AppCompatActivity {

    private View view;
    private Context context;

    String[] items = {"가슴", "등", "어깨", "하체","기타"};



    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.main4_3_create_exercise);

        Spinner spin = findViewById(R.id.spin);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);



    }
}

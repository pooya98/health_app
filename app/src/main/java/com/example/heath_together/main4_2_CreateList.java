package com.example.heath_together;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class main4_2_CreateList extends AppCompatActivity {

    String[] items = {"대한민국", "일본", "중국", "미국"};

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.main4_2_create_list);

        Spinner spinner = findViewById(R.id.spinner);
        ListView listView = findViewById(R.id.CreatList_ListView);


        //spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        //listview

    }
}

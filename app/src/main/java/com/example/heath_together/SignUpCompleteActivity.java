package com.example.heath_together;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SignUpCompleteActivity extends Activity implements View.OnClickListener {


    private Button Button_Confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_complete);

        Button_Confirm = findViewById(R.id.SignUpComplete_Confirm);
        Button_Confirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int _id = view.getId();

        if (_id == R.id.SignUpComplete_Confirm) {
            finish();
        }
    }
}
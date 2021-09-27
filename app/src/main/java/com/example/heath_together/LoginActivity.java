package com.example.heath_together;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends Activity implements OnClickListener {

    private Button btn_GoSignUp;
    private Button btn_DoLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_GoSignUp = findViewById(R.id.btn_GoSignUp);
        btn_DoLogin = findViewById(R.id.btn_DoLogin);

        btn_GoSignUp.setOnClickListener(this);
        btn_DoLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int _id = v.getId();

        if (_id == R.id.btn_GoSignUp) {
            startSignUpActivity();
        }
        else if(_id == R.id.btn_DoLogin){
            startMainActivity();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private void startSignUpActivity(){
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
    }
    private void startMainActivity(){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
package com.example.heath_together;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.heath_together.Dialog.LoadingDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends Activity implements OnClickListener {

    private FirebaseAuth mAuth;
    private static final String TAG = "LoginActivity";

    private EditText EditText_Email;
    private EditText EditText_Password;
    private Button Button_DoLogin;
    private Button Button_GoSignUp;

    LoadingDialog customLoadingDialog;


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            // reload();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        EditText_Email = findViewById(R.id.Login_Email);
        EditText_Password = findViewById(R.id.Login_Password);
        Button_DoLogin = findViewById(R.id.Login_DoLogin);
        Button_GoSignUp = findViewById(R.id.Login_GoSignUp);

        Button_DoLogin.setOnClickListener(this);
        Button_GoSignUp.setOnClickListener(this);

        Button_DoLogin.setClickable(false);

        //로딩창 객체 생성
        customLoadingDialog = new LoadingDialog(this);
        //로딩창을 투명하게
        customLoadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        customLoadingDialog.setCancelable(false);



        EditText_Email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0 && EditText_Password.getText().toString().length() > 0) {
                    Button_DoLogin.setClickable(true);
                    Button_DoLogin.setBackgroundResource(R.drawable.edge_activated);
                } else {
                    Button_DoLogin.setClickable(false);
                    Button_DoLogin.setBackgroundResource(R.drawable.edge_inactivated);
                }
            }
        });

        EditText_Password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0 && EditText_Email.getText().toString().length() > 0) {
                    Button_DoLogin.setClickable(true);
                    Button_DoLogin.setBackgroundColor(Color.BLUE);
                    Button_DoLogin.setBackgroundResource(R.drawable.edge_activated);
                } else {
                    Button_DoLogin.setClickable(false);
                    Button_DoLogin.setBackgroundResource(R.drawable.edge_inactivated);
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        int _id = v.getId();

        if (_id == R.id.Login_GoSignUp) {
            startSignUpActivity();
        }
        else if(_id == R.id.Login_DoLogin){
            Login();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private void Login(){

        customLoadingDialog.show();

        String email = EditText_Email.getText().toString();
        String password = EditText_Password.getText().toString();


        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public  void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            customLoadingDialog.dismiss();
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            startMainActivity();
                        } else {
                            customLoadingDialog.dismiss();
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "로그인 정보를 잘못 입력하였습니다.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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
package com.example.heath_together;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.example.heath_together.Dialog.LoadingDialog;
import com.example.heath_together.FirebaseInit.firebaseinit;
import com.example.heath_together.Object.DTO.UserItem;
import com.example.heath_together.UserInfo.UserInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

public class LoginActivity extends Activity implements OnClickListener {

    private static final String TAG = "LoginActivity";

    //private FirebaseAuth mAuth;

    private EditText EditText_Email;
    private EditText EditText_Password;
    private Button Button_DoLogin;
    private TextView TextViewButton_GoSignUp;

    LoadingDialog customLoadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText_Email = findViewById(R.id.Login_Email);
        EditText_Password = findViewById(R.id.Login_Password);
        Button_DoLogin = findViewById(R.id.Login_DoLogin);
        TextViewButton_GoSignUp = findViewById(R.id.Login_GoSignUp);

        Button_DoLogin.setOnClickListener(this);
        TextViewButton_GoSignUp.setOnClickListener(this);

        Button_DoLogin.setClickable(false);

        firebaseinit.doFirebaseInit();

        //로딩창 객체 생성
        customLoadingDialog = new LoadingDialog(this);
        //로딩창을 투명하게
        customLoadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        customLoadingDialog.setCancelable(false);

        SharedPreferences UserInfo = getSharedPreferences("UserInfo", MODE_PRIVATE);

        if(UserInfo.getString("id","") != null && UserInfo.getString("pw","") != null){
            if(UserInfo.getString("id","").length() > 0 && UserInfo.getString("pw","").length() > 0){
                EditText_Email.setText(UserInfo.getString("id",""));
                EditText_Password.setText(UserInfo.getString("pw",""));
                Login();
            }
        }

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
//            startMainActivity();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private void Login(){
        customLoadingDialog.show();

        //mAuth = FirebaseAuth.getInstance();

        String email = EditText_Email.getText().toString();
        String password = EditText_Password.getText().toString();

        // mAuth.signInWithEmailAndPassword(email, password)

        firebaseinit.firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public  void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            customLoadingDialog.dismiss();
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            //FirebaseUser user = mAuth.getCurrentUser();
                            firebaseinit.firebaseUser = firebaseinit.firebaseAuth.getCurrentUser();

                            //FirebaseFirestore db = FirebaseFirestore.getInstance();

                            SharedPreferences UserInfo_sf = getSharedPreferences("UserInfo", MODE_PRIVATE);
                            SharedPreferences.Editor editor = UserInfo_sf.edit();

                            //if(user != null && db != null) {

                            if(firebaseinit.firebaseUser != null && firebaseinit.firebaseFirestore != null) {
                                DocumentReference docRef = firebaseinit.firebaseFirestore.collection("users").document(firebaseinit.firebaseUser.getUid());

                                docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                    @Override
                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                        UserItem userItem = documentSnapshot.toObject(UserItem.class);

                                        editor.putString("id", EditText_Email.getText().toString());
                                        editor.putString("pw", EditText_Password.getText().toString());
                                        editor.putString("userName", userItem.getUserName());
                                        editor.putString("userUid", userItem.getUid());
                                        UserInfo.user_Id = userItem.getUid();
                                        UserInfo.user_Name = userItem.getUserName();
                                        editor.commit();
                                        startMainActivity();
                                    }
                                });
                            }
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
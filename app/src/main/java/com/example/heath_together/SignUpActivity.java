package com.example.heath_together;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.heath_together.Dialog.LoadingDialog;
import com.example.heath_together.Object.DTO.UserItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class SignUpActivity  extends Activity implements View.OnClickListener {

    private static final String TAG = "SignUpActivity";

    private static final int USER_NAME_MIN_LEN = 3;
    private static final int PW_MIN_LEN = 6;

    private EditText EditText_UserName;
    private EditText EditText_Email;
    private EditText EditText_Password;
    private EditText EditText_PasswordCheck;
    private Button Button_DoSignUp;
    private Button Button_Cancel;

    private FirebaseAuth mAuth;

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
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        EditText_UserName = findViewById(R.id.SignUp_UserName);
        EditText_Email = findViewById(R.id.SignUp_Email);
        EditText_Password = findViewById(R.id.SignUp_password);
        EditText_PasswordCheck = findViewById(R.id.SignUp_PasswordCheck);
        Button_DoSignUp = findViewById(R.id.SignUp_DoSignUp);
        Button_Cancel = findViewById(R.id.SignUp_Cancel);

        Button_DoSignUp.setOnClickListener(this);
        Button_Cancel.setOnClickListener(this);

        Button_DoSignUp.setClickable(false);

        //로딩창 객체 생성
        customLoadingDialog = new LoadingDialog(this);
        //로딩창을 투명하게
        customLoadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        customLoadingDialog.setCancelable(false);



        EditText_UserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                if (check_form()) {
                    Button_DoSignUp.setClickable(true);
                    Button_DoSignUp.setBackgroundResource(R.drawable.edge_activated);
                } else {
                    Button_DoSignUp.setClickable(false);
                    Button_DoSignUp.setBackgroundResource(R.drawable.edge_inactivated);
                }
            }
        });

        EditText_Email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                if (check_form()) {
                    Button_DoSignUp.setClickable(true);
                    Button_DoSignUp.setBackgroundResource(R.drawable.edge_activated);
                } else {
                    Button_DoSignUp.setClickable(false);
                    Button_DoSignUp.setBackgroundResource(R.drawable.edge_inactivated);
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
                if (check_form()) {
                    Button_DoSignUp.setClickable(true);
                    Button_DoSignUp.setBackgroundResource(R.drawable.edge_activated);
                } else {
                    Button_DoSignUp.setClickable(false);
                    Button_DoSignUp.setBackgroundResource(R.drawable.edge_inactivated);
                }
            }
        });

        EditText_PasswordCheck.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                if (check_form()) {
                    Button_DoSignUp.setClickable(true);
                    Button_DoSignUp.setBackgroundResource(R.drawable.edge_activated);
                } else {
                    Button_DoSignUp.setClickable(false);
                    Button_DoSignUp.setBackgroundResource(R.drawable.edge_inactivated);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        int _id = view.getId();

        if (_id == R.id.SignUp_DoSignUp) {
            if(EditText_Password.getText().toString().equals(EditText_PasswordCheck.getText().toString())){
                customLoadingDialog.show();
                SignUp();
            }else{
                Toast.makeText(SignUpActivity.this, "비밀번호가 일치하지 않습니다.",
                        Toast.LENGTH_SHORT).show();
            }
        }
        else if(_id == R.id.SignUp_Cancel){
            finish();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private void SignUp() {

        String name = EditText_UserName.getText().toString();
        String email = EditText_Email.getText().toString();
        String password = EditText_Password.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();


                            FirebaseFirestore db = FirebaseFirestore.getInstance();

                            Map<String, Object> docData = new HashMap<>();
                            docData.put("grouplist", Arrays.asList());

                            db.collection("memberGroups").document(user.getUid()).set(docData);
                            FirebaseStorage storage = FirebaseStorage.getInstance();
                            Uri uri = Uri.parse("android.resource://com.example.heath_together/drawable/profile");

                            StorageReference storageRef = storage.getReference();
                            StorageReference riverRef = storageRef.child("userProfilePhoto/"+user.getUid()+".jpg");
                            UploadTask uploadTask = riverRef.putFile(uri);

                            uploadTask.addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    Toast.makeText(SignUpActivity.this, "사진이 정상적으로 업로드 되지 않았습니다." ,Toast.LENGTH_SHORT).show();
                                }
                            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Toast.makeText(SignUpActivity.this, "사진이 정상적으로 업로드 되었습니다." ,Toast.LENGTH_SHORT).show();
                                }
                            });

                            UserItem userItem = new UserItem(name, email, user.getUid());
                            db.collection("users").document(user.getUid()).set(userItem)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            customLoadingDialog.dismiss();
                                            Log.d(TAG, "DocumentSnapshot successfully written!");
                                            Log.d(TAG, "createUserWithEmail:success");
                                            startSignUpCompleteActivity();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            customLoadingDialog.dismiss();
                                            user.delete()
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                Log.d(TAG, "User account deleted.");
                                                                Toast.makeText(SignUpActivity.this, "정보 등록 오류",
                                                                        Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });
                                        }
                                    });
                        } else {
                            customLoadingDialog.dismiss();
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());

                            try {
                                throw task.getException();
                            } catch(FirebaseAuthWeakPasswordException e) {
                                Toast.makeText(SignUpActivity.this, "비밀번호는 6자리 이상이어야합니다.",
                                        Toast.LENGTH_SHORT).show();
                            } catch(FirebaseAuthInvalidCredentialsException e) {
                                Toast.makeText(SignUpActivity.this, "유효하지 않은 이메일 형식입니다.",
                                        Toast.LENGTH_SHORT).show();
                            } catch(FirebaseAuthUserCollisionException e) {
                                Toast.makeText(SignUpActivity.this, "이미 사용 중인 이메일 입니다.",
                                        Toast.LENGTH_SHORT).show();
                            } catch(Exception e) {
                                Log.e(TAG, e.getMessage());
                            }
                        }
                    }
                });
    }

    private void startSignUpCompleteActivity(){
        Intent intent = new Intent(SignUpActivity.this, SignUpCompleteActivity.class);
        startActivity(intent);
        finish();
    }

    private boolean check_form(){
        int userName_len = EditText_UserName.getText().toString().length();
        int email_len = EditText_Email.getText().toString().length();
        int password_len = EditText_Password.getText().toString().length();
        int passwordCheck_len = EditText_PasswordCheck.getText().toString().length();

        if(userName_len >= USER_NAME_MIN_LEN && email_len > 0 && password_len >= PW_MIN_LEN && passwordCheck_len >= PW_MIN_LEN)
            return true;
        else
            return false;
    }
}
package com.example.heath_together;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.heath_together.Dialog.LoadingDialog;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileSettingActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "ProfileSettingActivity";

    private FirebaseAuth mAuth;

    private EditText EditText_UserName;
    private Button Button_TakePicture;
    private Button Button_GetPicture;
    private Button Button_Complete;

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
        setContentView(R.layout.activity_profile_setting);

        mAuth = FirebaseAuth.getInstance();

        EditText_UserName = findViewById(R.id.Profile_setting_Name);
        //Button_TakePicture = findViewById(R.id.Profile_Setting_TakePicture);
        //Button_GetPicture = findViewById(R.id.Profile_Setting_GetPicture);
        Button_Complete = findViewById(R.id.Profile_Setting_Complete);

//        Button_TakePicture.setOnClickListener(this);
//        Button_GetPicture.setOnClickListener(this);
        Button_Complete.setOnClickListener(this);

        //로딩창 객체 생성
        customLoadingDialog = new LoadingDialog(this);
        //로딩창을 투명하게
        customLoadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        customLoadingDialog.setCancelable(false);

        CircleImageView circleImageView_ProfilePhoto = (CircleImageView)findViewById(R.id.Profile_Setting_Image);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        storageRef.child("userProfilePhoto/"+user.getUid()+".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.e("확","인");

                Glide.with(getApplicationContext())
                        .load(uri)
                        .into(circleImageView_ProfilePhoto);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("노","확인");
            }
        });

    }

    @Override
    public void onClick(View view) {
        int _id = view.getId();

//        if (_id == R.id.Profile_Setting_TakePicture) {
//            //Intent intent = new Intent(ProfileSettingActivity.this, CameraActivity.class);
//            //startActivity(intent);
//        }
//        else if(_id == R.id.Profile_Setting_GetPicture){
//
//        }
//        else if(_id == R.id.Profile_Setting_Complete){
//
//        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
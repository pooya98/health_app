package com.example.heath_together;

import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.heath_together.FirebaseInit.firebaseinit;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private Main1 main1;
    private Main2 main2;
    private Main3 main3;
    private Main4 main4;
    private GroupFragment groupFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(firebaseinit.firebaseUser != null) {
            String name = firebaseinit.firebaseUser.getDisplayName();
            String email = firebaseinit.firebaseUser.getEmail();
            Uri photoUrl = firebaseinit.firebaseUser.getPhotoUrl();

            // Check if user's email is verified
            // boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = firebaseinit.firebaseUser.getUid();

            System.out.println("===== TEST =====");
            System.out.println(name);
            System.out.println(email);
            System.out.println(photoUrl);
            //System.out.println(emailVerified);
            System.out.println(uid);
            System.out.println("===== TEST =====");

        }

        bottomNavigationView = findViewById(R.id.bottom_navi);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {



            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_home:
                        setFrag(0);
                        break;
                    case R.id.action_group:
                        setFrag(1);
                        break;
                    case R.id.action_search:
                        setFrag(2);
                        break;
                    case R.id.action_profile:

                        setFrag(3);
                        break;
                }
                return true;
            }
        });
        main1 = new Main1();
        main2 = new Main2();
        main3 = new Main3();
        main4 = new Main4();
        main3.fillGroupList();
        main3.fillAccountList();
        groupFragment = new GroupFragment();


        setFrag(0);
    }

    private void setFrag(int n) {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();

        switch (n){
            case 0:
                ft.replace(R.id.main_frame, main1);
                ft.commit();
                break;
            case 1:
                ft.replace(R.id.main_frame, main2);
                ft.commit();
                break;
            case 2:

                ft.replace(R.id.main_frame, main3);

                ft.commit();
                break;
            case 3:

                ft.replace(R.id.main_frame, main4);
                ft.commit();
                break;
        }
    }




    public void onChangeFragment(int index){
        if(index == 0){
            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, groupFragment).commit();
        }else if(index ==1){
        }
    }


}
package com.example.heath_together;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user != null) {
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();

            System.out.println("===== TEST =====");
            System.out.println(name);
            System.out.println(email);
            System.out.println(photoUrl);
            System.out.println(emailVerified);
            System.out.println(uid);
            System.out.println("===== TEST =====");

        }

        bottomNavigationView = findViewById(R.id.bottom_navi);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {



            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_home:
                        getSupportActionBar().setTitle("홈");
                        setFrag(0);
                        break;
                    case R.id.action_group:
                        getSupportActionBar().setTitle("그룹");
                        setFrag(1);
                        break;
                    case R.id.action_search:
                        getSupportActionBar().setTitle("검색");
                        setFrag(2);
                        break;
                    case R.id.action_profile:

                        getSupportActionBar().setTitle("마이페이지");
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
        getSupportActionBar().setTitle("홈");
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
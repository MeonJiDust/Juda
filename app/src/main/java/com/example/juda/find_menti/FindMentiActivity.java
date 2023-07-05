package com.example.juda.find_menti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.juda.R;
import com.example.juda.fragment.AllPostFragment;
import com.example.juda.fragment.HomeFragment;
import com.example.juda.fragment.MyPageFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FindMentiActivity extends AppCompatActivity {

    BottomNavigationView navigationView;

    Fragment home_fragment, my_page_fragment, all_post_fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_menti2);

        home_fragment = new HomeFragment();
        my_page_fragment = new MyPageFragment();
        all_post_fragment = new AllPostFragment();

        navigationView = findViewById(R.id.nav_view);

        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, home_fragment).commitAllowingStateLoss();

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if(item.getItemId() == R.id.home){
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, home_fragment).commitAllowingStateLoss();
                    return true;
                }else if(item.getItemId() == R.id.all_post){
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, all_post_fragment).commitAllowingStateLoss();
                    return true;
                }else if(item.getItemId() == R.id.my_page){
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_layout, my_page_fragment).commitAllowingStateLoss();
                    return true;
                }
                return true;
            }
        });
    }
}
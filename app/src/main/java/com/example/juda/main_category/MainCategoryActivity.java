package com.example.juda.main_category;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.juda.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainCategoryActivity extends AppCompatActivity {
    //ArrayList<MainCategoryInfo> main_cate_info;

    Button employ, exer, real_life;

    private SharedPreferences sharedPreferences;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_category);

        /*main_cate_info = new ArrayList<MainCategoryInfo>();

        main_cate_info.add(new MainCategoryInfo("취업 / 진로"));
        main_cate_info.add(new MainCategoryInfo("운동"));
        main_cate_info.add(new MainCategoryInfo("실생활"));

        ListView listView = (ListView)findViewById(R.id.main_cate_list);
        final MyAdapter myAdapter = new MyAdapter(this,main_cate_info);

        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id){
                Toast.makeText(getApplicationContext(),
                        myAdapter.getItem(position).getMain_category(),
                        Toast.LENGTH_LONG).show();
            }
        });*/
        employ = findViewById(R.id.btn_employment); //tag 1
        exer = findViewById(R.id.btn_exercise); //tag 2
        real_life = findViewById(R.id.btn_real_life); //tag 3

        employ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //db저장 ------

                Intent intent = new Intent(MainCategoryActivity.this, SubCategoryActivity.class);
                sharedPreferences = getSharedPreferences("test", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("tag", 1);
                editor.commit();
                startActivity(intent);
            }
        });
        exer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //db저장 ------

                Intent intent = new Intent(MainCategoryActivity.this, SubCategoryActivity.class);
                sharedPreferences = getSharedPreferences("test", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("tag", 2);
                editor.commit();
                startActivity(intent);
            }
        });
        real_life.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //db저장 ------

                Intent intent = new Intent(MainCategoryActivity.this, SubCategoryActivity.class);
                sharedPreferences = getSharedPreferences("test", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("tag", 3);
                editor.commit();
                startActivity(intent);
            }
        });
    }

}
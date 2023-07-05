package com.example.juda.main_category;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewDebug;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.juda.R;
import com.example.juda.adapter.HomeFragAdapter;
import com.example.juda.adapter.SubCategoryAdapter;
import com.example.juda.find_menti.FindMentiActivity;
import com.example.juda.info.PostingInfo;
import com.example.juda.info.SubCategoryInfo;

import java.util.ArrayList;

public class SubCategoryActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    int main_category_tag;

    private RecyclerView recyclerView;
    private ArrayList<SubCategoryInfo> sub_info;
    private SubCategoryAdapter subCategoryAdapter;

    Button complete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);

        Intent intent = getIntent();
        sharedPreferences = getSharedPreferences("test", MODE_PRIVATE);

        main_category_tag = sharedPreferences.getInt("tag", 0);

        Log.d("tag", Integer.toString(main_category_tag));
        recyclerView = findViewById(R.id.recyclerView);
        sub_info = new ArrayList<>();

        if(main_category_tag == 1){
            for(int i=0;i<5;i++){
                addItem("sub_category01-" + i);
            }
        }else if(main_category_tag == 2){
            for(int i=0;i<5;i++){
                addItem("sub_category02-" + i);
            }
        }else{
            for(int i=0;i<5;i++){
                addItem("sub_category03-" + i);
            }
        }


        subCategoryAdapter = new SubCategoryAdapter(sub_info);
        recyclerView.setAdapter(subCategoryAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        complete = findViewById(R.id.btn_complete);
        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubCategoryActivity.this, FindMentiActivity.class);
                startActivity(intent);
            }
        });
    }
    public void addItem(String sub){
        SubCategoryInfo item = new SubCategoryInfo();

        Log.d("currSub", sub);
        item.setSub(sub);

        sub_info.add(item);
    }
}
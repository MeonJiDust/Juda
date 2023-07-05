package com.example.juda.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.juda.R;
import com.example.juda.adapter.HomeFragAdapter;
import com.example.juda.info.PostingInfo;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private View view;

    private RecyclerView recyclerView;
    private ArrayList<PostingInfo> postingInfos;
    private HomeFragAdapter homeFragAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.home_fragment_layout, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        postingInfos = new ArrayList<>();

        for(int i=0;i<5;i++){
            addItem("titletitle" + i, "subtitlesubtitlesubtitle", "dedededeedededededetailatilatilatialtilatiltialconetetnacontetnsdafjeiot");
        }

        homeFragAdapter = new HomeFragAdapter(postingInfos);
        recyclerView.setAdapter(homeFragAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        return view;
    }

    public void addItem(String title, String sub_title, String detail_contents){
        PostingInfo item = new PostingInfo();

        item.setTitle(title);
        item.setSub_title(sub_title);
        item.setDetail_contents(detail_contents);

        postingInfos.add(item);
    }
}

package com.example.juda.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.juda.R;
import com.example.juda.info.PostingInfo;

import java.util.ArrayList;

public class HomeFragAdapter extends RecyclerView.Adapter<HomeFragAdapter.ViewHolder>{

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title, sub_title, detail_contents;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.home_frg_title);
            sub_title = (TextView) itemView.findViewById(R.id.home_frg_sub_title);
            detail_contents = (TextView) itemView.findViewById(R.id.home_frg_detail);
        }
    }
    private ArrayList<PostingInfo> postingInfos = null;
    public HomeFragAdapter(ArrayList<PostingInfo> postingInfos){
        this.postingInfos = postingInfos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_view_home_frg, parent, false);
        HomeFragAdapter.ViewHolder viewHolder = new HomeFragAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HomeFragAdapter.ViewHolder holder, int position) {

        PostingInfo item = postingInfos.get(position);

        holder.title.setText(item.getTitle());
        holder.sub_title.setText(item.getSub_title());
        holder.detail_contents.setText(item.getDetail_contents());
    }

    @Override
    public int getItemCount() {
        return postingInfos.size();
    }
}

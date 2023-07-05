package com.example.juda.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.juda.R;
import com.example.juda.info.PostingInfo;
import com.example.juda.info.SubCategoryInfo;
import com.example.juda.main_category.MainCategoryInfo;

import java.util.ArrayList;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.ViewHolder> {

    String tag02;
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView sub_tag;

        boolean isClicked = false;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sub_tag = (TextView) itemView.findViewById(R.id.sub_tag);

            sub_tag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isClicked = !isClicked;
                    if(isClicked){
                        sub_tag.setBackgroundResource(R.drawable.round_background);
                    }else{
                        sub_tag.setBackgroundResource(R.drawable.round_transparency);
                    }
                    int pos = getAdapterPosition();
                    tag02 = tag02 + Integer.toString(pos) + " ";


                }
            });
        }
    }
    private ArrayList<SubCategoryInfo> sub_info = null;
    public SubCategoryAdapter(ArrayList<SubCategoryInfo> sub_info){
        this.sub_info = sub_info;
    }

    @NonNull
    @Override
    public SubCategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_view_sub, parent, false);
        SubCategoryAdapter.ViewHolder viewHolder = new SubCategoryAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SubCategoryAdapter.ViewHolder holder, int position) {

        SubCategoryInfo item = sub_info.get(position);

        holder.sub_tag.setText(item.getSub());
    }

    @Override
    public int getItemCount() {
        return sub_info.size();
    }
}

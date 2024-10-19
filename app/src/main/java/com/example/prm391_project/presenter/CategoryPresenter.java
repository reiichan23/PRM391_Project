package com.example.prm391_project.presenter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.prm391_project.R;
import com.example.prm391_project.view.ListFoodsActivity;
import com.example.prm391_project.model.Category;

import java.util.ArrayList;

public class CategoryPresenter  extends RecyclerView.Adapter<CategoryPresenter.Viewholder> {
    ArrayList<Category> items;
    Context context;

    public CategoryPresenter(ArrayList<Category> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public CategoryPresenter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context= parent.getContext();
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_category, parent, false);
        return new Viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryPresenter.Viewholder holder, int position) {

        holder.titleTxt.setText(items.get(position).getName());

        switch (position){
            case 0:{
                holder.pic.setBackgroundResource(R.drawable.cat_0_background);
                break;
            }
            case 1:{
                holder.pic.setBackgroundResource(R.drawable.cat_1_background);
                break;
            }
            case 2:{
                holder.pic.setBackgroundResource(R.drawable.cat_2_background);
                break;
            }
            case 3:{
                holder.pic.setBackgroundResource(R.drawable.cat_3_background);
                break;
            }
            case 4:{
                holder.pic.setBackgroundResource(R.drawable.cat_4_background);
                break;
            }
            case 5:{
                holder.pic.setBackgroundResource(R.drawable.cat_5_background);
                break;
            }
            case 6:{
                holder.pic.setBackgroundResource(R.drawable.cat_6_background);
                break;
            }
            case 7:{
                holder.pic.setBackgroundResource(R.drawable.cat_7_background);
                break;
            }
        }

        int drawableResourceId = context.getResources().getIdentifier(items.get(position).getImagePath(),
                "drawable", holder.itemView.getContext().getPackageName());

        Glide.with(context)
                .load(drawableResourceId)
                .into(holder.pic);

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, ListFoodsActivity.class);
            intent.putExtra("CategoryId", items.get(position).getId());
            intent.putExtra("CategoryName", items.get(position).getName());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder {
        TextView titleTxt;
        ImageView pic;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            titleTxt = itemView.findViewById(R.id.catNameTxt);
            pic = itemView.findViewById(R.id.imgCat);
        }
    }
}

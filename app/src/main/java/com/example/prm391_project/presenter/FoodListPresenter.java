package com.example.prm391_project.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.prm391_project.view.DetailActivity;
import com.example.prm391_project.model.Foods;
import com.example.prm391_project.databinding.ViewholderListFoodBinding;

import java.util.ArrayList;

public class FoodListPresenter extends RecyclerView.Adapter<FoodListPresenter.ViewHolder> {
    ArrayList<Foods> items;
    Context context;
    ViewholderListFoodBinding binding;

    public FoodListPresenter(ArrayList<Foods> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        binding = ViewholderListFoodBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Foods food = items.get(position);
        holder.binding.titleTxt.setText(food.getTitle());
        holder.binding.timeTxt.setText(food.getTimeValue() + " ph");
        holder.binding.priceTxt.setText(food.getPrice()+" ₫");
        holder.binding.rateTxt.setText("" + food.getStar());

        Glide.with(context)
                .load(food.getImagePath())
                .transform(new CenterCrop(), new RoundedCorners(30))
                .into(holder.binding.pic);

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("object", food);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ViewholderListFoodBinding binding;

        public ViewHolder(@NonNull ViewholderListFoodBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
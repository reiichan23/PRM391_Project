package com.example.prm391_project.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.prm391_project.view.DetailActivity;
import com.example.prm391_project.model.Foods;
import com.example.prm391_project.databinding.ViewholderBestDealBinding;

import java.util.ArrayList;

public class BestFoodsPresenter extends RecyclerView.Adapter<BestFoodsPresenter.ViewHolder> {
    ArrayList<Foods> items;
    Context context;
    ViewholderBestDealBinding binding;

    public BestFoodsPresenter(ArrayList<Foods> items) {
        this.items = items;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ViewholderBestDealBinding binding;

        public ViewHolder(@NonNull ViewholderBestDealBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context= parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        binding = ViewholderBestDealBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.titleTxt.setText(items.get(position).getTitle());
        holder.binding.priceTxt.setText(items.get(position).getPrice() +" ₫");
        holder.binding.timeTxt.setText(items.get(position).getTimeValue()+" ph");
        holder.binding.starTxt.setText(""+items.get(position).getStar());

        Glide.with(context)
                .load(items.get(position).getImagePath())
                .transform(new CenterCrop(), new RoundedCorners(30))
                .into(holder.binding.pic);

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra("object", items.get(position));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}

package com.example.prm391_project.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.prm391_project.databinding.ViewholderCheckoutBinding;
import com.example.prm391_project.helper.ChangeNumberItemsListener;
import com.example.prm391_project.helper.ManagementCart;
import com.example.prm391_project.model.Foods;

import java.util.ArrayList;

public class CheckoutPresenter extends RecyclerView.Adapter<CheckoutPresenter.ViewHolder> {
    ArrayList<Foods> list;

    public CheckoutPresenter(ArrayList<Foods> list) {
        this.list = list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ViewholderCheckoutBinding binding;

        public ViewHolder(@NonNull ViewholderCheckoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewholderCheckoutBinding binding = ViewholderCheckoutBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Foods foodItem = list.get(position);
        holder.binding.titleTxt.setText(foodItem.getTitle());
        holder.binding.feeEachItem.setText( (foodItem.getNumberInCart() * foodItem.getPrice()) +" ₫");
        holder.binding.totalEachItem.setText(foodItem.getNumberInCart() + " × " + foodItem.getPrice() +" ₫");
        holder.binding.numberItemTxt.setText(String.valueOf(foodItem.getNumberInCart()));


        Glide.with(holder.itemView.getContext())
                .load(foodItem.getImagePath())
                .transform(new CenterCrop(), new RoundedCorners(30))
                .into(holder.binding.pic);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}

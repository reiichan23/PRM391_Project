package com.example.prm391_project.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.prm391_project.R;
import com.example.prm391_project.databinding.ViewholderCartBinding;
import com.example.prm391_project.helper.ChangeNumberItemsListener;
import com.example.prm391_project.model.Foods;

import java.util.ArrayList;

import com.example.prm391_project.helper.ManagementCart;

public class CartPresenter extends RecyclerView.Adapter<CartPresenter.ViewHolder> {
    ArrayList<Foods> list;
    private final ManagementCart managementCart;
    ChangeNumberItemsListener changeNumberItemsListener;

    public CartPresenter(ArrayList<Foods> list, Context context, ChangeNumberItemsListener changeNumberItemsListener) {
        this.list = list;
        managementCart = new ManagementCart(context);
        this.changeNumberItemsListener = changeNumberItemsListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ViewholderCartBinding binding;

        public ViewHolder(@NonNull ViewholderCartBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ViewholderCartBinding binding = ViewholderCartBinding.inflate(inflater, parent, false);
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

        holder.binding.plusCartBtn.setOnClickListener(view -> {
            managementCart.plusNumberItem(list, position, () -> {
                notifyDataSetChanged();
                changeNumberItemsListener.change();
            });
        });

        holder.binding.minusCartBtn.setOnClickListener(view -> {
            managementCart.minusNumberItem(list, position, () -> {
                notifyDataSetChanged();
                changeNumberItemsListener.change();
            });
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}

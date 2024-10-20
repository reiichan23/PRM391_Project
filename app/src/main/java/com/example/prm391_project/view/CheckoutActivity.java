package com.example.prm391_project.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm391_project.databinding.ActivityCheckoutBinding;
import com.example.prm391_project.helper.ManagementCart;
import com.example.prm391_project.presenter.CheckoutPresenter;

public class CheckoutActivity extends BaseActivity {
    private ActivityCheckoutBinding binding;
    private ManagementCart managementCart;
    private RecyclerView.Adapter adapter;
    private double tax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        managementCart = new ManagementCart(this);

        setVariable();
        calculateCart();
        initList();
    }

    private void initList() {
        if (managementCart.getListCart().isEmpty()){
            binding.emptyTxt.setVisibility(View.VISIBLE);
            binding.scrollviewCart.setVisibility(View.GONE);
        }else {
            binding.emptyTxt.setVisibility(View.GONE);
            binding.scrollviewCart.setVisibility(View.VISIBLE);
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.cardView.setLayoutManager(linearLayoutManager);
        adapter = new CheckoutPresenter(managementCart.getListCart());
        binding.cardView.setAdapter(adapter);
    }

    @SuppressLint("SetTextI18n")
    private void calculateCart() {
        double percentTax=0.02;
        double delivery=10;

        tax = (double) Math.round(managementCart.getTotalFee() * percentTax * 100.0) /100;

        double total = (double) Math.round((managementCart.getTotalFee() + tax + delivery) * 100) /100;
        double itemTotal = (double) Math.round(managementCart.getTotalFee() * 100) /100;

        binding.totalFeeTxt.setText("VND"+itemTotal);
        binding.taxTxt.setText("VND" + tax);
        binding.deliveryTxt.setText("VND" + delivery);
        binding.totalTxt.setText("VND" + total);
    }

    private void setVariable() {
        binding.backBtn.setOnClickListener(view -> finish());
    }
}

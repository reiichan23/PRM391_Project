package com.example.prm391_project.view;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

//import com.example.prm391_project.Adapter.CartAdapter;


import com.example.prm391_project.databinding.ActivityCartBinding;
import com.example.prm391_project.helper.ManagementCart;
import com.example.prm391_project.presenter.CartPresenter;

public class CartActivity extends BaseActivity {
    private ActivityCartBinding binding;
    private RecyclerView.Adapter adapter;
    private ManagementCart managementCart;
    private int tax;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
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
        adapter = new CartPresenter(managementCart.getListCart(), this, this::calculateCart);
        binding.cardView.setAdapter(adapter);
    }

    @SuppressLint("SetTextI18n")
    private void calculateCart() {
        double percentTax=0.02;
        int delivery=10000;

        tax = (int) Math.round(managementCart.getTotalFee() * percentTax * 100.0) /100;

        int total = (int) Math.round((managementCart.getTotalFee() + tax + delivery) * 100) /100;
        int itemTotal = (int) Math.round(managementCart.getTotalFee() * 100) /100;

        binding.totalFeeTxt.setText(itemTotal + " ₫");
        binding.taxTxt.setText( tax + " ₫" );
        binding.deliveryTxt.setText(delivery + " ₫" );
        binding.totalTxt.setText(total + " ₫" );
    }

    private void setVariable() {
        binding.backBtn.setOnClickListener(view -> finish());
        binding.button2.setOnClickListener(view -> startActivity(new Intent(CartActivity.this, CheckoutActivity.class)));
    }
//private void setVariable() {
//    // Set the back button functionality
//    binding.backBtn.setOnClickListener(view -> finish());
//
//    // Set the button functionality to navigate to the checkout page
//    binding.button2.setOnClickListener(view -> {
//        Intent intent = new Intent(CartActivity.this, CheckoutActivity.class);
//        startActivity(intent);
//    });
//}

}
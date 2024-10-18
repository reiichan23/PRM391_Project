package com.example.prm391_project.Activity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

//import com.example.prm391_project.Adapter.CartAdapter;

import com.example.prm391_project.Helper.ManagementCart;
import com.example.prm391_project.databinding.ActivityCartBinding;

public class CartActivity extends BaseActivity {
    private ActivityCartBinding binding;
    private RecyclerView.Adapter adapter;
    private ManagementCart managementCart;
    private double tax;
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

//        adapter = new CartAdapter(managementCart.getListCart(), this, () -> calculateCart());

        binding.cardView.setAdapter(adapter);
    }

    private void calculateCart() {
        double percentTax=0.02;
        double delivery=10;

        tax = Math.round(managementCart.getTotalFee()*percentTax*100.0)/100;

        double total = Math.round((managementCart.getTotalFee() + tax + delivery)*100)/100;
        double itemTotal = Math.round(managementCart.getTotalFee()*100)/100;

        binding.totalFeeTxt.setText("$"+itemTotal);
        binding.taxTxt.setText("$" + tax);
        binding.deliveryTxt.setText("$" + delivery);
        binding.totalTxt.setText("$" + total);
    }

    private void setVariable() {
        binding.backBtn.setOnClickListener(view -> finish());
    }

}
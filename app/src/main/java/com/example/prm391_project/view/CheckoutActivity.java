package com.example.prm391_project.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm391_project.Api.CreateOrder;
import com.example.prm391_project.databinding.ActivityCheckoutBinding;
import com.example.prm391_project.helper.ManagementCart;
import com.example.prm391_project.presenter.CheckoutPresenter;

import org.json.JSONObject;

import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;

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

        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // ZaloPay SDK Init
        ZaloPaySDK.init(2553, Environment.SANDBOX);

        binding.totalFeeTxt.setText(itemTotal + " ₫");
        binding.taxTxt.setText( tax + " ₫" );
        binding.deliveryTxt.setText(delivery + " ₫" );
        binding.totalTxt.setText(total + " ₫" );
        String totalString = String.format("%.0f",total);

        binding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateOrder orderApi = new CreateOrder();
                try {
                    JSONObject data = orderApi.createOrder(totalString);
                    Log.d("Amount", totalString);

                    String code = data.getString("return_code");
                    Toast.makeText(getApplicationContext(), "return_code: " + code, Toast.LENGTH_LONG).show();

                    if (code.equals("1")) {


                        String token = data.getString("zp_trans_token");
                        ZaloPaySDK.getInstance().payOrder(CheckoutActivity.this, token, "demozpdk://app", new PayOrderListener() {
                            @Override
                            public void onPaymentSucceeded(String s, String s1, String s2) {
                                Intent intent = new Intent(CheckoutActivity.this, PaymentStatusActivity.class);
                                intent.putExtra("status", "Thanh toán thành công");

                                startActivity(intent);
                            }

                            @Override
                            public void onPaymentCanceled(String s, String s1) {
                                Intent intent = new Intent(CheckoutActivity.this, PaymentStatusActivity.class);
                                intent.putExtra("status", "Thanh toán bị hủy");
                                startActivity(intent);
                            }

                            @Override
                            public void onPaymentError(ZaloPayError zaloPayError, String s, String s1) {
                                Intent intent = new Intent(CheckoutActivity.this, PaymentStatusActivity.class);
                                intent.putExtra("status", "Thanh toán thất bại");
                                startActivity(intent);
                            }
                        });

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

        });

    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ZaloPaySDK.getInstance().onResult(intent);
    }


    private void setVariable() {
        binding.backBtn.setOnClickListener(view -> finish());

    }
}
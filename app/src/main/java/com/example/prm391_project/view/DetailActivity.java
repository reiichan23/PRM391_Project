package com.example.prm391_project.view;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.prm391_project.model.Foods;
import com.example.prm391_project.helper.ManagementCart;
import com.example.prm391_project.R;
import com.example.prm391_project.databinding.ActivityDetailBinding;

public class DetailActivity extends BaseActivity {
    ActivityDetailBinding binding;
    private Foods object;
    private int num=1;
    private ManagementCart managementCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setStatusBarColor(getResources().getColor(R.color.black));

        getIntentExtra();
        setVariable();
    }

    private void setVariable() {
        managementCart = new ManagementCart(this);
        binding.backBtn.setOnClickListener(view -> finish());

        Glide.with(DetailActivity.this)
                .load(object.getImagePath())
                .into(binding.pic);
        binding.priceTxt.setText( object.getPrice() + " ₫" );
        binding.titleTxt.setText(object.getTitle());
        binding.descriptionTxt.setText(object.getDescription());
        binding.rateTxt.setText(object.getStar()+" Rating");
        binding.ratingBar.setRating((float) object.getStar());
        binding.totalTxt.setText((num*object.getPrice() + " ₫" ));

        binding.plusBtn.setOnClickListener(view -> {
            num = num+1;
            binding.numTxt.setText(num+" ");
            binding.totalTxt.setText((num * object.getPrice()) + " ₫");
        });
        binding.minusBtn.setOnClickListener(view -> {
            if (num >1){
                num = num -1;
                binding.numTxt.setText(num + "");
                binding.totalTxt.setText((num * object.getPrice()) + " ₫");
            }
        });
        binding.addbtn.setOnClickListener(view -> {
            object.setNumberInCart(num);
            managementCart.insertFood(object);

        });
    }

    private void getIntentExtra() {
        object= (Foods) getIntent().getSerializableExtra("object");
    }
}
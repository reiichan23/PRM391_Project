package com.example.prm391_project.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.example.prm391_project.databinding.ActivityIntroBinding;


public class IntroActivity extends BaseActivity {
ActivityIntroBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIntroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setVariable();
        getWindow().setStatusBarColor(Color.parseColor("#FFE4B5"));
    }

    private void setVariable() {
        binding.loginBtn.setOnClickListener(view -> {
            if (mAuth.getCurrentUser()!=null){
                startActivity(new Intent(IntroActivity.this, MainActivity.class));
            }else{
                startActivity(new Intent(IntroActivity.this, LoginActivity.class));
            }
        });

        binding.signupBtn.setOnClickListener(view -> startActivity(new Intent(IntroActivity.this, SignupActivity.class)));
    }


}
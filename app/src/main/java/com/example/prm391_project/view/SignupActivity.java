package com.example.prm391_project.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import com.example.prm391_project.databinding.ActivitySignupBinding;

public class SignupActivity extends BaseActivity {
    ActivitySignupBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setVariable();
    }

    private void setVariable() {
        binding.signupBtn.setOnClickListener(v -> {
            String email = binding.userEdt.getText().toString().trim();
            String password = binding.passEdt.getText().toString().trim();
            String repassword = binding.rePassEdt.getText().toString().trim();

            if (validateInput(email, password, repassword)) {
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignupActivity.this, task -> {
                    if (task.isSuccessful()) {
                        Log.i(TAG, "onComplete: ");
                        startActivity(new Intent(SignupActivity.this, MainActivity.class));
                    } else {
                        Log.i(TAG, "failure: " + task.getException());
                        Toast.makeText(SignupActivity.this, "Tạo tài khoản thất bại, kiểm tra lại email hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        binding.textView5.setOnClickListener(v -> startActivity(new Intent(SignupActivity.this, LoginActivity.class)));
    }

    private boolean validateInput(String email, String password, String repassword) {
        if (email.isEmpty()) {
            Toast.makeText(this, "Email không được để trống", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Email không hợp lệ", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.isEmpty()) {
            Toast.makeText(this, "Mật khẩu không được để trống", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.length() < 6) {
            Toast.makeText(this, "Mật khẩu phải có ít nhất 6 ký tự", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!password.equals(repassword)) {
            Toast.makeText(this, "Mật khẩu không không khớp", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
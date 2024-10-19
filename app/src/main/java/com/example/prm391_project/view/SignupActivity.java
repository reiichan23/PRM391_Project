package com.example.prm391_project.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
            String email = binding.userEdt.getText().toString();
            String password = binding.passEdt.getText().toString();
            String repassword =  binding.rePassEdt.getText().toString();

            if(password.length()<6){
                Toast.makeText(SignupActivity.this, "Your password must be 6 character", Toast.LENGTH_SHORT).show();
                return;
            }

            if(!repassword.equals(password)){
                Toast.makeText(SignupActivity.this, "Passwords do not match. Please try again!", Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignupActivity.this, task -> {
                if(task.isSuccessful()){
                    Log.i(TAG, "onComplete: ");
                    startActivity(new Intent(SignupActivity.this, MainActivity.class));
                }else {
                    Log.i(TAG, "failure: "+ task.getException());
                    Toast.makeText(SignupActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                }
            });
        });

        binding.textView5.setOnClickListener(v -> startActivity(new Intent(SignupActivity.this, LoginActivity.class)));
    }
}
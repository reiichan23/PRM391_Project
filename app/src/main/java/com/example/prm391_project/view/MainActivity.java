package com.example.prm391_project.view;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.prm391_project.R;
import com.example.prm391_project.databinding.ActivityMainBinding;
import com.example.prm391_project.model.Foods;
import com.example.prm391_project.model.Category;
import com.example.prm391_project.presenter.BestFoodsPresenter;
import com.example.prm391_project.presenter.CategoryPresenter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {
    private ActivityMainBinding binding;
    private FirebaseAuth mAuth;
    private TextView welcomeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        welcomeTextView = findViewById(R.id.textView9);

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String welcomeMessage = "Hello, " + currentUser.getEmail() + "!";
            welcomeTextView.setText(welcomeMessage);
        }

        initBestFood();
        initCategory();
        setVariable();
    }

    private void setVariable() {
        binding.logoutBtn.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        });

        binding.searchBtn.setOnClickListener(view -> {
            String text = binding.searchEdt.getText().toString();
            if (!text.trim().isEmpty()) {
                Intent intent = new Intent(MainActivity.this, ListFoodsActivity.class);
                intent.putExtra("text", text);
                intent.putExtra("isSearch", true);
                startActivity(intent);
            }
        });

        binding.cardBtn.setOnClickListener(view -> startActivity(new Intent(MainActivity.this, CartActivity.class)));

        binding.textView14.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ListFoodsActivity.class);
            intent.putExtra("viewAll", true);
            startActivity(intent);
        });
    }

    private void initBestFood() {
        DatabaseReference myRef = database.getReference("Foods");
        binding.progressBarBestFood.setVisibility(View.VISIBLE);
        ArrayList<Foods> list = new ArrayList<>();
        Query query = myRef.orderByChild("BestFood").equalTo(true);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot issue : snapshot.getChildren()) {
                        list.add(issue.getValue(Foods.class));
                    }
                    if (!list.isEmpty()) {
                        binding.bestFoodView.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
                        RecyclerView.Adapter adapter = new BestFoodsPresenter(list);
                        binding.bestFoodView.setAdapter(adapter);
                    }
                    binding.progressBarBestFood.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initCategory() {
        DatabaseReference myRef = database.getReference("Category");
        binding.progressBarCategory.setVisibility(View.VISIBLE);
        ArrayList<Category> list = new ArrayList<>();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot issue : snapshot.getChildren()) {
                        Category category = issue.getValue(Category.class);
                        list.add(category);
                    }
                    if (!list.isEmpty()) {
                        Log.d("Category", list.toString());
                        binding.categoryView.setLayoutManager(new GridLayoutManager(MainActivity.this, 4));
                        RecyclerView.Adapter adapter = new CategoryPresenter(list);
                        binding.categoryView.setAdapter(adapter);
                    }
                    binding.progressBarCategory.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
package com.taffyosales.taffyosales.ui.auth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.taffyosales.taffyosales.databinding.ActivityRegisterBinding;
import com.taffyosales.taffyosales.model.Users;
import com.taffyosales.taffyosales.viewmodel.UserViewModel;


import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding;
    FirebaseAuth mAuth;
    UserViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_register);

        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        viewModel=new ViewModelProvider(this).get(UserViewModel.class);
        mAuth = FirebaseAuth.getInstance();

        binding.btnReg.setOnClickListener(v -> {
            final String userEmail = binding.emailId.getEditText().getText().toString();
            final String userPassword = binding.password.getEditText().getText().toString();
            final String name = binding.name.getEditText().getText().toString();

            if (name.isEmpty()) {
                binding.name.setError("Provide your Name!");
                binding.name.requestFocus();
            } else if (userEmail.isEmpty()) {
                binding.emailId.setError("Provide your Email first!");
                binding.emailId.requestFocus();
            } else if (userPassword.isEmpty()) {
                binding.password.setError("Enter Password!");
                binding.password.requestFocus();
            } else if (userEmail.isEmpty() && userPassword.isEmpty() && name.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "Fields Empty!", Toast.LENGTH_SHORT).show();
            } else if (!(userEmail.isEmpty() && userPassword.isEmpty()&& name.isEmpty())) {
                binding.progressBar.setVisibility(View.VISIBLE);
                mAuth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Toast.makeText(RegisterActivity.this, "Not sucessfull", Toast.LENGTH_SHORT).show();
                        binding.progressBar.setVisibility(View.INVISIBLE);
                    } else {
                        Users user=new Users(name,userEmail,mAuth.getUid(),userPassword);
                        viewModel.AddUserData(user);
                        binding.progressBar.setVisibility(View.INVISIBLE);
                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                        intent.putExtra("Email", userEmail);
                        startActivity(intent);
                        finish();
                    }
                });
            }

        });

        binding.back.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        binding.login.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        });


    }
}

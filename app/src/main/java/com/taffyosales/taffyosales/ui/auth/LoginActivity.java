package com.taffyosales.taffyosales.ui.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.taffyosales.taffyosales.databinding.ActivityLoginBinding;
import com.taffyosales.taffyosales.ui.home.MainActivity;


public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    FirebaseAuth mAuth;

    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        mAuth = FirebaseAuth.getInstance();

        binding.btnLogIn.setOnClickListener(v -> {
            final String userEmail = binding.emailId.getText().toString();
            final String userPassword = binding.password.getText().toString();

            if (userEmail.isEmpty()) {
                binding.emailId.setError("Provide your Email first!");
                binding.emailId.requestFocus();
            } else if (userPassword.isEmpty()) {
                binding.password.setError("Enter Password!");
                binding.password.requestFocus();
            } else if (userEmail.isEmpty() && userPassword.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Fields Empty!", Toast.LENGTH_SHORT).show();
            } else if (!(userEmail.isEmpty() && userPassword.isEmpty())) {
                binding.progressBar.setVisibility(View.VISIBLE);
                mAuth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Not sucessfull", Toast.LENGTH_SHORT).show();
                        binding.progressBar.setVisibility(View.INVISIBLE);
                    } else {
                        binding.progressBar.setVisibility(View.INVISIBLE);
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("Email", userEmail);
                        startActivity(intent);
                        finish();
                    }

                });
            }

        });

        binding.register.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

    }
}

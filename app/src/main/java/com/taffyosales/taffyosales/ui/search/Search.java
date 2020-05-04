package com.taffyosales.taffyosales.ui.search;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.taffyosales.taffyosales.R;
import com.taffyosales.taffyosales.databinding.ActivityMainBinding;
import com.taffyosales.taffyosales.databinding.ActivitySearchBinding;
import com.taffyosales.taffyosales.ui.home.MainActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class Search extends AppCompatActivity {


    ActivitySearchBinding binding;
    EditText searchEditText;
    FloatingActionButton fab;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
//        binding = ActivitySearchBinding.inflate(getLayoutInflater());
//        View view = binding.getRoot();
        CircleImageView profileDp = findViewById(R.id.user_profile);
        searchEditText=findViewById(R.id.search_bar);
        mAuth=FirebaseAuth.getInstance();
        fab=findViewById(R.id.fab);
        searchEditText.requestFocus();
        FirebaseFirestore.getInstance().collection("Users").document(mAuth.getUid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot document, @Nullable FirebaseFirestoreException e) {
                if (document.exists()) {
                    Glide.with(Search.this).asBitmap().load(document.getString("user_image")).into(profileDp);
                }
            }
        });




    }
}

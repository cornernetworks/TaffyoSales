package com.taffyosales.taffyosales.ui.home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.taffyosales.taffyosales.R;
import com.taffyosales.taffyosales.adapter.UserAdapter;
import com.taffyosales.taffyosales.databinding.ActivityMainBinding;
import com.taffyosales.taffyosales.model.Store;
import com.taffyosales.taffyosales.model.Users;
import com.taffyosales.taffyosales.ui.product.Add_Product;
import com.taffyosales.taffyosales.ui.store.Add_Store;
import com.taffyosales.taffyosales.ui.auth.LoginActivity;
import com.taffyosales.taffyosales.ui.search.Search;
import com.taffyosales.taffyosales.util.ViewAnimation;
import com.taffyosales.taffyosales.viewmodel.UserViewModel;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private static final String TAG = "MainActivity";
    FirebaseAuth mAuth;
    ActivityMainBinding binding;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference userRef = db.collection("Users");
    private ArrayList<Users> mExampleList;
    private RecyclerView mRecyclerView;
    private UserAdapter mAdapter;
    private StorageReference mStorageRef;
    UserViewModel viewModel;
    private Uri imageUri;
    private boolean isRorate = false;

    FloatingActionButton search, qr_search, create_new;
    private DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        mStorageRef = FirebaseStorage.getInstance().getReference();


        ViewAnimation.init(binding.search);
        ViewAnimation.init(binding.qrScan);
        ViewAnimation.init(binding.createNew);


        viewModel = new ViewModelProvider(this).get(UserViewModel.class);


        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() == null) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        setSupportActionBar(binding.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, binding.drawerLayout, binding.toolbar, R.string.search, R.string.search);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        actionBarDrawerToggle.setDrawerSlideAnimationEnabled(true);
        binding.navView.setNavigationItemSelectedListener(this);

        View hView = binding.navView.getHeaderView(0);
        TextView nav_user = (TextView) hView.findViewById(R.id.username);
        TextView nav_name = (TextView) hView.findViewById(R.id.name);
        CircleImageView imageView = hView.findViewById(R.id.profile_image);
        CircleImageView profileDp = findViewById(R.id.user_profile);
        binding.searchBar.setOnClickListener(v -> {

            Intent intent = new Intent(MainActivity.this, Search.class);
            Pair[] pairs = new Pair[3];

            pairs[0] = new Pair<View, String>(binding.userProfile, "profileTransationConstrain");
            pairs[1] = new Pair<View, String>(binding.searchBar, "searchTransationConstrain");
            pairs[2] = new Pair<View, String>(binding.constrainLayout, "sharedTransationConstrain");
            pairs[2] = new Pair<View, String>(binding.fab, "fabTransationConstrain");

            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, pairs);

            startActivity(intent, options.toBundle());

            Toast.makeText(this, "Search Clicked", Toast.LENGTH_SHORT).show();
            Log.e("Search", "Search Clicked");

        });

        FirebaseFirestore.getInstance().collection("stores").document(mAuth.getUid()).collection("store").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot snapshot = task.getResult();
                if (snapshot.isEmpty()) {
                    binding.fab.setVisibility(View.INVISIBLE);
                } else {
                    binding.fab.setVisibility(View.VISIBLE);
                }
            }
        });

//        binding.createNew.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {

//            }
//        });

        binding.fab.setOnClickListener(v -> {
            isRorate = ViewAnimation.rotateFab(v, !isRorate);
            if (isRorate) {
                ViewAnimation.showIn(binding.search);
                ViewAnimation.showIn(binding.createNew);
                ViewAnimation.showIn(binding.qrScan);

            } else {
                ViewAnimation.showOut(binding.search);
                ViewAnimation.showOut(binding.createNew);
                ViewAnimation.showOut(binding.qrScan);
            }
        });

        binding.search.setOnClickListener(v -> {
            Toast.makeText(this, "Search Clicked", Toast.LENGTH_SHORT).show();
        });

        binding.qrScan.setOnClickListener(v -> {
            Toast.makeText(this, "qrScan Clicked", Toast.LENGTH_SHORT).show();
        });

        binding.createNew.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Add_Product.class);
            startActivity(intent);
            Toast.makeText(this, "createNew Clicked", Toast.LENGTH_SHORT).show();
        });


        profileDp.setOnClickListener(v -> {
            showDialog();
        });

//        binding.searchBar.setOnClickListener(v -> {
//            binding.searchBar.setText("Its Clicked");
//            Toast.makeText(MainActivity.this, "Its Clicked", Toast.LENGTH_SHORT).show();
//        });


        Log.e(TAG, "onCreate: " + imageUri);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1, 1)
                        .start(MainActivity.this);
            }
        });


        FirebaseFirestore.getInstance().collection("Users").document(mAuth.getUid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot document, @Nullable FirebaseFirestoreException e) {
                if (document.exists()) {
                    String name = document.getString("name");
                    String email_id = document.getString("email_id");
                    nav_user.setText(email_id);
                    nav_name.setText(name);
                    Glide.with(MainActivity.this).asBitmap().load(document.getString("user_image")).into(imageView);
                    Glide.with(MainActivity.this).asBitmap().load(document.getString("user_image")).into(profileDp);

                }
            }
        });


        setUpRecyclerView();
        mAdapter.startListening();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                viewModel.uploadUserImg(FirebaseAuth.getInstance().getUid(), resultUri);
                imageUri = resultUri;
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    private void setUpRecyclerView() {
        Query query = userRef.orderBy("name", Query.Direction.ASCENDING);
        FirestoreRecyclerOptions<Users> options = new FirestoreRecyclerOptions.Builder<Users>()
                .setQuery(query, Users.class)
                .build();
        mAdapter = new UserAdapter(options);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAdapter.stopListening();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        String itemName = item.getTitle().toString();
        switch (itemName) {
            case "Log Out":
                Log.e(TAG, "onNavigationItemSelected: " + item);
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case "Settings":
                Log.e(TAG, "onNavigationItemSelected: " + item);
                break;
            default:

        }


        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    void showDialog() {

        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.profile_dialog, null);


        CircleImageView userProfile = (CircleImageView) view.findViewById(R.id.user_profile);
        TextView email = view.findViewById(R.id.emailId);
        TextView username = view.findViewById(R.id.username);
        TextView add_store = view.findViewById(R.id.add_store);
        ImageView add_store_img = view.findViewById(R.id.add_store_image);
        TextView store = view.findViewById(R.id.store);
        ImageView store_img = view.findViewById(R.id.store_image);


        FirebaseFirestore.getInstance().collection("stores").document(mAuth.getUid())
                .collection("store").get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
                Store store1 = snapshot.toObject(Store.class);

                add_store.setVisibility(view.INVISIBLE);
                add_store_img.setVisibility(view.INVISIBLE);
                store.setVisibility(view.VISIBLE);
                store_img.setVisibility(view.VISIBLE);

                store.setText(store1.getShopname());
                Log.e("Shopsss", " Details  " + store1);
            }
        }).addOnFailureListener(e -> {

            Log.e("Shopsss", " Details not have  ");
            add_store.setVisibility(view.VISIBLE);
            add_store_img.setVisibility(view.VISIBLE);
            store.setVisibility(view.INVISIBLE);
            store_img.setVisibility(view.INVISIBLE);
        });

        add_store_img.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Add_Store.class);
            startActivity(intent);
        });

        add_store.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Add_Store.class);
            startActivity(intent);
        });


        FirebaseFirestore.getInstance().collection("Users").document(mAuth.getUid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot document, @Nullable FirebaseFirestoreException e) {
                if (document.exists()) {
                    String name = document.getString("name");
                    String email_id = document.getString("email_id");

                    Log.e("UserData", "onEvent: " + name + " and email is  " + email_id);

                    username.setText(name);
                    email.setText(email_id);
                    Glide.with(MainActivity.this).asBitmap().load(document.getString("user_image")).into(userProfile);

                }
            }
        });


        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setView(view)
                .create();

        alertDialog.show();
    }

}

package com.example.brizz.usermading.ActivityE_Mading;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.brizz.usermading.Fragment.Adapter.TabAdapter;
import com.example.brizz.usermading.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.main_toolbar)
    Toolbar mainToolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;

    private String current_uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mainToolbar);
        getSupportActionBar().setTitle("E-Mading");

        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        viewPager = findViewById(R.id.viewPager);
        TabAdapter adapter = new TabAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcon();

    }

    private void setupTabIcon() {
        tabLayout.getTabAt(0).setIcon(R.drawable.home).setText("Home");
        tabLayout.getTabAt(1).setIcon(R.drawable.user).setText("User");
        tabLayout.getTabAt(2).setIcon(R.drawable.info).setText("About");
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {

            backToLogin();
        } else {

            current_uid = mAuth.getCurrentUser().getUid();

            firebaseFirestore.collection("Users").document(current_uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                    if (task.isSuccessful()){

                        if (!task.getResult().exists()){

                            Intent setupProfil = new Intent(MainActivity.this, ProfilSettingsActivity.class);
                            startActivity(setupProfil);
                            finish();

                        }

                    }else {

                        String errMessage = task.getException().getMessage();
                        Toast.makeText(MainActivity.this, "Error : " + errMessage, Toast.LENGTH_SHORT).show();
                    }

                }
            });

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.action_logout_btn:
                logOut();
                return true;
            case R.id.action_profilset_btn:
                Intent settingProfil = new Intent(MainActivity.this, ProfilSettingsActivity.class);
                startActivity(settingProfil);

                return true;

                default:
                    return false;
        }

    }

    private void logOut() {

        mAuth.signOut();
        backToLogin();
    }

    private void backToLogin() {
        Intent intent = new Intent(MainActivity.this, LoginUserActivity.class);
        startActivity(intent);
        finish();
    }
}

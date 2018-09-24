package com.example.brizz.usermading.ActivityE_Mading;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.brizz.usermading.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginUserActivity extends AppCompatActivity {

    @BindView(R.id.email_login)
    TextInputEditText emailLogin;
    @BindView(R.id.pass_login)
    TextInputEditText passLogin;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.btn_gosignup)
    Button btnGosignup;
    @BindView(R.id.logo_magazine)
    ImageView logoMagazine;
    @BindView(R.id.tv_emading)
    TextView tvEmading;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
    }

    @OnClick(R.id.btn_gosignup)
    public void onSignUpClicked() {
        Intent goDaftar = new Intent(LoginUserActivity.this, SignUpActivity.class);
        startActivity(goDaftar);
    }


    @OnClick(R.id.btn_login)
    public void onLoginClicked() {
        String loginEmail = emailLogin.getText().toString().trim();
        String loginPass  = passLogin.getText().toString().trim();

        if (!TextUtils.isEmpty(loginEmail) && !TextUtils.isEmpty(loginPass)){
            progressBar.setVisibility(View.VISIBLE);

            mAuth.signInWithEmailAndPassword(loginEmail, loginPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()){

                        sendToMain();

                    }else {

                        String errorMessage = task.getException().getMessage();
                        Toast.makeText(LoginUserActivity.this, "Error : " + errorMessage, Toast.LENGTH_SHORT).show();

                    }
                    progressBar.setVisibility(View.INVISIBLE);

                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            sendToMain();
        }
    }

    private void sendToMain(){
        Intent mainIntent = new Intent(LoginUserActivity.this, MainActivity.class);
        startActivity(mainIntent);
        finish();
    }
}

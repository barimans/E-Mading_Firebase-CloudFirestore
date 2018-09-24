package com.example.brizz.usermading.ActivityE_Mading;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
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

public class SignUpActivity extends AppCompatActivity {

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.email_signup)
    TextInputEditText emailSignup;
    @BindView(R.id.pass_signup)
    TextInputEditText passSignup;
    @BindView(R.id.repass_signup)
    TextInputEditText repassSignup;
    @BindView(R.id.btn_sign_up)
    Button btnSignUp;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
    }

    @OnClick(R.id.btn_sign_up)
    public void onViewClicked() {

        String regEmail = emailSignup.getText().toString().trim();
        String regPass  = passSignup.getText().toString().trim();
        String rePass   = repassSignup.getText().toString().trim();

        if (!TextUtils.isEmpty(regEmail) && !TextUtils.isEmpty(regPass) & !TextUtils.isEmpty(rePass)){

            if (rePass.equals(regPass)){

                progressBar.setVisibility(View.VISIBLE);

                mAuth.createUserWithEmailAndPassword(regEmail, regPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){

                            Intent setupProfil = new Intent(SignUpActivity.this, ProfilSettingsActivity.class);
                            startActivity(setupProfil);
                            finish();

                        }else {
                            String errMessage = task.getException().getMessage();
                            Toast.makeText(SignUpActivity.this, "Error : " + errMessage, Toast.LENGTH_SHORT).show();
                        }

                        progressBar.setVisibility(View.INVISIBLE);

                    }
                });

            }else {
                Toast.makeText(this, "Confirm Password & Password Doesn't Match", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null){
            sendToMain();
        }
    }

    private void sendToMain() {
        Intent mainIntent = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(mainIntent);
        finish();
    }
}

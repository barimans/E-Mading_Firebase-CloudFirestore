package com.example.brizz.usermading.ActivityE_Mading;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.brizz.usermading.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfilSettingsActivity extends AppCompatActivity {

    @BindView(R.id.profil_toolbar)
    Toolbar profilToolbar;
    @BindView(R.id.circle_image)
    CircleImageView circleImage;
    @BindView(R.id.user_profil)
    TextInputEditText userProfil;
    @BindView(R.id.btn_save_profil)
    Button btnSaveProfil;
    @BindView(R.id.setup_progress)
    ProgressBar setupProgress;
    @BindView(R.id.phone_signup)
    TextInputEditText phoneSignup;

    private String user_id;
    private boolean isChanged = false;

    private Uri mainImageUri = null;
    private StorageReference storageReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_settings);
        ButterKnife.bind(this);

        setSupportActionBar(profilToolbar);
        getSupportActionBar().setTitle("Profil Setup");


        storageReference = FirebaseStorage.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        user_id = firebaseAuth.getCurrentUser().getUid();
        firebaseFirestore = FirebaseFirestore.getInstance();

        setupProgress.setVisibility(View.VISIBLE);
        btnSaveProfil.setEnabled(false);

        firebaseFirestore.collection("Users").document(user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {

                    if (task.getResult().exists()) {

                        String name = task.getResult().getString("name");
                        String phone = task.getResult().getString("phone");
                        String image = task.getResult().getString("image");

                        mainImageUri = Uri.parse(image);

                        userProfil.setText(name);
                        phoneSignup.setText(phone);

                        RequestOptions placeholderRequest = new RequestOptions();
                        placeholderRequest.placeholder(R.drawable.set_profil);

                        Glide.with(ProfilSettingsActivity.this).setDefaultRequestOptions(placeholderRequest).load(image).into(circleImage);
                    }

                } else {

                    String error = task.getException().getMessage();
                    Toast.makeText(ProfilSettingsActivity.this, "Firestore Retrieve Error : " + error, Toast.LENGTH_SHORT).show();
                }
                setupProgress.setVisibility(View.INVISIBLE);
                btnSaveProfil.setEnabled(true);
            }
        });

    }

    @OnClick(R.id.btn_save_profil)
    public void onSaveProfilClicked() {

        final String user_name = userProfil.getText().toString().trim();
        final String phone_num = phoneSignup.getText().toString().trim();

        if (!TextUtils.isEmpty(user_name) && !TextUtils.isEmpty(phone_num) & mainImageUri != null) {

            setupProgress.setVisibility(View.VISIBLE);

            if (isChanged) {

                user_id = firebaseAuth.getCurrentUser().getUid();

                StorageReference image_path = storageReference.child("profil_images").child(user_id + ".jpg");
                image_path.putFile(mainImageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                        if (task.isSuccessful()) {

                            storeToFirestore(task, user_name, phone_num);

                        } else {

                            String errorMessage = task.getException().getMessage();
                            Toast.makeText(ProfilSettingsActivity.this, "Image Error : " + errorMessage, Toast.LENGTH_SHORT).show();

                            setupProgress.setVisibility(View.INVISIBLE);

                        }
                    }
                });
            } else {
                storeToFirestore(null, user_name, phone_num);
            }
        }
    }

    private void storeToFirestore(@NonNull Task<UploadTask.TaskSnapshot> task, String user_name, String phone_num) {

        Uri download_uri;

        if (task != null) {

            download_uri = task.getResult().getDownloadUrl();

        } else {

            download_uri = mainImageUri;
        }

        Map<String, String> userMap = new HashMap<>();
        userMap.put("name", user_name);
        userMap.put("phone", phone_num);
        userMap.put("image", download_uri.toString());

        firebaseFirestore.collection("Users").document(user_id).set(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {

                    Toast.makeText(ProfilSettingsActivity.this, "User Settings are Updated", Toast.LENGTH_SHORT).show();
                    Intent succes = new Intent(ProfilSettingsActivity.this, MainActivity.class);
                    succes.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(succes);
                    finish();

                } else {

                    String error = task.getException().getMessage();
                    Toast.makeText(ProfilSettingsActivity.this, "Firestore Error : " + error, Toast.LENGTH_SHORT).show();
                }
                setupProgress.setVisibility(View.INVISIBLE);

            }
        });
    }

    @OnClick(R.id.circle_image)
    public void onViewClicked() {

        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .start(ProfilSettingsActivity.this);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//
//            if (ContextCompat.checkSelfPermission(ProfilSettingsActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//
//                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
//                ActivityCompat.requestPermissions(ProfilSettingsActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
//            } else {
//
//                CropImage.activity()
//                        .setGuidelines(CropImageView.Guidelines.ON)
//                        .start(ProfilSettingsActivity.this);
//            }
//        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                mainImageUri = result.getUri();
                circleImage.setImageURI(mainImageUri);

                isChanged = true;

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

                Exception error = result.getError();

            }
        }
    }

}

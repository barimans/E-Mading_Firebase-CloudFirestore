package com.example.brizz.usermading.ActivityE_Mading;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.brizz.usermading.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.zelory.compressor.Compressor;

public class NewPostActivity extends AppCompatActivity {

    @BindView(R.id.new_post_toolbar)
    Toolbar newPostToolbar;
    @BindView(R.id.iv_new_post)
    ImageView ivNewPost;
    @BindView(R.id.et_description)
    TextInputEditText etDescription;
    @BindView(R.id.btn_new_post)
    Button btnNewPost;
    @BindView(R.id.new_post_progress)
    ProgressBar newPostProgress;
    @BindView(R.id.et_tags)
    TextInputEditText etTags;

    private Uri postImageUri = null;

    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    private String current_uid;
    private Bitmap compressedImageFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);
        ButterKnife.bind(this);

        setSupportActionBar(newPostToolbar);
        getSupportActionBar().setTitle("Add New Post Here");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        storageReference = FirebaseStorage.getInstance().getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        current_uid = firebaseAuth.getCurrentUser().getUid();
    }

    @OnClick(R.id.iv_new_post)
    public void onImageClicked() {

        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMinCropResultSize(512, 512)
                .start(NewPostActivity.this);
    }

    @OnClick(R.id.btn_new_post)
    public void onPostClicked() {

        final String tag  = etTags.getText().toString().trim();
        final String desc = etDescription.getText().toString().trim();

        if (!TextUtils.isEmpty(desc) && !TextUtils.isEmpty(tag) && postImageUri != null) {

            newPostProgress.setVisibility(View.VISIBLE);

            final String randomName = UUID.randomUUID().toString();

            StorageReference filePath = storageReference.child("post_images").child(randomName + ".jpg");
            filePath.putFile(postImageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                    final String downloadUri = task.getResult().getDownloadUrl().toString();

                    if (task.isSuccessful()) {

                        File newImageFile = new File(postImageUri.getPath());
                        try {
                            compressedImageFile = new Compressor(NewPostActivity.this)
                                    .setMaxHeight(100)
                                    .setMaxWidth(100)
                                    .setQuality(50)
                                    .compressToBitmap(newImageFile);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        compressedImageFile.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                        byte[] thumbData = baos.toByteArray();
                        UploadTask uploadTask = storageReference.child("post_images/thumb").child(randomName + ".jpg").putBytes(thumbData);
                        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                String downloadThumbUri = taskSnapshot.getDownloadUrl().toString();

                                HashMap<String, Object> postMap = new HashMap<>();
                                postMap.put("image_thumb", downloadThumbUri);
                                postMap.put("image_url", downloadUri);
                                postMap.put("tag", tag);
                                postMap.put("desc", desc);
                                postMap.put("user_id", current_uid);
                                postMap.put("timestamp", FieldValue.serverTimestamp());

                                firebaseFirestore.collection("Posts").add(postMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentReference> task) {

                                        if (task.isSuccessful()) {

                                            Toast.makeText(NewPostActivity.this, "Post was Added", Toast.LENGTH_SHORT).show();
                                            Intent toMain = new Intent(NewPostActivity.this, MainActivity.class);
                                            toMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(toMain);
                                            finish();

                                        } else {
                                            String errMessage = task.getException().getMessage();
                                            Toast.makeText(NewPostActivity.this, "Error : " + errMessage, Toast.LENGTH_SHORT).show();

                                        }

                                        newPostProgress.setVisibility(View.INVISIBLE);

                                    }
                                });

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });

                    } else {

                        newPostProgress.setVisibility(View.INVISIBLE);
                    }

                }
            });

        } else {
            Toast.makeText(this, "Field Tidak Boleh Kosong !!!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                postImageUri = result.getUri();
                ivNewPost.setImageURI(postImageUri);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

                Exception error = result.getError();

            }
        }
    }

}

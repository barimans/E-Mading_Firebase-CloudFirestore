package com.example.brizz.usermading.ActivityE_Mading;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.brizz.usermading.R;
import com.github.chrisbanes.photoview.PhotoView;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class SinglePageActivity extends AppCompatActivity {

    @BindView(R.id.single_userimage)
    CircleImageView singleUserimage;
    @BindView(R.id.single_username)
    TextView singleUsername;
    @BindView(R.id.single_image)
    PhotoView singleImage;
    @BindView(R.id.single_tag)
    TextView singleTag;
    @BindView(R.id.single_desc)
    TextView singleDesc;

    private String getUserImage, getUsername, getImage, getTag, getDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_page);
        ButterKnife.bind(this);

        getUserImage = getIntent().getStringExtra("user_image");
        Glide.with(SinglePageActivity.this).load(getUserImage).into(singleUserimage);

        getUsername = getIntent().getStringExtra("user_name");
        singleUsername.setText(getUsername);


        getImage = getIntent().getStringExtra("image");
        Glide.with(SinglePageActivity.this).load(getImage).into(singleImage);

        getTag = getIntent().getStringExtra("tag");
        singleTag.setText(getTag);

        getDesc = getIntent().getStringExtra("desc");
        singleDesc.setText(getDesc);

//        getDate = getIntent().getStringExtra("date");
//        singleDate.setText(getDate);
    }
}

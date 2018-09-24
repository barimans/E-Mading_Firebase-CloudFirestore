package com.example.brizz.usermading.Adapter;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.text.format.DateFormat;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.brizz.usermading.ActivityE_Mading.CommentsActivity;
import com.example.brizz.usermading.ActivityE_Mading.SinglePageActivity;
import com.example.brizz.usermading.Model.MadingPost;
import com.example.brizz.usermading.Model.User;
import com.example.brizz.usermading.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class MadingRecyclerAdapter extends RecyclerView.Adapter<MadingRecyclerAdapter.ViewHolder> {

    public List<MadingPost> mading_list;
    public List<User> user_list;
    public Context context;

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    public MadingRecyclerAdapter(List<MadingPost> mading_list, List<User> user_list) {
        this.mading_list = mading_list;
        this.user_list = user_list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mading_list_item, parent, false);
        context = parent.getContext();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        holder.setIsRecyclable(false);

        final String madingPostId = mading_list.get(position).MadingPostId;
        final String currentUserId = firebaseAuth.getCurrentUser().getUid();

        //Retrive Tag
        final String tag_data = mading_list.get(position).getTag();
        holder.setTagView(tag_data);

        //Retrive Description
        final String desc_data = mading_list.get(position).getDesc();
        holder.setDescText(desc_data);

        //Retrieve Mading Post Image
        final String image_url = mading_list.get(position).getImage_url();
        String image_thumb = mading_list.get(position).getImage_thumb();
        holder.setMadingImage(image_url, image_thumb);

        String mading_user_id = mading_list.get(position).getUser_id();
        if (mading_user_id.equals(currentUserId)) {
            holder.madingDeleteBtn.setEnabled(true);
            holder.madingDeleteBtn.setVisibility(View.VISIBLE);
        }

        //Retrieve UserImage & UserName
        final String userName = user_list.get(position).getName();
        final String imageUser = user_list.get(position).getImage();
        holder.setUserData(userName, imageUser);

        //Retrieve Date
        try {
            long millisecond = mading_list.get(position).getTimestamp().getTime();
            String dateString = DateFormat.format("dd/MM/yyyy", new Date(millisecond)).toString();
            Log.v("Date ", dateString);
            holder.setDate(dateString);

        }catch (Exception e){

            Toast.makeText(context, "Bad Connection", Toast.LENGTH_SHORT).show();

        }

        //Get Likes Count
        firebaseFirestore.collection("Posts/" + madingPostId + "/Likes").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                if (!documentSnapshots.isEmpty()) {

                    int count = documentSnapshots.size();
                    holder.updateLikeCount(count);

                } else {
                    holder.updateLikeCount(0);
                }

            }
        });

        //Get Like Button
        firebaseFirestore.collection("Posts/" + madingPostId + "/Likes").document(currentUserId).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @SuppressLint("NewApi")
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {

                if (documentSnapshot.exists()) {
                    holder.madingLikeBtn.setImageDrawable(context.getDrawable(R.drawable.action_like_red));

                } else {
                    holder.madingLikeBtn.setImageDrawable(context.getDrawable(R.drawable.action_like_gray));

                }

            }
        });

        //Set Like Button
        holder.madingLikeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firebaseFirestore.collection("Posts/" + madingPostId + "/Likes").document(currentUserId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if (!task.getResult().exists()) {

                            HashMap<String, Object> likesMap = new HashMap<>();
                            likesMap.put("timestamp", FieldValue.serverTimestamp());

                            firebaseFirestore.collection("Posts/" + madingPostId + "/Likes").document(currentUserId).set(likesMap);

                        } else {

                            firebaseFirestore.collection("Posts/" + madingPostId + "/Likes").document(currentUserId).delete();

                        }

                    }
                });
            }
        });

        //Go To Comments Activity
        holder.madingCommentsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent commentsIntent = new Intent(context, CommentsActivity.class);
                commentsIntent.putExtra("mading_post_id", madingPostId);
                context.startActivity(commentsIntent);
            }
        });

        //Delete Mading Post
        holder.madingDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.showProgress();

                firebaseFirestore.collection("Posts").document(madingPostId).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        mading_list.remove(position);
                        user_list.remove(position);

                        holder.closeProgress();
                    }
                });
            }
        });

        holder.goDescPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent sendData = new Intent(context, SinglePageActivity.class);
                sendData.putExtra("user_image", imageUser);
                sendData.putExtra("user_name", userName);
                sendData.putExtra("image", image_url);
                sendData.putExtra("tag", tag_data);
                sendData.putExtra("desc", desc_data);
                context.startActivity(sendData);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mading_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private View mView;
        private ProgressDialog mProgress;

        private CardView goDescPage;
        private TextView descView, tagView, dateView;
        private ImageView madingImageView;
        private TextView madingUserName;
        private CircleImageView madingUserImage;
        private ImageView madingLikeBtn;
        private TextView madingLikeCount;
        private ImageView madingCommentsBtn;
        private Button madingDeleteBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;

            madingLikeBtn = mView.findViewById(R.id.mading_like_btn);
            madingLikeCount = mView.findViewById(R.id.mading_like_count);
            madingCommentsBtn = mView.findViewById(R.id.mading_comments_btn);
            madingDeleteBtn = mView.findViewById(R.id.delete_mading);
            goDescPage = mView.findViewById(R.id.main_mading_post);
            mProgress = new ProgressDialog(context);
        }

        public void showProgress(){
            mProgress.setMessage("Deleting Post ...");
            mProgress.show();
        }

        public void closeProgress(){
            mProgress.dismiss();
        }

        public void setDescText(String descText) {
            descView = mView.findViewById(R.id.mading_desc);
            descView.setText(descText);
        }

        public void setTagView(String tagText){
            tagView = mView.findViewById(R.id.mading_tag);
            tagView.setText(tagText);
        }

        public void setMadingImage(String downloadUri, String thumbUri) {

            madingImageView = mView.findViewById(R.id.mading_image_post);

            RequestOptions placeholderOption = new RequestOptions();
            placeholderOption.placeholder(R.drawable.mading_image);

            Glide.with(context).applyDefaultRequestOptions(placeholderOption).load(downloadUri).thumbnail(
                    Glide.with(context).load(thumbUri)
            ).into(madingImageView);

        }

        public void setDate(String date) {

            dateView = mView.findViewById(R.id.mading_date);
            dateView.setText(date);
        }

        public void setUserData(String name, String image) {

            madingUserName = mView.findViewById(R.id.mading_user);
            madingUserImage = mView.findViewById(R.id.mading_user_image);

            madingUserName.setText(name);

            RequestOptions placeholderOption = new RequestOptions();
            placeholderOption.placeholder(R.drawable.user_image);
            Glide.with(context).applyDefaultRequestOptions(placeholderOption).load(image).into(madingUserImage);
        }

        public void updateLikeCount(int count) {

            madingLikeCount = mView.findViewById(R.id.mading_like_count);
            madingLikeCount.setText(count + " Likes");
        }

        private void goSinglePage(String user_image, String user_name, String date, String image, String tag, String desc){
            Intent sendData = new Intent(context, SinglePageActivity.class);
            sendData.putExtra("user_image", user_image);
            sendData.putExtra("user_name", user_name);
            sendData.putExtra("date", date);
            sendData.putExtra("image", image);
            sendData.putExtra("tag", tag);
            sendData.putExtra("desc", desc);
            context.startActivity(sendData);
        }
    }
}

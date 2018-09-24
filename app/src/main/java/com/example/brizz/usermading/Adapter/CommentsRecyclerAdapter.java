package com.example.brizz.usermading.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.brizz.usermading.Model.Comments;
import com.example.brizz.usermading.Model.User;
import com.example.brizz.usermading.R;

import java.util.List;

public class CommentsRecyclerAdapter extends RecyclerView.Adapter<CommentsRecyclerAdapter.ViewHolder> {

    private List<Comments> comments_list;
    private List<User> user_list;
    private Context context;

    public CommentsRecyclerAdapter(List<Comments> comments_list, List<User> user_list) {
        this.comments_list = comments_list;
        this.user_list = user_list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comments_list_item, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.setIsRecyclable(true);

        String comments_message = comments_list.get(position).getMessage();
        holder.setCommentsMessage(comments_message);

        String user_name = user_list.get(position).getName();
        String user_image = user_list.get(position).getImage();
        holder.setUserData(user_name, user_image);

    }

    @Override
    public int getItemCount() {

        if (comments_list != null){
            return comments_list.size();
        }else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView commentView;
        private View mView;
        private TextView userName;
        private ImageView imageUser;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setCommentsMessage(String commentsMessage){
            commentView = mView.findViewById(R.id.comment_message);
            commentView.setText(commentsMessage);
        }

        public void setUserData(String comment_name, String comment_image){
            userName = mView.findViewById(R.id.user_name_comments);
            imageUser = mView.findViewById(R.id.user_image_comments);
            userName.setText(comment_name);

            RequestOptions placeholderOption = new RequestOptions();
            placeholderOption.placeholder(R.drawable.user_image);
            Glide.with(context).applyDefaultRequestOptions(placeholderOption).load(comment_image).into(imageUser);
        }
    }
}

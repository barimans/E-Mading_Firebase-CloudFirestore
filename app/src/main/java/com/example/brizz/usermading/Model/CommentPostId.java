package com.example.brizz.usermading.Model;

import android.support.annotation.NonNull;

import com.google.firebase.firestore.Exclude;

public class CommentPostId {

    @Exclude
    public String CommentsPostId;

    public <T extends CommentPostId> T withId(@NonNull final String Id){
        this.CommentsPostId = Id;
        return (T) this;
    }
}

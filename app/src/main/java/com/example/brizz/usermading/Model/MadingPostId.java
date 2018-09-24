package com.example.brizz.usermading.Model;

import android.support.annotation.NonNull;

import com.google.firebase.firestore.Exclude;

public class MadingPostId {

    @Exclude
    public String MadingPostId;

    public <T extends MadingPostId> T withId(@NonNull final String Id){
        this.MadingPostId = Id;
        return (T) this;
    }
}

package com.example.brizz.usermading.ActivityE_Mading;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.brizz.usermading.Adapter.CommentsRecyclerAdapter;
import com.example.brizz.usermading.Model.Comments;
import com.example.brizz.usermading.Model.MadingPost;
import com.example.brizz.usermading.Model.User;
import com.example.brizz.usermading.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CommentsActivity extends AppCompatActivity {

    @BindView(R.id.comments_toolbar)
    Toolbar commentsToolbar;
    @BindView(R.id.comments_list)
    RecyclerView commentsList;
    @BindView(R.id.comments_field)
    EditText commentsField;
    @BindView(R.id.comments_sent)
    ImageView commentsSent;
    @BindView(R.id.tv_comments)
    TextView tvComments;

    private String mading_post_id;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    private String current_user_id;

    private List<Comments> comments_list;
    private List<User> user_list;
    private CommentsRecyclerAdapter commentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        ButterKnife.bind(this);

        setSupportActionBar(commentsToolbar);
        getSupportActionBar().setTitle("Comments");

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        current_user_id = firebaseAuth.getCurrentUser().getUid();
        mading_post_id = getIntent().getStringExtra("mading_post_id");

        //Recycler Comments
        comments_list = new ArrayList<>();
        user_list = new ArrayList<>();
        commentAdapter = new CommentsRecyclerAdapter(comments_list,user_list);
        commentsList.setHasFixedSize(true);
        commentsList.setLayoutManager(new LinearLayoutManager(this));
        commentsList.setAdapter(commentAdapter);

        firebaseFirestore.collection("Posts/" + mading_post_id + "/Comments")
                .addSnapshotListener(CommentsActivity.this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                if (!documentSnapshots.isEmpty()) {

                    for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                        if (doc.getType() == DocumentChange.Type.ADDED) {

                            String commentId = doc.getDocument().getId();
                            final Comments comments = doc.getDocument().toObject(Comments.class).withId(commentId);

                            String commentsUserId = doc.getDocument().getString("user_id");
                            firebaseFirestore.collection("Users").document(commentsUserId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                    if (task.isSuccessful()){

                                        User user = task.getResult().toObject(User.class);
                                        user_list.add(user);
                                        comments_list.add(comments);
                                        commentAdapter.notifyDataSetChanged();
                                    }

                                }
                            });

                        }
                    }
                }

            }
        });
    }

    @OnClick(R.id.comments_sent)
    public void onViewClicked() {

        String comment_message = commentsField.getText().toString().trim();

        if (!comment_message.isEmpty()){

            HashMap<String, Object> commentsMap = new HashMap<>();
            commentsMap.put("message", comment_message);
            commentsMap.put("user_id", current_user_id);
            commentsMap.put("timestamp", FieldValue.serverTimestamp());

            firebaseFirestore.collection("Posts/" + mading_post_id + "/Comments").add(commentsMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                @Override
                public void onComplete(@NonNull Task<DocumentReference> task) {

                    if (!task.isSuccessful()){

                        String errMessage = task.getException().getMessage();
                        Toast.makeText(CommentsActivity.this, "Error : " + errMessage, Toast.LENGTH_SHORT).show();

                    }

                }
            });

        }
    }
}

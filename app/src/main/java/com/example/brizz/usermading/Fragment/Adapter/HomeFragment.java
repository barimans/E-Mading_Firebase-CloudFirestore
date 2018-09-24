package com.example.brizz.usermading.Fragment.Adapter;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.brizz.usermading.ActivityE_Mading.NewPostActivity;
import com.example.brizz.usermading.Adapter.MadingRecyclerAdapter;
import com.example.brizz.usermading.Model.MadingPost;
import com.example.brizz.usermading.Model.User;
import com.example.brizz.usermading.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    @BindView(R.id.mading_list_view)
    RecyclerView madingListView;
    Unbinder unbinder;
    private FloatingActionButton fab;
    private List<MadingPost> mading_list;
    private List<User> user_list;

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private MadingRecyclerAdapter madingRecyclerAdapter;
    private DocumentSnapshot lastVisible;
    private Boolean isFirstPageFirstLoad = true;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goNewPost = new Intent(getActivity(), NewPostActivity.class);
                getActivity().startActivity(goNewPost);
            }
        });
        unbinder = ButterKnife.bind(this, view);

        mading_list = new ArrayList<>();
        user_list =new ArrayList<>();

        firebaseAuth = FirebaseAuth.getInstance();

        madingRecyclerAdapter = new MadingRecyclerAdapter(mading_list, user_list);
        madingListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        madingListView.setAdapter(madingRecyclerAdapter);
        madingListView.setHasFixedSize(true);

        if (firebaseAuth.getCurrentUser() != null) {

            firebaseFirestore = FirebaseFirestore.getInstance();

            madingListView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    Boolean reachedBottom = !recyclerView.canScrollVertically(1);

                    if(reachedBottom){

                        loadMorePost();

                    }
                }
            });

            Query firstQuery = firebaseFirestore.collection("Posts").orderBy("timestamp", Query.Direction.DESCENDING).limit(3);
            firstQuery.addSnapshotListener(getActivity(), new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                    if (!documentSnapshots.isEmpty()) {

                        if (isFirstPageFirstLoad){

                            lastVisible = documentSnapshots.getDocuments().get(documentSnapshots.size() -1);
                            mading_list.clear();
                            user_list.clear();

                        }

                        for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                            if (doc.getType() == DocumentChange.Type.ADDED) {

                                final String madingPostId = doc.getDocument().getId();
                                final MadingPost madingPost = doc.getDocument().toObject(MadingPost.class).withId(madingPostId);

                                String madingUserId = doc.getDocument().getString("user_id");
                                firebaseFirestore.collection("Users").document(madingUserId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                        if (task.isSuccessful()){

                                            User user = task.getResult().toObject(User.class);

                                            if (isFirstPageFirstLoad){

                                                user_list.add(user);
                                                mading_list.add(madingPost);

                                            }else{

                                                user_list.add(0,user);
                                                mading_list.add(0,madingPost);
                                            }

                                            madingRecyclerAdapter.notifyDataSetChanged();

                                        }

                                    }
                                });
                            }
                        }

                        isFirstPageFirstLoad = false;
                    }

                }
            });

        }

        return view;
    }

    public void loadMorePost(){

        Query nextQuery = firebaseFirestore.collection("Posts")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .startAfter(lastVisible)
                .limit(3);
        nextQuery.addSnapshotListener(getActivity(), new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                if (!documentSnapshots.isEmpty()) {

                    lastVisible = documentSnapshots.getDocuments().get(documentSnapshots.size() -1);

                    for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                        if (doc.getType() == DocumentChange.Type.ADDED) {

                            String madingPostId = doc.getDocument().getId();
                            final MadingPost madingPost = doc.getDocument().toObject(MadingPost.class).withId(madingPostId);

                            String madingUserId = doc.getDocument().getString("user_id");
                            firebaseFirestore.collection("Users").document(madingUserId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                    if (task.isSuccessful()){

                                        User user = task.getResult().toObject(User.class);

                                        user_list.add(user);
                                        mading_list.add(madingPost);

                                        madingRecyclerAdapter.notifyDataSetChanged();

                                    }

                                }
                            });

                        }
                    }
                }

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

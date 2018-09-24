package com.example.brizz.usermading.Fragment.Adapter;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.brizz.usermading.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment {


    @BindView(R.id.profil_username)
    TextView profilUsername;
    @BindView(R.id.profil_notelp)
    TextView profilNotelp;
    @BindView(R.id.profil_email)
    TextView profilEmail;
    @BindView(R.id.profil_image)
    CircleImageView profilImage;
    Unbinder unbinder;

    private String user_id;
    private String get_email;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    public UserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        unbinder = ButterKnife.bind(this, view);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        user_id = firebaseAuth.getCurrentUser().getUid();
        get_email = firebaseAuth.getCurrentUser().getEmail();

        profilEmail.setText(get_email);

        firebaseFirestore.collection("Users").document(user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()){

                    if (task.getResult().exists()){

                        String profil_name = task.getResult().getString("name");
                        String profil_phone = task.getResult().getString("phone");
                        String profil_image = task.getResult().getString("image");
                        profilUsername.setText(profil_name);
                        profilNotelp.setText(profil_phone);

                        RequestOptions placeholderRequest = new RequestOptions();
                        placeholderRequest.placeholder(R.drawable.set_profil);

                        Glide.with(getActivity()).setDefaultRequestOptions(placeholderRequest).load(profil_image).into(profilImage);

                    }

                }

            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

package com.android.evgeniy.firebaseblog.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.evgeniy.firebaseblog.R;
import com.android.evgeniy.firebaseblog.models.UserDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {
    private View view;
    private TextView textFirstName;
    private TextView textLastName;
    private TextView textEmail;
    private TextView textAge;
    private TextView textGender;
    private UserDetails userDetails;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        textFirstName = view.findViewById(R.id.textFirstName);
        textLastName = view.findViewById(R.id.textLastName);
        textEmail = view.findViewById(R.id.textEmail);
        textAge = view.findViewById(R.id.textAge);
        textGender = view.findViewById(R.id.textGender);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        String userId;
        if (getArguments() != null && getArguments().containsKey("userId")) {
            userId = getArguments().getString("userId");
        } else userId = mAuth.getUid();

        if (mAuth != null) {
            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference(userId + "/Details");

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    userDetails = dataSnapshot.getValue(UserDetails.class);
                    updateUi();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }
        return view;
    }

    public void updateUi() {
        textGender.setText(userDetails.getGender());
        textAge.setText(String.valueOf(userDetails.getAge()));
        textEmail.setText(userDetails.getEmail());
        textLastName.setText(userDetails.getLastName());
        textFirstName.setText(userDetails.getFirstName());
    }
}

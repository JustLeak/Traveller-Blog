package com.android.evgeniy.firebaseblog.dataaccess;

import android.support.annotation.NonNull;

import com.android.evgeniy.firebaseblog.dataaccess.api.IUserDetailsDao;
import com.android.evgeniy.firebaseblog.models.UserDetails;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserDetailsDao implements IUserDetailsDao {
    private final String childName = "/Details";
    private DatabaseReference mRef;
    private UserDetails userDetails = new UserDetails();

    @Override
    public void setOneByUid(UserDetails userDetails, String uId) {
        if (userDetails != null || !uId.isEmpty()) {
            mRef = FirebaseDatabase.getInstance().getReference().child(uId + childName);
            mRef.setValue(userDetails);
        }
    }

    @Override
    public String getNameByUid(String uId) {
        mRef = FirebaseDatabase.getInstance().getReference().child(uId + childName + "/firstName");
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userDetails.setFirstName(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return userDetails.getFirstName();
    }

    @Override
    public String getSurnameByUid(String uId) {
        mRef = FirebaseDatabase.getInstance().getReference().child(uId + childName + "/lastName");
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                userDetails.setLastName(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return userDetails.getLastName();
    }
}

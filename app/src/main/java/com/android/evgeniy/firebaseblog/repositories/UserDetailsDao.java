package com.android.evgeniy.firebaseblog.repositories;

import com.android.evgeniy.firebaseblog.models.UserDetails;
import com.android.evgeniy.firebaseblog.repositories.interfaces.IUserDetailsDao;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserDetailsDao implements IUserDetailsDao {
    private DatabaseReference mRef;
    private final String childName = "/Details";

    @Override
    public void setOneByUid(UserDetails userDetails, String uid) {
        if (userDetails != null || !uid.isEmpty()) {
            mRef = FirebaseDatabase.getInstance().getReference().child(uid + childName);
            mRef.setValue(userDetails);
        }
    }
}

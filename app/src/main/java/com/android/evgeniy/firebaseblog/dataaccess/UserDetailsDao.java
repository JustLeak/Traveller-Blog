package com.android.evgeniy.firebaseblog.dataaccess;

import com.android.evgeniy.firebaseblog.dataaccess.api.IUserDetailsDao;
import com.android.evgeniy.firebaseblog.models.UserDetails;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserDetailsDao implements IUserDetailsDao {
    private final String childName = "/Details";
    private DatabaseReference mRef;

    @Override
    public void setOneByUid(UserDetails userDetails, String uid) {
        if (userDetails != null || !uid.isEmpty()) {
            mRef = FirebaseDatabase.getInstance().getReference().child(uid + childName);
            mRef.setValue(userDetails);
        }
    }
}

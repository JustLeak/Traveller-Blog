package com.android.evgeniy.firebaseblog.repositories;

import com.android.evgeniy.firebaseblog.models.UserDetails;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserDao implements UserDetailsDao, UserNotesDao {

    @Override
    public void addUserDetails(UserDetails userDetails, String uid) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(uid + "/Details");
        databaseReference.setValue(userDetails);
    }
}

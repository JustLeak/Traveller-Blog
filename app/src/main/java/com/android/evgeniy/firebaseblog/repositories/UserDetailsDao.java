package com.android.evgeniy.firebaseblog.repositories;

import com.android.evgeniy.firebaseblog.models.UserDetails;
import com.google.firebase.database.FirebaseDatabase;

public interface UserDetailsDao {
    void addUserDetails(UserDetails userDetails, String uid);
}

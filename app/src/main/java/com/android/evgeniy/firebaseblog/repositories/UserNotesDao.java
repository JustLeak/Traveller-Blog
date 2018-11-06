package com.android.evgeniy.firebaseblog.repositories;

import com.android.evgeniy.firebaseblog.models.UserNote;
import com.android.evgeniy.firebaseblog.repositories.interfaces.IUserNotesDao;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserNotesDao implements IUserNotesDao {
    private DatabaseReference mRef;
    private final String childName = "/Notes";

    @Override
    public void addOneByUid(UserNote userNote, String uid) {
        if (userNote != null || !uid.isEmpty()) {
            mRef = FirebaseDatabase.getInstance().getReference().child(uid + childName);
            mRef.push().setValue(userNote);
        }
    }
}

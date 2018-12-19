package com.android.evgeniy.firebaseblog.dataaccess.api;

import android.support.v7.widget.RecyclerView;

import com.android.evgeniy.firebaseblog.models.UserNote;
import com.google.firebase.database.DatabaseReference;

public interface IUserNotesDao {
    void addOneByUid(UserNote userNote, String uid);

    void getAll(RecyclerView.Adapter adapter);

    void removeByKey(String ownerId, String key);

    void removeByReference(DatabaseReference reference);
}

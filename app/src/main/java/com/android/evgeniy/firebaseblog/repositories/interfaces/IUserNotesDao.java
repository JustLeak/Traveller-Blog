package com.android.evgeniy.firebaseblog.repositories.interfaces;

import com.android.evgeniy.firebaseblog.models.UserNote;

public interface IUserNotesDao {
    void addOneByUid(UserNote userNote, String uid);
}

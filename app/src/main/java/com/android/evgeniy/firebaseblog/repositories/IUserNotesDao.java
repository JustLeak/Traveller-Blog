package com.android.evgeniy.firebaseblog.repositories;

import com.android.evgeniy.firebaseblog.models.UserNote;

public interface IUserNotesDao {
    void addOneByUid(UserNote userNote, String uid);
}

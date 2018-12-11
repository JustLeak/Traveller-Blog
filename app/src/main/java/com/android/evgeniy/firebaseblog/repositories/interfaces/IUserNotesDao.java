package com.android.evgeniy.firebaseblog.repositories.interfaces;

import android.support.v4.app.Fragment;

import com.android.evgeniy.firebaseblog.models.UserNote;

public interface IUserNotesDao {
    void  addOneByUid(UserNote userNote, String uid);
    void getAll(Fragment fragment);
}

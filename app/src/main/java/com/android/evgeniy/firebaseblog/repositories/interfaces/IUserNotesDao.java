package com.android.evgeniy.firebaseblog.repositories.interfaces;

import com.android.evgeniy.firebaseblog.models.UserNote;

import java.util.ArrayList;

public interface IUserNotesDao {
    void  addOneByUid(UserNote userNote, String uid);
    ArrayList<UserNote> getAll();
    UserNote getOneById(int id);
    int getCount();
}

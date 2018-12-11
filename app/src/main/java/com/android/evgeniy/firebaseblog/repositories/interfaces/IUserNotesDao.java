package com.android.evgeniy.firebaseblog.repositories.interfaces;

import com.android.evgeniy.firebaseblog.models.UserNote;

import java.util.List;

public interface IUserNotesDao {
    void  addOneByUid(UserNote userNote, String uid);
    List<UserNote> getAll();
    UserNote getOneById(int id);
}

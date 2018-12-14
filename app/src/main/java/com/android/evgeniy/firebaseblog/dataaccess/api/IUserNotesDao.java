package com.android.evgeniy.firebaseblog.dataaccess.api;

import android.support.v7.widget.RecyclerView;

import com.android.evgeniy.firebaseblog.models.UserNote;

public interface IUserNotesDao {
    void  addOneByUid(UserNote userNote, String uid);
    void getAll(RecyclerView.Adapter adapter);
}

package com.android.evgeniy.firebaseblog.repositories.interfaces;

import android.support.v7.widget.RecyclerView;

import com.android.evgeniy.firebaseblog.models.Friend;
import com.android.evgeniy.firebaseblog.models.UserNote;

import java.util.ArrayList;

public interface IUserFriendsDao {
    void  addOneByUid(Friend friend, String uid);
    void getAll(RecyclerView.Adapter adapter);
}

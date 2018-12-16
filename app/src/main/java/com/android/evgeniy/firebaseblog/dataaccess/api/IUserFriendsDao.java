package com.android.evgeniy.firebaseblog.dataaccess.api;

import android.support.v7.widget.RecyclerView;

import com.android.evgeniy.firebaseblog.models.Friend;

public interface IUserFriendsDao {
    void addOneByUid(Friend friend, String uid);

    void getAll(RecyclerView.Adapter adapter);
}

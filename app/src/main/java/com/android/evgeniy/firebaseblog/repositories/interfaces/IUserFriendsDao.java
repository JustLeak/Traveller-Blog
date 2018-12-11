package com.android.evgeniy.firebaseblog.repositories.interfaces;

import com.android.evgeniy.firebaseblog.models.Friend;

import java.util.ArrayList;

public interface IUserFriendsDao {
    void  addOneByUid(Friend friend);
    ArrayList<Friend> getAll();
    Friend getOneById(int id);
    int getCount();
}

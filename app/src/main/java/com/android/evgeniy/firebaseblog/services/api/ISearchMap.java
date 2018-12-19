package com.android.evgeniy.firebaseblog.services.api;

import android.content.Context;

import com.android.evgeniy.firebaseblog.models.Friend;

import java.util.ArrayList;

public interface ISearchMap {
    void findFriendByEmail(final String email, Context viewContext, ArrayList<Friend> friends);

    void addMapItem(String email);
}

package com.android.evgeniy.firebaseblog.services.api;

import android.content.Context;

public interface ISearchMap {
    void findFriendByEmail(final String email, Context viewContext);

    void addMapItem(String email);
}

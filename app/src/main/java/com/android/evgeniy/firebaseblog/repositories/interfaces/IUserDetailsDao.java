package com.android.evgeniy.firebaseblog.repositories.interfaces;

import com.android.evgeniy.firebaseblog.models.UserDetails;

public interface IUserDetailsDao {
    void setOneByUid(UserDetails userDetails, String uid);
}

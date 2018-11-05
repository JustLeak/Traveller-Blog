package com.android.evgeniy.firebaseblog.repositories;

import com.android.evgeniy.firebaseblog.models.UserDetails;

public interface IUserDetailsDao {
    void setOneByUid(UserDetails userDetails, String uid);
}

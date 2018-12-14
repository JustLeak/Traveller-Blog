package com.android.evgeniy.firebaseblog.dataaccess.api;

import com.android.evgeniy.firebaseblog.models.UserDetails;

public interface IUserDetailsDao {
    void setOneByUid(UserDetails userDetails, String uid);
}

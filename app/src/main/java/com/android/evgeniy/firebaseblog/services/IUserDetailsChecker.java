package com.android.evgeniy.firebaseblog.services;

import com.android.evgeniy.firebaseblog.models.UserDetails;

public interface IUserDetailsChecker {
    boolean isCorrectUserDetails(UserDetails userDetails);
}

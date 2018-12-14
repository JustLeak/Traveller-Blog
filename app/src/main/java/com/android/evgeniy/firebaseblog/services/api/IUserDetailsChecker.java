package com.android.evgeniy.firebaseblog.services.api;

import com.android.evgeniy.firebaseblog.models.UserDetails;

import java.util.HashMap;

public interface IUserDetailsChecker {
    boolean isCorrectUserDetails(UserDetails userDetails);

    boolean isCorrectInputs(HashMap<String, String> inputs);
}

package com.android.evgeniy.firebaseblog.services;

import com.android.evgeniy.firebaseblog.models.UserDetails;

public class Checker implements IUserDetailsChecker {
    @Override
    public boolean isCorrectUserDetails(UserDetails userDetails) {
        if (userDetails.getAge() == null)
            return false;
        else if (userDetails.getEmail().isEmpty())
            return false;
        else if (userDetails.getGender().isEmpty())
            return false;
        else if (userDetails.getName().isEmpty())
            return false;
        else return !userDetails.getSurname().isEmpty();
    }
}

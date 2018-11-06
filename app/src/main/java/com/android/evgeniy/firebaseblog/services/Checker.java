package com.android.evgeniy.firebaseblog.services;

import com.android.evgeniy.firebaseblog.models.UserDetails;

import java.util.HashMap;

public class Checker implements IUserDetailsChecker {
    @Override
    public boolean isCorrectUserDetails(UserDetails userDetails) {
        if (userDetails.getAge() == null)
            return false;
        else if (userDetails.getEmail().isEmpty())
            return false;
        else if (userDetails.getGender().isEmpty())
            return false;
        else if (userDetails.getFirstName().isEmpty())
            return false;
        else return !userDetails.getLastName().isEmpty();
    }

    @Override
    public boolean isCorrectInputs(HashMap<String, String> inputs) {

        for (String key : inputs.keySet()) {
            if (inputs.get(key).equals("")) {
                return false;
            }
        }
        return true;
    }
}

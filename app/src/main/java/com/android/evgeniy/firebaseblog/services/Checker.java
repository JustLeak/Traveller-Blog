package com.android.evgeniy.firebaseblog.services;

import com.android.evgeniy.firebaseblog.models.UserDetails;

import java.util.HashMap;

import lombok.Getter;

@Getter
public class Checker implements IUserDetailsChecker {
    private HashMap<String, Boolean> resultMap = new HashMap<>();

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
        resultMap.clear();
        for (String key : inputs.keySet()) {
            if (inputs.get(key).equals("")) {
                resultMap.put(key, false);
            } else
                resultMap.put(key, true);
        }
        return !resultMap.containsValue(false);
    }

    public boolean isEmptyResult() {
        return resultMap.isEmpty();
    }
}

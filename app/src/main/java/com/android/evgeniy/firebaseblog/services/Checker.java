package com.android.evgeniy.firebaseblog.services;

import com.android.evgeniy.firebaseblog.services.api.IUserDetailsChecker;

import java.util.HashMap;

import lombok.Getter;

@Getter
public class Checker implements IUserDetailsChecker {
    private HashMap<String, Boolean> resultMap = new HashMap<>();

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
}

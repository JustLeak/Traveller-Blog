package com.android.evgeniy.firebaseblog.services;

import com.android.evgeniy.firebaseblog.models.UserDetails;

import java.util.HashMap;

public class UserDetailsSetter {
    public static UserDetails set(HashMap<String, String> inputs) {
        return UserDetails.builder().email(inputs.get("email"))
                .firstName(inputs.get("firstName"))
                .lastName(inputs.get("lastName"))
                .age(Integer.valueOf(inputs.get("age")))
                .gender(inputs.get("gender")).build();
    }
}

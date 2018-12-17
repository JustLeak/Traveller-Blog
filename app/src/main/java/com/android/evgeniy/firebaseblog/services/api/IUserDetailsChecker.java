package com.android.evgeniy.firebaseblog.services.api;

import java.util.HashMap;

public interface IUserDetailsChecker {
    boolean isCorrectInputs(HashMap<String, String> inputs);
}

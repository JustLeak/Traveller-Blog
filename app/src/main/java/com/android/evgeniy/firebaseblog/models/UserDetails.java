package com.android.evgeniy.firebaseblog.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@ToString
public class UserDetails {
    private String email;
    private String name;
    private String surname;
    private Integer age;
    private String gender;
}

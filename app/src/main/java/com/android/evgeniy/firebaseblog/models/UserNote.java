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
public class UserNote {
    private String date;
    private String time;
    private String text;
    private Location location;
    private String ownerId;
}

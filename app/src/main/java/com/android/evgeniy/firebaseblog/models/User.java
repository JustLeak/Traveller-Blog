package com.android.evgeniy.firebaseblog.models;


import java.util.ArrayList;
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
public class User {
    String uid;
    UserDetails userDetails;
    ArrayList<UserNote> userNotes;
}

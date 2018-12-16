package com.android.evgeniy.firebaseblog.models;

import android.os.Parcel;
import android.os.Parcelable;

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
public class UserNote implements Parcelable {
    public static final Parcelable.Creator<UserNote> CREATOR = new Parcelable.Creator<UserNote>() {
        public UserNote createFromParcel(Parcel in) {
            return new UserNote(in);
        }

        public UserNote[] newArray(int size) {
            return new UserNote[size];
        }
    };
    private String key;
    private String date;
    private String time;
    private String text;
    private Location location;
    private String ownerId;

    private UserNote(Parcel in) {
        String[] data = new String[7];
        in.readStringArray(data);
        key = data[0];
        date = data[1];
        time = data[2];
        text = data[3];
        location = new Location();
        location.setLat(data[4]);
        location.setLng(data[5]);
        ownerId = data[6];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        String[] data = {key, date, time, text, location.getLat(), location.getLng(), ownerId};
        dest.writeStringArray(data);
    }
}

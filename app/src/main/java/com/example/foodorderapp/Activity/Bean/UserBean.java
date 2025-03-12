package com.example.foodorderapp.Activity.Bean;

import android.os.Parcel;
import android.os.Parcelable;
import lombok.Data;
import lombok.NonNull;

@Data
public class UserBean implements Parcelable {
    private int id;
    private String username;
    private String email;
    private String password;

    public UserBean(int id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    protected UserBean(Parcel in) {
        id = in.readInt();
        username = in.readString();
        email = in.readString();
        password = in.readString();
    }

    public static final Creator<UserBean> CREATOR = new Creator<UserBean>() {
        @Override
        public UserBean createFromParcel(Parcel in) {
            return new UserBean(in);
        }

        @Override
        public UserBean[] newArray(int size) {
            return new UserBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(username);
        dest.writeString(email);
        dest.writeString(password);
    }
}
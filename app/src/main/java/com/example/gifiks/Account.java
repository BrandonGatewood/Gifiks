package com.example.gifiks;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Account implements Parcelable {
    private final String username;
    private final String email;
    private final String password;
    
    // List of gif post account has made
    // private final ArrayList<posts> posts;


    public Account(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    protected Account(Parcel in) {
        username = in.readString();
        email = in.readString();
        password = in.readString();
    }

    public static final Creator<Account> CREATOR = new Creator<Account>() {
        @Override
        public Account createFromParcel(Parcel in) {
            return new Account(in);
        }

        @Override
        public Account[] newArray(int size) {
            return new Account[size];
        }
    };

    // Getter methods
    public String getUsername() {
        return this.username;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(username);
        parcel.writeString(email);
        parcel.writeString(password);
    }
}

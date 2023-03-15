package com.example.gifiks;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Account implements Parcelable {
    private String username;
    private String email;

    private String bio;

    public Account(String username, String email) {
        this.username = username;
        this.email = email;
        this.bio = "This is a BIO";
    }

    protected Account(Parcel in) {
        username = in.readString();
        email = in.readString();
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
    public String getBio() {
        return this.bio;
    }

    // Setter methods
    public void updateUsername(String newUsername) {
        this.username = newUsername;
    }
    public void updateBio(String newBio) {
        this.bio = newBio;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(username);
        parcel.writeString(email);
    }
}

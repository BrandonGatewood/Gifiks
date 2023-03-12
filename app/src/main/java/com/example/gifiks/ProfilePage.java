package com.example.gifiks;

import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;
import com.example.gifiks.databinding.ActivityProfilePageBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import pl.droidsonroids.gif.GifImageView;


public class ProfilePage extends Fragment {
    private ActivityProfilePageBinding binding;
    BottomNavigationView bottomNavigationView;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        setHasOptionsMenu(true);
        binding = ActivityProfilePageBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // bundle contains Account object
        Bundle bundle = this.getArguments();
        Account receivedAccount = Objects.requireNonNull(bundle).getParcelable("AccountInfo");

        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()).setTitle(receivedAccount.getUsername());
        bottomNavigationView = view.findViewById(R.id.bottomNavigationView);

        // Set up user profile
        setUserBio(view, receivedAccount);
        setUserProfilePicture(view, receivedAccount);

        // Use bottom nav bar to move to Home Page
        binding.bottomNavigationView.findViewById(R.id.homePageIcon).setOnClickListener(view1 -> NavHostFragment.findNavController(ProfilePage.this)
                .navigate(R.id.action_to_HomePageFragment, bundle));
        // Use bottom nav bar to move to upload gif page
        binding.bottomNavigationView.findViewById(R.id.uploadGifIcon).setOnClickListener(view2 -> NavHostFragment.findNavController(ProfilePage.this)
                .navigate(R.id.action_to_UploadGifFragment, bundle));
        // Move to login page
        binding.logout.setOnClickListener(view2 -> NavHostFragment.findNavController(ProfilePage.this)
                .navigate(R.id.action_to_LoginFragment));
    }

    /*
        Checks database for the users profile picture. If none exists then no profile will be empty.
     */
    private void setUserProfilePicture(View view, Account user) {
        GifImageView profilePicture = view.findViewById(R.id.profilePicture);
        String profilePicturePath = Objects.requireNonNull(this.getContext()).getDataDir().getAbsolutePath() + File.separator + "profilePicture" + File.separator + user.getUsername() + ".gif";
        File projDir = new File(profilePicturePath);
        Glide.with(this).load(projDir).into(profilePicture);

    }

    /*
        Sets the users bio from an Account object.
     */
    private void setUserBio(View view, Account user) {
        TextView setBio = view.findViewById(R.id.bio);
        setBio.setText(user.getBio());
    }

    // Both functions, onResume() and onStop() will remove back button from action bar.
    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()). setDisplayHomeAsUpEnabled(false);
    }

    @Override
    public void onStop() {
        super.onStop();
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()). setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



}
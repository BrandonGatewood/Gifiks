package com.example.gifiks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.gifiks.databinding.ActivityProfilePageBinding;

import java.util.Objects;


public class ProfilePage extends Fragment {
    private ActivityProfilePageBinding binding;
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = ActivityProfilePageBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Move to Home Page
        binding.homePage.setOnClickListener(view1 -> NavHostFragment.findNavController(ProfilePage.this)
                .navigate(R.id.action_to_HomePageFragment));
        // Move to Upload Gif page
        binding.uploadGif.setOnClickListener(view2 -> NavHostFragment.findNavController(ProfilePage.this)
                .navigate(R.id.action_to_UploadGifFragment));
        // Move to login page
        binding.logout.setOnClickListener(view2 -> NavHostFragment.findNavController(ProfilePage.this)
                .navigate(R.id.action_to_LoginFragment));
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
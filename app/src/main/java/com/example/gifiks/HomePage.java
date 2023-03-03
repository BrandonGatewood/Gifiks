package com.example.gifiks;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.gifiks.databinding.ActivityHomePageBinding;

import java.util.Objects;

public class HomePage extends Fragment {
    private ActivityHomePageBinding binding;
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = ActivityHomePageBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // bundle contains Account object
        Bundle bundle = this.getArguments();
        Account receivedAccount = Objects.requireNonNull(bundle).getParcelable("AccountInfo");

        // Move to Profile Page
        binding.profilePage.setOnClickListener(view1 -> {
            String message = "Username: " + receivedAccount.getUsername() + "\nEmail: " + receivedAccount.getEmail();
            Toast.makeText(view1.getContext(), message, Toast.LENGTH_LONG).show();


            NavHostFragment.findNavController(HomePage.this)
                    .navigate(R.id.action_to_ProfilePageFragment);
        });


        // Move to upload gif page
        binding.uploadGif.setOnClickListener(view2 -> NavHostFragment.findNavController(HomePage.this)
                .navigate(R.id.action_to_UploadGifFragment));

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
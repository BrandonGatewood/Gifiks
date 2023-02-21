package com.example.gifiks;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import com.example.gifiks.databinding.ActivityLoginPageBinding;

public class LoginPage extends Fragment {
    private ActivityLoginPageBinding binding;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = ActivityLoginPageBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(LoginPage.this)
                        .navigate(R.id.action_to_CreateAccountFragment);
            }
        });
        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(LoginPage.this)
                        .navigate(R.id.action_to_UploadGifFragment);
            }
        });
    }

    /*
    * Starts a new activity to create a new account.
    public void createAccount(View view) {
        Intent intent = new Intent(this, CreateAccount.class);
        startActivity(intent);
    }*/

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
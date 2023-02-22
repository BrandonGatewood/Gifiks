package com.example.gifiks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
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

        final EditText viewUsername = view.findViewById(R.id.loginUsername);
        final EditText viewPassword = view.findViewById(R.id.loginPassword);

        binding.createAccount.setOnClickListener(view12 -> NavHostFragment.findNavController(LoginPage.this)
                .navigate(R.id.action_to_CreateAccountFragment));

        binding.login.setOnClickListener(view1 -> {
            String username = viewUsername.getText().toString();
            String password = viewPassword.getText().toString();

            // Check if username and password
            if(username.isEmpty()) {
                Toast.makeText(view1.getContext(), "Missing Username", Toast.LENGTH_LONG).show();
            }
            else if(password.isEmpty()) {
                Toast.makeText(view1.getContext(), "Missing Password", Toast.LENGTH_LONG).show();
            }
            // Validate login credentials
            else {
                Toast.makeText(view1.getContext(), "Validate", Toast.LENGTH_LONG).show();
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
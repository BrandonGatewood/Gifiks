package com.example.gifiks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.gifiks.databinding.ActivityLoginPageBinding;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Objects;

public class LoginPage extends Fragment {
    private ActivityLoginPageBinding binding;
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = ActivityLoginPageBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final EditText viewUsername = view.findViewById(R.id.loginUsername);
        final EditText viewPassword = view.findViewById(R.id.loginPassword);

        binding.createAccount.setOnClickListener(view1 -> NavHostFragment.findNavController(LoginPage.this)
                .navigate(R.id.action_to_CreateAccountFragment));

        binding.login.setOnClickListener(view2 -> {
            String username = viewUsername.getText().toString();
            String password = viewPassword.getText().toString();

            // Check if username and password has been entered
            if(username.isEmpty()) {
                Toast.makeText(view2.getContext(), "Missing Username", Toast.LENGTH_LONG).show();
            }
            else if(password.isEmpty()) {
                Toast.makeText(view2.getContext(), "Missing Password", Toast.LENGTH_LONG).show();
            }
            else {
                // Validate login credentials
                try {
                    if(validateLoginCredentials(username, password)) {
                        NavHostFragment.findNavController(LoginPage.this)
                                .navigate(R.id.action_to_HomePageFragment);
                    }
                    else {
                        Toast.makeText(view2.getContext(), "Username or password is incorrect", Toast.LENGTH_LONG).show();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    /*
        Validates login credentials to sign into users account. Returns true if login credentials are
        correct, returns false if login credentials are wrong.
     */
    private boolean validateLoginCredentials(String username, String password) throws IOException {
        File directory = Objects.requireNonNull(this.getContext()).getDataDir();
        File accounts = new File(directory, "accounts.txt");
        Reader reader = new FileReader(accounts);

        try (
                BufferedReader br = new BufferedReader(reader)
        ) {
            String toParse = br.readLine();

            while(toParse != null) {
                // Index 0 is username
                // Index 1 is email
                // Index 2 is password
                String[] loginCredentials = toParse.split(";");

                // Found a match, login credentials are correct
                if(username.equals(loginCredentials[0]) && password.equals(loginCredentials[2]))
                    return true;

                toParse = br.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // No match found

        return false;
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
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

            if(username.isEmpty()) {
                Toast.makeText(view2.getContext(), "Missing Username", Toast.LENGTH_LONG).show();
            }
            else if(password.isEmpty()) {
                Toast.makeText(view2.getContext(), "Missing Password", Toast.LENGTH_LONG).show();
            }
            else {
                // Validate login credentials
                try {
                    Account usersAccount = validateLoginCredentials(username, password);
                    if(usersAccount != null) {
                        /*
                            Bundle is used to pass Account objects through fragments, with a key and
                            value.
                            key = "AccountInfo"
                            value = usersAccount
                        */
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("AccountInfo", usersAccount);

                        NavHostFragment.findNavController(LoginPage.this)
                                .navigate(R.id.action_to_HomePageFragment, bundle);
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
        Validates login credentials to sign into users account. If login credentials are correct,
        then it returns an Account object for that user. If login credentials are incorrect, then
        it returns null.
     */
    private Account validateLoginCredentials(String username, String password) throws IOException {
        File directory = Objects.requireNonNull(this.getContext()).getDataDir();
        File accountsFile = new File(directory, "accounts.txt");
        Reader reader = new FileReader(accountsFile);
        try (
                BufferedReader br = new BufferedReader(reader)
        ) {
            String toParse = br.readLine();
            Account usersAccount;
            while(toParse != null) {
                // Index 0 is username
                // Index 1 is email
                // Index 2 is password
                String[] loginCredentials = toParse.split(";");

                // Found a match, login credentials are correct
                if(username.equals(loginCredentials[0]) && password.equals(loginCredentials[2])) {
                    usersAccount = new Account(loginCredentials[0], loginCredentials[1]);
                    return usersAccount;
                }
                toParse = br.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // No match found
        return null;
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
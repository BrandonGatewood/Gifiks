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

import com.example.gifiks.databinding.ActivityCreateAccountBinding;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.Objects;


public class CreateAccount extends Fragment {
    private ActivityCreateAccountBinding binding;
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = ActivityCreateAccountBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final EditText viewUsername = view.findViewById(R.id.createAnAccountUsername);
        final EditText viewEmail = view.findViewById(R.id.createAnAccountEmail);
        final EditText viewPassword = view.findViewById(R.id.createAnAccountPassword);

        binding.createAccount.setOnClickListener(view1 -> {
            String username = viewUsername.getText().toString();
            String email = viewEmail.getText().toString();
            String password = viewPassword.getText().toString();

            // Check if username, email, and password has been entered
            if(username.isEmpty()) {
                Toast.makeText(view1.getContext(), "Missing Username", Toast.LENGTH_LONG).show();
            }
            else if(email.isEmpty()) {
                Toast.makeText(view1.getContext(), "Missing Email", Toast.LENGTH_LONG).show();
            }
            else if(password.isEmpty()) {
                Toast.makeText(view1.getContext(), "Missing Password", Toast.LENGTH_LONG).show();
            }
            else {
                // Check if username is already take
                try {
                    if(checkUsernameAndEmail(username, email, view1)) {
                        // Add new account to database
                        String welcome = "Welcome to Gifiks, " + username;
                        addNewAccount(username, email, password);
                        Toast.makeText(view1.getContext(), welcome , Toast.LENGTH_LONG).show();

                        NavHostFragment.findNavController(CreateAccount.this)
                                .navigate(R.id.action_to_HomePageFragment);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    /*
        Checks database (.txt file) if username is already taken. Returns true if not taken and false
        if taken.
     */
    private boolean checkUsernameAndEmail(String username, String email, View view1) throws IOException {
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
                String[] account = toParse.split(";");

                // Username taken
                if(username.equals(account[0])) {
                    Toast.makeText(view1.getContext(), "Username is taken", Toast.LENGTH_LONG).show();
                    return false;
                }
                // Email taken
                if(email.equals(account[1])) {
                    Toast.makeText(view1.getContext(), "Email is taken", Toast.LENGTH_LONG).show();
                    return false;
                }

                toParse = br.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
    /*
        Add new account to database (.txt file)
    */
    private void addNewAccount(String username, String email, String password) throws IOException {
        File directory = Objects.requireNonNull(this.getContext()).getDataDir();
        File accounts = new File(directory, "accounts.txt");

        try (
                PrintWriter pw = new PrintWriter(new FileWriter(accounts, true))
        ){
            pw.println(username + ";" + email + ";" + password);
            pw.flush();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

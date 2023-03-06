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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Objects;


public class CreateAccount extends Fragment {
    private final ArrayList<Account> allAccounts = new ArrayList<>();
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
        File directory = Objects.requireNonNull(this.getContext()).getDataDir();
        File accountsFile = new File(directory, "accounts.txt");

        try {
            parseAccountsFile(accountsFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

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
                    if(!checkUsername(username)) {
                        Toast.makeText(view1.getContext(), "Username is taken", Toast.LENGTH_LONG).show();
                    }
                    if(!checkEmail(email)) {
                        Toast.makeText(view1.getContext(), "Email is taken", Toast.LENGTH_LONG).show();
                    }
                    else {
                        String promptWelcomeMessage = "Welcome to Gifiks, " + username;

                        Account newAccount = new Account(username, email);
                        addNewAccountToTextFile(newAccount, password, accountsFile);

                        Bundle bundle = new Bundle();
                        bundle.putParcelable("AccountInfo", newAccount);

                        Toast.makeText(view1.getContext(), promptWelcomeMessage, Toast.LENGTH_LONG).show();
                        NavHostFragment.findNavController(CreateAccount.this)
                                .navigate(R.id.action_to_HomePageFragment, bundle);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    /*
        Parses through database (.txt file) and save all accounts into an array of Accounts.
     */
    private void parseAccountsFile(File accountsFile) throws FileNotFoundException {
        Reader reader = new FileReader(accountsFile);

        try (
                BufferedReader br = new BufferedReader(reader)
        ) {
            String toParse = br.readLine();

            while(toParse != null) {
                // Index 0 is username
                // Index 1 is email
                // Index 2 is password
                String[] anAccount = toParse.split(";");

                Account account = new Account(anAccount[0], anAccount[1]);
                allAccounts.add(account);
                toParse = br.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*
        Check allAccounts array to see if username has been taken. Returns true if username is not
        taken. Returns false if username is taken.
     */
    private boolean checkUsername(String username) {
        for(Account anAccount : allAccounts) {
            if(anAccount.getUsername().equals(username))
                return false;
        }
        return true;
    }
    /*
       Check allAccounts array to see if email has been taken. Returns true if email is not
       taken. Returns false if email is taken.
    */
    private boolean checkEmail(String email) {
        for(Account anAccount : allAccounts) {
            if(anAccount.getEmail().equals(email))
                return false;
        }
        return true;
    }

    /*
        Add new account to database (.txt file)
    */
    private void addNewAccountToTextFile(Account newAccount, String password, File accountsFile) throws IOException {
        try (
                PrintWriter pw = new PrintWriter(new FileWriter(accountsFile, true))
        ){
            pw.println(newAccount.getUsername() + ";" + newAccount.getEmail() + ";" + password);
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

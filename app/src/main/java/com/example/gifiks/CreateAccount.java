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


public class CreateAccount extends Fragment {
    private ActivityCreateAccountBinding binding;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
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
                if(checkUsername(username)) {
                    Toast.makeText(view1.getContext(), "Username is taken", Toast.LENGTH_LONG).show();
                }
                else {
                    NavHostFragment.findNavController(CreateAccount.this)
                            .navigate(R.id.action_to_UploadGifFragment);
                }
            }
        });
    }

    /*
       Checks database (.txt file) if username is already taken. Returns true if taken and false
       if not taken.
     */
    private boolean checkUsername(String username) {
        return true;
    }

    /*
        Add new account to database (.txt file)
     */
    private void AddNewAccount() {

    }

}

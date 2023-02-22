package com.example.gifiks;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.gifiks.databinding.ActivityCreateAccountBinding;
import com.example.gifiks.databinding.ActivityLoginPageBinding;

import javax.xml.parsers.FactoryConfigurationError;


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

        binding.createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(CreateAccount.this)
                        .navigate(R.id.action_to_UploadGifFragment);
            }
        });
    }
    /**
     * When submit button is clicked, check if ALL input fields have been entered and validate
     * them before submitting.
     */
/*    public void checkInputs(View view) {
        EditText username_field = CreateAccount.this.findViewById(R.id.createAnAccountUsername);
        EditText email_field = CreateAccount.this.findViewById(R.id.createAnAccountEmail);
        EditText password_field = CreateAccount.this.findViewById(R.id.createAnAccountPassword);


        // First check if any of the required fields is missing
        if (TextUtils.isEmpty(username_field.getText().toString())) {
            Toast.makeText(CreateAccount.this, "Missing Username", Toast.LENGTH_LONG).show();
        }
        else if (TextUtils.isEmpty(email_field.getText().toString())) {
            Toast.makeText(CreateAccount.this, "Missing email", Toast.LENGTH_LONG).show();
        }
        else if (TextUtils.isEmpty(password_field.getText().toString())) {
            Toast.makeText(CreateAccount.this, "Missing password", Toast.LENGTH_LONG).show();
        }
        else {
            AddNewAccount();
        }
    }*/
    /**
     * Add new account to database
     */
    private void AddNewAccount() {

    }

}

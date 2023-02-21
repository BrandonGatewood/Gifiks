package com.example.gifiks;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;



public class CreateAccount extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
    }

    /**
     * When submit button is clicked, check if ALL input fields have been entered and validate
     * them before submitting.
     */
    public void checkInputs(View view) {
        EditText username_field = this.findViewById(R.id.createAnAccountUsername);
        EditText email_field = this.findViewById(R.id.createAnAccountEmail);
        EditText password_field = this.findViewById(R.id.createAnAccountPassword);


        // First check if any of the required fields is missing
        if (TextUtils.isEmpty(username_field.getText().toString())) {
            Toast.makeText(this, "Missing Username", Toast.LENGTH_LONG).show();
        }
        else if (TextUtils.isEmpty(email_field.getText().toString())) {
            Toast.makeText(this, "Missing email", Toast.LENGTH_LONG).show();
        }
        else if (TextUtils.isEmpty(password_field.getText().toString())) {
            Toast.makeText(this, "Missing password", Toast.LENGTH_LONG).show();
        }
        else {
            AddNewAccount();
        }
    }
    /**
     * Add new account to database
     */
    private void AddNewAccount() {

    }

}

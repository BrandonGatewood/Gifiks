package com.example.gifiks;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

@RunWith(AndroidJUnit4.class)
public class LoginPageUnitTest {

    @Test
    public void ValidateLoginCredentials_NoUsernameAndPassword_ReturnsNull() throws IOException {
        File directory = ApplicationProvider.getApplicationContext().getDataDir();
        File accountsFile = new File(directory, "accounts.txt");

        assertNull(LoginPage.validateLoginCredentials("", "", accountsFile));

    }

    @Test
    public void ValidateLoginCredentials_IncorrectUserName_ReturnsNull() throws IOException {
        File directory = ApplicationProvider.getApplicationContext().getDataDir();
        File accountsFile = new File(directory, "accounts.txt");

        assertNull(LoginPage.validateLoginCredentials("", "2", accountsFile));
    }

    @Test
    public void ValidateLoginCredentials_IncorrectPassword_ReturnsNull() throws IOException {
        File directory = ApplicationProvider.getApplicationContext().getDataDir();
        File accountsFile = new File(directory, "accounts.txt");

        assertNull(LoginPage.validateLoginCredentials("bg", "", accountsFile));
    }
}

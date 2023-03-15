package com.example.gifiks;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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

        BufferedReader br = new BufferedReader(new FileReader(accountsFile));
        String toParse = br.readLine();
        String[] anAccount = toParse.split(";");

        assertNull(LoginPage.validateLoginCredentials("", anAccount[2], accountsFile));
    }

    @Test
    public void ValidateLoginCredentials_IncorrectPassword_ReturnsNull() throws IOException {
        File directory = ApplicationProvider.getApplicationContext().getDataDir();
        File accountsFile = new File(directory, "accounts.txt");

        BufferedReader br = new BufferedReader(new FileReader(accountsFile));
        String toParse = br.readLine();
        String[] anAccount = toParse.split(";");

        assertNull(LoginPage.validateLoginCredentials(anAccount[0], "", accountsFile));
    }

    @Test
    public void ValidateLoginCredentials_CorrectCredentialsReturnsAccount_AssertTrue() throws IOException {
        File directory = ApplicationProvider.getApplicationContext().getDataDir();
        File accountsFile = new File(directory, "accounts.txt");

        BufferedReader br = new BufferedReader(new FileReader(accountsFile));
        String toParse = br.readLine();
        String[] anAccount = toParse.split(";");

        Account correctAccount = new Account(anAccount[0], anAccount[1], anAccount[3]);
        Account loggingInAccount = LoginPage.validateLoginCredentials(anAccount[0], anAccount[2], accountsFile);

        assert loggingInAccount != null;
        assertTrue(correctAccount.getUsername().equals(loggingInAccount.getUsername()) &&
                correctAccount.getEmail().equals(loggingInAccount.getEmail()) &&
                correctAccount.getBio().equals(loggingInAccount.getBio()));
    }
}

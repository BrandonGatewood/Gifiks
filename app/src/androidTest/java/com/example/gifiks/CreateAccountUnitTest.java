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
public class CreateAccountUnitTest {
    @Test
    public void CheckUsername_UsernameNotTaken_ReturnsTrue() throws IOException {
        File directory = ApplicationProvider.getApplicationContext().getDataDir();
        File accountsFile = new File(directory, "accounts.txt");

        CreateAccount.parseAccountsFile(accountsFile);

        assertTrue(CreateAccount.checkUsername(""));

    }
    @Test
    public void CheckUsername_UsernameTaken_ReturnsFalse() throws IOException {
        File directory = ApplicationProvider.getApplicationContext().getDataDir();
        File accountsFile = new File(directory, "accounts.txt");

        BufferedReader br = new BufferedReader(new FileReader(accountsFile));
        String toParse = br.readLine();
        String[] anAccount = toParse.split(";");

        CreateAccount.parseAccountsFile(accountsFile);

        assertFalse(CreateAccount.checkUsername(anAccount[0]));

    }

    @Test
    public void CheckEmail_EmailNotTaken_ReturnsTrue() throws IOException {
        File directory = ApplicationProvider.getApplicationContext().getDataDir();
        File accountsFile = new File(directory, "accounts.txt");

        CreateAccount.parseAccountsFile(accountsFile);

        assertTrue(CreateAccount.checkEmail(""));

    }
    @Test
    public void CheckEmail_EmailTaken_ReturnsFalse() throws IOException {
        File directory = ApplicationProvider.getApplicationContext().getDataDir();
        File accountsFile = new File(directory, "accounts.txt");

        BufferedReader br = new BufferedReader(new FileReader(accountsFile));
        String toParse = br.readLine();
        String[] anAccount = toParse.split(";");

        CreateAccount.parseAccountsFile(accountsFile);

        assertFalse(CreateAccount.checkEmail(anAccount[1]));
    }
}

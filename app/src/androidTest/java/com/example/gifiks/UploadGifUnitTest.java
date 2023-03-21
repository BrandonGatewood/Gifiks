package com.example.gifiks;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import android.net.Uri;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@RunWith(AndroidJUnit4.class)
public class UploadGifUnitTest {
    private UploadGif gif;
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void preTestSetup() {
        gif = new UploadGif();
    }

    @Test
    public void ChrckUri_Null_ReturnsFalse() throws IOException {
        Uri emptyuri = null;
        assertFalse(gif.saveGif(emptyuri));
    }

    @Test
    public void ChrckUri_badinput_ReturnsNullPointerException() throws IOException {
        expectedException.expect(NullPointerException.class);
        Uri badUri = Uri.fromFile(ApplicationProvider.getApplicationContext().getDataDir());
        gif.saveGif(badUri);
    }
}

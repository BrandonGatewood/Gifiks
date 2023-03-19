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

public class GalleryUnitTest {

    Gallery user;
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void preTestSetup() {
        user = new Gallery();
    }

    @Test
    public void Chrckuser_Null_ReturnsFalse() throws IOException {
        String name = null;
        assertFalse(user.getGifs(name));
    }

    @Test
    public void ChrckUser_badname_ReturnsNullPointerException() throws IOException {
        expectedException.expect(NullPointerException.class);
        String name = "uhuigi";
        user.getGifs(name);
    }

}

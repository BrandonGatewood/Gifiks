package com.example.gifiks;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.gifiks.databinding.ActivityMainBinding;
import com.example.gifiks.databinding.GifDetailActivityBinding;

import java.io.File;

public class GifDetailActivity extends AppCompatActivity {
    private GifDetailActivityBinding binding;
    String imgPath;
    private ImageView imageView;
    private float mScaleFactor = 1.0f;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = GifDetailActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        imgPath = getIntent().getStringExtra("imgPath");
        imageView = findViewById(R.id.idIVImage);

        Glide.with(this).load(imgPath).placeholder(R.drawable.ic_launcher_background).into(imageView);
    }
}

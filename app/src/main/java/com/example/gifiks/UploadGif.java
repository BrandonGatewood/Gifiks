package com.example.gifiks;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;
import com.example.gifiks.databinding.UploadGifBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

//to upload a gif it must be in the memory of the phone. one place to add them is at data/media/0/Pictures
public class UploadGif extends Fragment {
    Button AddGif;
    ImageView ViewGif;

    private UploadGifBinding binding;

    BottomNavigationView bottomNavigationView;

    String user;
    File directory;
    File gifdirectory;
    String gifName;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        setHasOptionsMenu(true);
        binding = UploadGifBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AddGif = (Button) getView().findViewById(R.id.AddGif);
        ViewGif = (ImageView) getView().findViewById(R.id.ViewGif);

        Bundle bundle = this.getArguments();
        bottomNavigationView = (BottomNavigationView) getView().findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.uploadGifIcon);

        Account receivedAccount = Objects.requireNonNull(bundle).getParcelable("AccountInfo");
        user = "Gifs/" + receivedAccount.getUsername();

        directory = Objects.requireNonNull(this.getContext()).getDataDir();

        gifdirectory = new File(directory, user);

        binding.AddGif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });
        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(UploadGif.this)
                        .navigate(R.id.action_to_GalleryFragment, bundle);
            }
        });
        // Use bottom nav bar to move to Profile page
        binding.bottomNavigationView.findViewById(R.id.profilePageIcon).setOnClickListener(view1 -> NavHostFragment.findNavController(this)
                .navigate(R.id.action_to_ProfilePageFragment, bundle));

        // Use bottom nav bar to move to Home Page
        binding.bottomNavigationView.findViewById(R.id.homePageIcon).setOnClickListener(view1 -> NavHostFragment.findNavController(this)
                .navigate(R.id.action_to_HomePageFragment, bundle));
    }

    private void imageChooser(){
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        launchSomeActivity.launch(i);
    }
    ActivityResultLauncher<Intent> launchSomeActivity
            = registerForActivityResult(
            new ActivityResultContracts
                    .StartActivityForResult(),
            result -> {
                if (result.getResultCode()
                        == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null
                            && data.getData() != null) {
                        Uri uri = data.getData();
                        String selectedImageUri = data.getData().toString();
                        gifName = selectedImageUri.substring(selectedImageUri.lastIndexOf("%2F") + 3);
                        Glide.with(this).asGif().load(selectedImageUri).placeholder(R.drawable.ic_launcher_background).into(ViewGif);
                        saveGif(uri);
                    }
                }
            });

    public Boolean saveGif(Uri uri) {
        if(uri == null) {
            return false;
        }
        File newGifFile = new File(gifdirectory, gifName);

        if (newGifFile == null) {
            return false;
        }
        try {
            InputStream input = this.getContext().getContentResolver().openInputStream(uri);
            OutputStream output = new FileOutputStream(newGifFile);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                output.write(input.readAllBytes());
            }

            output.close();
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
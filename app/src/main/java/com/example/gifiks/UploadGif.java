package com.example.gifiks;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.example.gifiks.databinding.UploadGifBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.channels.FileChannel;
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
                        Glide.with(this).asGif().load(selectedImageUri).into(ViewGif);
                        saveGif(uri);
                        /*Target<File> fileTarget = Glide.with(this).asFile()
                                .load(selectedImageUri)
                                .listener(new RequestListener<File>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<File> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(File resource, Object model, Target<File> target, DataSource dataSource, boolean isFirstResource) {
                                        return false;
                                    }
                                })
                                .into(new Target<File>() {
                                    @Override
                                    public void onStart() {}
                                    @Override
                                    public void onStop() {}
                                    @Override
                                    public void onDestroy() {}
                                    @Override
                                    public void onLoadStarted(@Nullable Drawable placeholder) {}
                                    @Override
                                    public void onLoadFailed(@Nullable Drawable errorDrawable) {}
                                    @Override
                                    public void onResourceReady(@NonNull File resource, @Nullable Transition<? super File> transition) {
                                        saveGif(resource);
                                    }
                                    @Override
                                    public void onLoadCleared(@Nullable Drawable placeholder) {}
                                    @Override
                                    public void getSize(@NonNull SizeReadyCallback cb) {}
                                    @Override
                                    public void removeCallback(@NonNull SizeReadyCallback cb) {}
                                    @Override
                                    public void setRequest(@Nullable Request request) {}
                                    @Nullable
                                    @Override
                                    public Request getRequest() {
                                        return null;
                                    }
                                });*/
                        // File gifFile = new File(fileTarget);
                        //Glide.with(this).asGif().load(selectedImageUri).into(fileTarget);
                    }
                }
            });

    private void saveGif(Uri uri) {
        File newGifFile = new File(gifdirectory, gifName);
        String filePath;

        //File file = new File(uri.getPath());//create path from uri
        //final String[] split = file.getPath().split(":");//split the path.
        //filePath = split[1];//assign it to a string(your choice).
        File selectedGifFile = new File(getImagePath(uri));
        if (newGifFile == null) {
            return;
        }
        try {
            FileOutputStream output = new FileOutputStream(newGifFile);
            FileInputStream input = new FileInputStream(selectedGifFile);

            FileChannel inputChannel = input.getChannel();
            FileChannel outputChannel = output.getChannel();

            inputChannel.transferTo(0, inputChannel.size(), outputChannel);
            output.close();
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private  String getImagePath(Uri contentURI){
        String result;
        Cursor cursor = this.getContext().getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
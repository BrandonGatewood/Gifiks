package com.example.gifiks;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.example.gifiks.databinding.FragmentGalleryBinding;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Gallery extends Fragment {
    private FragmentGalleryBinding binding;
    private ArrayList<String> imagePaths;
    private RecyclerView imagesRV;
    private RecyclerViewAdapter imageRVAdapter;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //imagePaths = new ArrayList<>();
        imagesRV = (RecyclerView) getView().findViewById(R.id.idRVImages);
/*        final AssetManager assetManager = Objects.requireNonNull(getActivity()).getAssets();
        try {
            String[] list =  assetManager.list("Gifs/Jaafar");
            for (int i = 0; i < list.length; i++){
                list[i] = "file:///android_asset/Gifs/Jaafar/" + list[i];
            }
            List<String> newlist = Arrays.asList(list);
            imagePath = new ArrayList<>(newlist);
            imagesRV = (RecyclerView) getView().findViewById(R.id.idRVImages);
            prepareRecyclerView();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/
        try {
            getGifs("Jaafar");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        prepareRecyclerView();

    }

    private void getGifs(String username) throws IOException {
        int i = 0;
        String user = "Gifs/" + username;
        File directory = Objects.requireNonNull(this.getContext()).getDataDir();
        File gifs = new File(directory, user);
        File[] listOfGifs = gifs.listFiles();
        String[] list = new String[listOfGifs.length];
        for (i = 0; i < listOfGifs.length; i++){
            list[i] = gifs.toString() + "/" +listOfGifs[i].getName();
        }
        List<String> newlist = Arrays.asList(list);
        imagePaths = new ArrayList<>(newlist);
    }

    private void prepareRecyclerView() {
        imageRVAdapter = new RecyclerViewAdapter(this.getContext(), imagePaths);

        GridLayoutManager manager = new GridLayoutManager(this.getContext(), 4);

        imagesRV.setLayoutManager(manager);
        imagesRV.setAdapter(imageRVAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
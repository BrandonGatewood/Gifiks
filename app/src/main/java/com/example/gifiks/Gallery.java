package com.example.gifiks;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetManager;
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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.example.gifiks.databinding.FragmentGalleryBinding;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Gallery extends Fragment {
    private FragmentGalleryBinding binding;
    private ArrayList<String> imagePath;
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
        final AssetManager assetManager = Objects.requireNonNull(getActivity()).getAssets();
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
        }
    }

    private void prepareRecyclerView() {
        imageRVAdapter = new RecyclerViewAdapter(this.getContext(), imagePath);

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
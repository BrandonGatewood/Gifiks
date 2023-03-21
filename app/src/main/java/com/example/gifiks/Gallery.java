package com.example.gifiks;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gifiks.databinding.FragmentGalleryBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Gallery extends Fragment {
    private FragmentGalleryBinding binding;
    private ArrayList<String> imagePaths;
    private RecyclerView imagesRV;
    private RecyclerViewAdapter imageRVAdapter;

    BottomNavigationView bottomNavigationView;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        setHasOptionsMenu(true);
        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imagesRV = (RecyclerView) getView().findViewById(R.id.idRVImages);

        Bundle bundle = this.getArguments();
        Account receivedAccount = Objects.requireNonNull(bundle).getParcelable("AccountInfo");
        bottomNavigationView = (BottomNavigationView) getView().findViewById(R.id.bottomNavigationView);
        // Use bottom nav bar to move to upload gif page
        binding.bottomNavigationView.findViewById(R.id.uploadGifIcon).setOnClickListener(view2 -> NavHostFragment.findNavController(this)
                .navigate(R.id.action_to_UploadGifFragment, bundle));
        // Use bottom nav bar to move to Profile page
        binding.bottomNavigationView.findViewById(R.id.profilePageIcon).setOnClickListener(view1 -> NavHostFragment.findNavController(this)
                .navigate(R.id.action_to_ProfilePageFragment, bundle));

        // Use bottom nav bar to move to Home Page
        binding.bottomNavigationView.findViewById(R.id.homePageIcon).setOnClickListener(view1 -> NavHostFragment.findNavController(this)
                .navigate(R.id.action_to_HomePageFragment, bundle));

        try {
            getGifs(receivedAccount.getUsername());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        prepareRecyclerView();

    }

    public boolean getGifs(String username) throws IOException {
        if(username == null){
            return false;
        }
        int i = 0;
        String user = "Gifs/" + username;
        File directory = Objects.requireNonNull(this.getContext()).getDataDir();
        File gifs = new File(directory, user);
        File[] listOfGifs = gifs.listFiles();
        if(listOfGifs != null) {
            String[] list = new String[listOfGifs.length];
            for (i = 0; i < listOfGifs.length; i++) {
                list[i] = gifs.toString() + "/" + listOfGifs[i].getName();
            }
            List<String> newlist = Arrays.asList(list);
            imagePaths = new ArrayList<>(newlist);
        }
        return true;
    }

    private void prepareRecyclerView() {
        if(imagePaths != null) {
            imageRVAdapter = new RecyclerViewAdapter(this.getContext(), imagePaths, false);

            GridLayoutManager manager = new GridLayoutManager(this.getContext(), 4);

            imagesRV.setLayoutManager(manager);
            imagesRV.setAdapter(imageRVAdapter);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
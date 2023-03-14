package com.example.gifiks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gifiks.databinding.ActivityHomePageBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomePage extends Fragment {
    private ActivityHomePageBinding binding;
    BottomNavigationView bottomNavigationView;
    private ArrayList<String> imagePaths;
    private RecyclerView imagesRV;
    private RecyclerViewAdapter imageRVAdapter;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = ActivityHomePageBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imagesRV = (RecyclerView) getView().findViewById(R.id.RVImagesHP);

        // bundle contains Account object
        Bundle bundle = this.getArguments();
        Account receivedAccount = Objects.requireNonNull(bundle).getParcelable("AccountInfo");
        bottomNavigationView = view.findViewById(R.id.bottomNavigationView);

        // Use bottom nav bar to move to upload gif page
        binding.bottomNavigationView.findViewById(R.id.profilePageIcon).setOnClickListener(view1 -> NavHostFragment.findNavController(HomePage.this)
                .navigate(R.id.action_to_ProfilePageFragment, bundle));

        // Use bottom nav bar to move to upload gif page
        binding.bottomNavigationView.findViewById(R.id.uploadGifIcon).setOnClickListener(view2 -> NavHostFragment.findNavController(HomePage.this)
                .navigate(R.id.action_to_UploadGifFragment, bundle));

        try {
            getGifs();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        prepareRecyclerView();

    }

    private void getGifs() throws IOException {
        int i = 0, j = 0;
        List<String> newlist = new ArrayList<>();

        File directory = Objects.requireNonNull(this.getContext()).getDataDir();
        File users = new File(directory, "Gifs/");
        File[] listOfUsers = users.listFiles();

        if(listOfUsers != null) {
            for (j = 0; j < listOfUsers.length; j++) {
                String user = listOfUsers[j].toString();
                File gifs = new File(user);
                File[] listOfGifs = gifs.listFiles();

                if (listOfGifs != null) {
                    String[] list = new String[listOfGifs.length];
                    for (i = 0; i < listOfGifs.length; i++) {
                        list[i] = gifs.toString() + "/" + listOfGifs[i].getName();
                        newlist.add(list[i]);
                    }
                }
            }
            imagePaths = new ArrayList<>(newlist);
        }
    }

    private void prepareRecyclerView() {
        if(imagePaths != null) {
            imageRVAdapter = new RecyclerViewAdapter(this.getContext(), imagePaths, true);

            GridLayoutManager manager = new GridLayoutManager(this.getContext(), 1);

            imagesRV.setLayoutManager(manager);
            imagesRV.setAdapter(imageRVAdapter);
        }
    }

    // Both functions, onResume() and onStop() will remove back button from action bar.
    @Override
    public void onResume() {
        super.onResume();
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()). setDisplayHomeAsUpEnabled(false);
    }

    @Override
    public void onStop() {
        super.onStop();
        Objects.requireNonNull(((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar()). setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
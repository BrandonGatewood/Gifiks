package com.example.gifiks;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.gifiks.databinding.FragmentGifDetailActivityBinding;

import java.io.File;

public class GifDetailActivity extends Fragment {
    private FragmentGifDetailActivityBinding binding;
    String imgPath;
    private ImageView imageView;
    private ScaleGestureDetector scaleGestureDetector;

    private float mScaleFactor = 1.0f;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentGifDetailActivityBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imgPath = getActivity().getIntent().getStringExtra("imgPath");
        imageView = (ImageView) getView().findViewById(R.id.idIVImage);

        scaleGestureDetector = new ScaleGestureDetector(this.getContext(), new ScaleListener());

        File imgFile = new File(imgPath);
        if (imgFile.exists()) {
            //Picasso.get().load(imgFile).placeholder(R.drawable.ic_launcher_background).into(imageView);
            Glide.with(this).load(imgFile).placeholder(R.drawable.ic_launcher_background).into(imageView);
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        // inside on touch event method we are calling on
        // touch event method and passing our motion event to it.
        scaleGestureDetector.onTouchEvent(motionEvent);
        return true;
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        // on below line we are creating a class for our scale
        // listener and  extending it with gesture listener.
        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {

            // inside on scale method we are setting scale
            // for our image in our image view.
            mScaleFactor *= scaleGestureDetector.getScaleFactor();
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 10.0f));

            // on below line we are setting
            // scale x and scale y to our image view.
            imageView.setScaleX(mScaleFactor);
            imageView.setScaleY(mScaleFactor);
            return true;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
package com.example.gifiks;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {
    private final Context context;
    private final ArrayList<String> imagePathArrayList;

    private boolean homepage;

    public RecyclerViewAdapter(Context context, ArrayList<String> imagePathArrayList, boolean onHomePage) {
        this.context = context;
        this.imagePathArrayList = imagePathArrayList;
        this.homepage = onHomePage;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View cardView;
        if(homepage) {
            cardView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_card_homepage, parent, false);
        } else {
            cardView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_card, parent, false);
        }
        return new RecyclerViewHolder(cardView, homepage);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        String path = imagePathArrayList.get(position);
        String[] pathArr = path.split("/");
        int parts = pathArr.length;
        String gif = pathArr[parts-1];
        String[] name = gif.split("[.]");
        String user = pathArr[parts-2];
        if(homepage){
            holder.gifName.setText(name[0]);
            holder.userName.setText(user);
        }

        Glide.with(holder.imageIV).load(path).into(holder.imageIV);
        holder.itemView.setOnClickListener(v -> {
            Intent i = new Intent(context, GifDetailActivity.class);
            i.putExtra("imgPath", path);
            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return imagePathArrayList.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageIV;
        private TextView gifName;
        private TextView userName;

        public RecyclerViewHolder(@NonNull View itemView, boolean homepage) {
            super(itemView);
            imageIV = itemView.findViewById(R.id.idIVImage);
            if(homepage){
                gifName = itemView.findViewById(R.id.GifName);
                userName = itemView.findViewById(R.id.Byuser);
                ImageButton shareBtn = itemView.findViewById(R.id.shareBtn);
                ImageButton likeBtn = itemView.findViewById(R.id.likeBtn);
                likeBtn.setSelected(false);

                likeBtn.setOnClickListener(v -> {
                    likeBtn.setSelected(!likeBtn.isSelected());

                });

                shareBtn.setOnClickListener(v -> {
                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);

                    // type of the content to be shared
                    sharingIntent.setType("text/plain");




                    itemView.getContext().startActivity(Intent.createChooser(sharingIntent, "Share using"));
                });

            }
        }
    }
}

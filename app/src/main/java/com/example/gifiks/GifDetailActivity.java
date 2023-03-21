package com.example.gifiks;

import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.gifiks.databinding.GifDetailActivityBinding;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class GifDetailActivity extends AppCompatActivity {
    private GifDetailActivityBinding binding;
    String imgPath;

    Button comment;
    private ImageView imageView;
    private TextView commentView;
    private TextView userNameView;
    private TextView gifNameView;

    private String userName;
    private String gifName;

    private ArrayList<String> allCommentsPaths;
    private ArrayList<String> allUserCommentsPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = GifDetailActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        imgPath = getIntent().getStringExtra("imgPath");
        imageView = findViewById(R.id.idIVImage);
        commentView = findViewById(R.id.Comments);
        userNameView = findViewById(R.id.ByAUser);
        gifNameView = findViewById(R.id.AGifName);
        comment = findViewById(R.id.comment);

        final EditText viewYourComment = findViewById(R.id.yourComment);

        getComments();

        Glide.with(this).load(imgPath).placeholder(R.drawable.ic_launcher_background).into(imageView);

        binding.comment.setOnClickListener(view1 -> {
            String newComment = viewYourComment.getText().toString();

            if(newComment.isEmpty()) {
                Toast.makeText(this, "Missing a Comment", Toast.LENGTH_LONG).show();
            }
            else {
                createNewComment(newComment);
            }
        });

    }

    private void createNewComment(String newComment) {
        int count = getCommentCount() + 1;
        String commentsPath = "comments/" + userName + "." + gifName + "." + count + ".txt";
        File directory = Objects.requireNonNull(this.getDataDir());
        File commentFile = new File(directory, commentsPath);

        try {
            OutputStream output = new FileOutputStream(commentFile);

            output.write(newComment.getBytes());
            output.flush();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setCommentCount(count);
        appendAComment(commentFile);
    }

    private void appendAComment(File commentFile) {
            String comment;
                try {
                    Scanner sc = new Scanner(commentFile);
                    comment = "Comment: " + sc.useDelimiter("\\A").next() + "\n";
                    sc.close();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            commentView.append(comment);
    }

    private void getComments() {
        loadNames();
        saveAllCommentsPaths();
        getUserComments();
        loadComments();
    }

    private void loadComments() {
        int i = 0;
        String[] allComments;
        String text;
        if(allUserCommentsPath != null) {
            allComments = new String[allUserCommentsPath.size()];
            for (i = 0; i < allUserCommentsPath.size(); i++) {
                File comment = new File(allUserCommentsPath.get(i));
                try {
                    Scanner sc = new Scanner(comment);
                    allComments[i] = "Comment: " + sc.useDelimiter("\\A").next();
                    sc.close();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
            text = new String();
            for (i = 0; i < allComments.length; i++) {
                text += allComments[i] + "\n";
            }
            commentView.setText(text);
        }
    }

    private void saveAllCommentsPaths(){
        int i = 0;
        List<String> newList = new ArrayList<>();

        File directory = Objects.requireNonNull(this.getDataDir());
        File comments = new File(directory, "comments/");
        File[] listOfComments = comments.listFiles();


        if(listOfComments != null) {
            String[] list = new String[listOfComments.length];
            for (i = 0; i < listOfComments.length; i++) {
                list[i] = comments.toString() + "/" + listOfComments[i].getName();
                newList.add(list[i]);
            }
            allCommentsPaths = new ArrayList<>(newList);
        }
    }
    private void getUserComments() {
        int i = 0;
        String tempGifName, tempUserName;
        if(allCommentsPaths != null) {
            for (i = 0; i < allCommentsPaths.size(); i++) {
                tempUserName = getUserName(allCommentsPaths.get(i));
                if(Objects.equals(tempUserName, userName)){
                    tempGifName = getGifName(allCommentsPaths.get(i));
                    if(Objects.equals(tempGifName, gifName)){
                        if(allUserCommentsPath != null) {
                            allUserCommentsPath.add(allCommentsPaths.get(i));
                        } else {
                            allUserCommentsPath = new ArrayList<>();
                            allUserCommentsPath.add(allCommentsPaths.get(i));
                        }
                    }
                }
            }
        }
    }

    private String getUserName(String path) {
        String[] pathArr = path.split("/");
        int parts = pathArr.length;
        String commentFile = pathArr[parts-1];
        String[] name = commentFile.split("[.]");

        return name[0];
    }

    private String getGifName(String path) {
        String[] pathArr = path.split("/");
        int parts = pathArr.length;
        String commentFile = pathArr[parts-1];
        String[] name = commentFile.split("[.]");

        return name[1];
    }

    private void loadNames(){
        String[] pathArr = imgPath.split("/");
        int parts = pathArr.length;
        String gif = pathArr[parts-1];
        String[] name = gif.split("[.]");
        String user = pathArr[parts-2];

        gifNameView.setText(name[0]);
        gifName = name[0];
        userNameView.setText(user);
        userName = user;
    }

    private int getCommentCount() {
        int count = 0;
        String comments = "commentCount.txt";
        File directory = Objects.requireNonNull(this.getDataDir());
        File commentsCount = new File(directory, comments);
        try {
            Scanner sc = new Scanner(commentsCount);
            count = sc.nextInt();
            sc.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return count;
    }

    private void setCommentCount(int count) {
        String comments = "commentCount.txt";
        File directory = Objects.requireNonNull(this.getDataDir());
        File commentsCount = new File(directory, comments);
        try {
            FileOutputStream output = new FileOutputStream(commentsCount, false);

            output.write(String.valueOf(count).getBytes());
            output.flush();
            output.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

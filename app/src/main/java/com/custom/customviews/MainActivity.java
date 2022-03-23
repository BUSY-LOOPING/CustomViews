package com.custom.customviews;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.busy.looping.like_view_library.LikeView;
import com.custom.customviews.view.CustomView;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LikeView likeView = findViewById(R.id.like);


    }
}
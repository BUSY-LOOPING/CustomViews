package com.custom.customviews;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.custom.customviews.view.CustomView;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
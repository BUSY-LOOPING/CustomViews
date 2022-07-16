package com.custom.customviews;

import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.VirtualLayoutAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.DefaultLayoutHelper;
import com.alibaba.android.vlayout.layout.FixLayoutHelper;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.ScrollFixLayoutHelper;
import com.arasthel.spannedgridlayoutmanager.SpannedGridLayoutManager;
import com.busy.looping.strike_through_view.StrikedView;
import com.busy.looping.strike_through_view.ProfileCardPainter;
import com.custom.customviews.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        RecyclerView recyclerView = binding.recycler;
        ArrayList<Integer> list = new ArrayList<>();

//        SpannedGridLayoutManager layoutManager = new SpannedGridLayoutManager(new SpannedGridLayoutManager.GridSpanLookup() {
//            @Override
//            public SpannedGridLayoutManager.SpanInfo getSpanInfo(int position) {
////                 Conditions for 2x2 items
//                if ( position % 6 == 4) {
//                    return new SpannedGridLayoutManager.SpanInfo(2, 2);
//                } else {
//                    return new SpannedGridLayoutManager.SpanInfo(1, 1);
//                }
////                if (position == 0) {
////                    return new SpannedGridLayoutManager.SpanInfo(2, 2);
//////this will count of row and couloumn you want to replace
////                } else {
////                    return new SpannedGridLayoutManager.SpanInfo(1, 1);
////                }
//            }
//        }, 3, 1f);


//        SpannedGridLayoutManager spannedGridLayoutManager = new SpannedGridLayoutManager(SpannedGridLayoutManager.Orientation.VERTICAL, 4);
//        recyclerView.setLayoutManager(spannedGridLayoutManager);
        final VirtualLayoutManager layoutManager = new VirtualLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        final List<LayoutHelper> helpers = new LinkedList<>();

        final GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(4);
        gridLayoutHelper.setItemCount(25);

        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        recyclerView.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 10);


        final ScrollFixLayoutHelper scrollFixLayoutHelper = new ScrollFixLayoutHelper(FixLayoutHelper.TOP_RIGHT, 100, 100);

//        helpers.add(DefaultLayoutHelper.newHelper(7));
//        helpers.add(scrollFixLayoutHelper);
//        helpers.add(DefaultLayoutHelper.newHelper(2));
        helpers.add(gridLayoutHelper);

        layoutManager.setLayoutHelpers(helpers);

        for (int i = 0; i < 100; i++) {
            list.add(R.color.blue);
            list.add(R.color.purple_500);
            list.add(android.R.color.holo_green_dark);
            list.add(android.R.color.holo_orange_light);
            list.add(android.R.color.holo_red_dark);
            list.add(android.R.color.holo_red_light);
            list.add(android.R.color.darker_gray);
        }

        MyAdapter adapter = new MyAdapter(list, this, layoutManager);
        recyclerView.setAdapter(adapter);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                recyclerView.scrollToPosition(7);
                adapter.notifyDataSetChanged();
                if (binding != null)
                    binding.setIsLiked(Boolean.TRUE);
            }
        }, 2500);

//        final ViewTreeObserver observer = binding.profileCardContainer.getViewTreeObserver();
//        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                int avatarRadius = 100;
//                int avatarMargin = 0;
//                Log.d("mylog", "height: " + avatarRadius);
//
//                ProfileCardPainter profileCardPainter = new ProfileCardPainter(
//                        MainActivity.this,
//                        ContextCompat.getColor(MainActivity.this, R.color.design_default_color_primary),
//                        avatarRadius,
//                        avatarMargin);
//
//                binding.profileCardContainer.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.teal_200));
//
////                StrikedView strikedView = new StrikedView(
////                        MainActivity.this,
////                        binding.profileCardContainer.getWidth(),
////                        binding.profileCardContainer.getHeight(),
////                        profileCardPainter
////                );
////                binding.profileCardContainer.addView(
////                        strikedView
////                );
////                binding.profileCardContainer.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//            }
//        });

    }
}
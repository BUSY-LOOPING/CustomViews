package com.custom.customviews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.VirtualLayoutAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends VirtualLayoutAdapter<MyAdapter.MyAdapterViewHolder> {
    private ArrayList<Integer> list;
    private Context context;

    public MyAdapter(ArrayList<Integer> list, Context context, VirtualLayoutManager virtualLayoutManager) {
        super(virtualLayoutManager);
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false);
        return new MyAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterViewHolder holder, int position) {
        VirtualLayoutManager.LayoutParams layoutParams = new VirtualLayoutManager.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, 300);
        holder.itemView.setLayoutParams(layoutParams);

        if (position == 7) {
            layoutParams.height = 60;
            layoutParams.width = 60;
        } else if (position > 35) {
            layoutParams.height = 200 + (position - 30) * 100;
        }

        holder.itemView.setBackground(ContextCompat.getDrawable(context, list.get(position)));
    }

    @Override
    public int getItemCount() {
        List<LayoutHelper> helpers = getLayoutHelpers();
        if (helpers == null) {
            return 0;
        }
        int count = 0;
        for (int i = 0, size = helpers.size(); i < size; i++) {
            count += helpers.get(i).getItemCount();
        }
        return count;
    }

    public static class  MyAdapterViewHolder extends RecyclerView.ViewHolder {

        public MyAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

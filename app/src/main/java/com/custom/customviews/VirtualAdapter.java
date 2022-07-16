package com.custom.customviews;

import android.content.Context;

import androidx.annotation.NonNull;

import com.alibaba.android.vlayout.VirtualLayoutManager;

public class VirtualAdapter extends VirtualLayoutManager {
    public VirtualAdapter(@NonNull Context context) {
        super(context);
    }

    public VirtualAdapter(@NonNull Context context, int orientation) {
        super(context, orientation);
    }

    public VirtualAdapter(@NonNull Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }
}

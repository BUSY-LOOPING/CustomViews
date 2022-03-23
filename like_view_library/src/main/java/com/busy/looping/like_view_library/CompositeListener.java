package com.busy.looping.like_view_library;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Aux class to collect multiple click listeners.
 */
class CompositeListener implements View.OnClickListener {
    private final List<View.OnClickListener> registeredListeners = new ArrayList<View.OnClickListener>();

    public void registerListener (View.OnClickListener listener) {
        registeredListeners.add(listener);
    }

    @Override
    public void onClick(View v) {
        for(View.OnClickListener listener:registeredListeners) {
            listener.onClick(v);
        }
    }
}

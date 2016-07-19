
package com.github.shoji_kuroda.impression_view;

import android.support.v7.widget.RecyclerView;

/**
 * Created by kuroda02 on 2016/07/15.
 */
abstract class RecyclerViewScrollDetector extends RecyclerView.OnScrollListener {

    private static final int SCROLL_THRESHOLD = 4;

    abstract void onScrollUp();

    abstract void onScrollDown();

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        boolean isSignificantDelta = Math.abs(dy) > SCROLL_THRESHOLD;
        if (isSignificantDelta) {
            if (dy > 0) {
                onScrollUp();
            } else {
                onScrollDown();
            }
        }
    }
}
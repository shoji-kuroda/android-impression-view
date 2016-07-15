package com.github.shoji_kuroda.impression_view;

import android.widget.ScrollView;

/**
 * Created by kuroda02 on 2016/07/15.
 */
abstract class ScrollViewScrollDetector implements ObservableScrollView.OnScrollChangedListener {
    private static final int SCROLL_THRESHOLD = 4;
    private int lastScrollY;

    abstract void onScrollUp();

    abstract void onScrollDown();

    @Override
    public void onScrollChanged(ScrollView view, int l, int t, int oldl, int oldt) {
        if (Math.abs(t - this.lastScrollY) > SCROLL_THRESHOLD) {
            if (t > this.lastScrollY) {
                onScrollUp();
            } else {
                onScrollDown();
            }
        }
        this.lastScrollY = t;
    }
}

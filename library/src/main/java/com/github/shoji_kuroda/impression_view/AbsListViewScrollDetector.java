package com.github.shoji_kuroda.impression_view;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AbsListView;

/**
 * Created by kuroda02 on 2016/07/15.
 */
abstract class AbsListViewScrollDetector implements AbsListView.OnScrollListener {

    private static final int SCROLL_THRESHOLD = 4;
    private int lastScrollY;
    private int previousFirstVisibleItem;
    private AbsListView listView;

    abstract void onScrollUp();

    abstract void onScrollDown();

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (totalItemCount == 0) {
            return;
        }
        if (isSameRow(firstVisibleItem)) {
            int newScrollY = getTopItemScrollY();
            boolean isSignificantDelta = Math.abs(lastScrollY - newScrollY) > SCROLL_THRESHOLD;
            if (isSignificantDelta) {
                if (lastScrollY > newScrollY) {
                    onScrollUp();
                } else {
                    onScrollDown();
                }
            }
            lastScrollY = newScrollY;
        } else {
            if (firstVisibleItem > previousFirstVisibleItem) {
                onScrollUp();
            } else {
                onScrollDown();
            }

            lastScrollY = getTopItemScrollY();
            previousFirstVisibleItem = firstVisibleItem;
        }
    }

    public void setListView(@NonNull AbsListView listView) {
        this.listView = listView;
    }

    private boolean isSameRow(int firstVisibleItem) {
        return firstVisibleItem == previousFirstVisibleItem;
    }

    private int getTopItemScrollY() {
        if (listView == null || listView.getChildAt(0) == null) return 0;
        View topChild = listView.getChildAt(0);
        return topChild.getTop();
    }
}
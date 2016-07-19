package com.github.shoji_kuroda.impression_view;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ScrollView;

/**
 * Created by kuroda02 on 2016/07/15.
 */
abstract public class ImpressionView extends FrameLayout implements ImpressionDetector {

    private boolean isVisible;

    public ImpressionView(Context context) {
        super(context);
    }

    public ImpressionView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImpressionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void attachScrollView(ObservableScrollView scrollView) {
        attachScrollView(scrollView, null, null);
    }

    public void attachListView(ListView listView, int position) {
        attachListView(listView, null, null, position);
    }

    public void attachRecyclerView(RecyclerView recyclerView) {
        attachRecyclerView(recyclerView, null, null);
    }


    private void attachScrollView(ObservableScrollView scrollView,
                                  ScrollDirectionListener scrollDirectionListener,
                                  ObservableScrollView.OnScrollChangedListener onScrollChangedListener) {
        ScrollViewScrollDetectorImpl scrollDetector = new ScrollViewScrollDetectorImpl();
        scrollDetector.setScrollDirectionListener(scrollDirectionListener);
        scrollDetector.setOnScrollChangedListener(onScrollChangedListener);
        scrollView.setOnScrollChangedListener(scrollDetector);

        this.isVisible = scrollDetector.getVisible();
    }


    private void attachListView(ListView listView,
                                ScrollDirectionListener scrollDirectionListener,
                                AbsListView.OnScrollListener onScrollListener,
                                int position) {
        AbsListViewScrollDetectorImpl scrollDetector = new AbsListViewScrollDetectorImpl();
        scrollDetector.setScrollDirectionListener(scrollDirectionListener);
        scrollDetector.setOnScrollListener(onScrollListener);
        scrollDetector.setListView(listView);
        scrollDetector.setPosition(position);
        listView.setOnScrollListener(scrollDetector);
    }

    private void attachRecyclerView(RecyclerView recyclerView,
                                    ScrollDirectionListener scrollDirectionListener,
                                    RecyclerView.OnScrollListener onScrollListener) {
        RecyclerViewScrollDetectorImpl scrollDetector = new RecyclerViewScrollDetectorImpl();
        scrollDetector.setScrollDirectionListener(scrollDirectionListener);
        scrollDetector.setOnScrollListener(onScrollListener);
        scrollDetector.setRecyclerView(recyclerView);
        recyclerView.addOnScrollListener(scrollDetector);
    }


    private class ScrollViewScrollDetectorImpl extends ScrollViewScrollDetector {

        private final Rect scrollBounds;

        public ScrollViewScrollDetectorImpl() {
            this.scrollBounds = new Rect();
            getHitRect(this.scrollBounds);
        }

        private ScrollDirectionListener mScrollDirectionListener;

        private ObservableScrollView.OnScrollChangedListener mOnScrollChangedListener;

        private void setScrollDirectionListener(ScrollDirectionListener scrollDirectionListener) {
            mScrollDirectionListener = scrollDirectionListener;
        }

        public void setOnScrollChangedListener(ObservableScrollView.OnScrollChangedListener onScrollChangedListener) {
            mOnScrollChangedListener = onScrollChangedListener;
        }

        @Override
        public void onScrollUp() {
            checkViewVisibleChanged();
            if (mScrollDirectionListener != null) {
                mScrollDirectionListener.onScrollUp();
            }
        }

        @Override
        public void onScrollDown() {
            checkViewVisibleChanged();
            if (mScrollDirectionListener != null) {
                mScrollDirectionListener.onScrollDown();
            }
        }

        private void checkViewVisibleChanged() {
            boolean currentVisible = getVisible();
            if (!isVisible && currentVisible) {
                onShow();
            } else if (isVisible && !currentVisible) {
                onHide();
            }
            isVisible = currentVisible;
        }

        @Override
        public void onScrollChanged(ScrollView who, int l, int t, int oldl, int oldt) {
            if (mOnScrollChangedListener != null) {
                mOnScrollChangedListener.onScrollChanged(who, l, t, oldl, oldt);
            }
            super.onScrollChanged(who, l, t, oldl, oldt);
        }

        public boolean getVisible() {
            return getLocalVisibleRect(scrollBounds) && getVisibility() == View.VISIBLE;
        }
    }

    private class AbsListViewScrollDetectorImpl extends AbsListViewScrollDetector {
        private ScrollDirectionListener mScrollDirectionListener;
        private AbsListView.OnScrollListener mOnScrollListener;
        private int targetPosition;

        private void setScrollDirectionListener(ScrollDirectionListener scrollDirectionListener) {
            mScrollDirectionListener = scrollDirectionListener;
        }

        public void setOnScrollListener(AbsListView.OnScrollListener onScrollListener) {
            mOnScrollListener = onScrollListener;
        }

        @Override
        public void onScrollDown() {
            if (mScrollDirectionListener != null) {
                mScrollDirectionListener.onScrollDown();
            }
        }

        @Override
        public void onScrollUp() {
            if (mScrollDirectionListener != null) {
                mScrollDirectionListener.onScrollUp();
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                             int totalItemCount) {

            if (targetPosition >= firstVisibleItem && targetPosition < firstVisibleItem + visibleItemCount) {
                if (!isVisible) {
                    onShow();
                    isVisible = true;
                }
            } else {
                if (isVisible) {
                    onHide();
                    isVisible = false;
                }
            }

            if (mOnScrollListener != null) {
                mOnScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
            }

            super.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (mOnScrollListener != null) {
                mOnScrollListener.onScrollStateChanged(view, scrollState);
            }

            super.onScrollStateChanged(view, scrollState);
        }

        public void setPosition(int position) {
            this.targetPosition = position;
        }
    }


    private class RecyclerViewScrollDetectorImpl extends RecyclerViewScrollDetector {
        private ScrollDirectionListener mScrollDirectionListener;
        private RecyclerView.OnScrollListener mOnScrollListener;
        private RecyclerView recyclerView;

        private void setScrollDirectionListener(ScrollDirectionListener scrollDirectionListener) {
            mScrollDirectionListener = scrollDirectionListener;
        }

        public void setOnScrollListener(RecyclerView.OnScrollListener onScrollListener) {
            mOnScrollListener = onScrollListener;
        }

        @Override
        public void onScrollDown() {
            checkViewVisibleChanged();
            if (mScrollDirectionListener != null) {
                mScrollDirectionListener.onScrollDown();
            }
        }

        @Override
        public void onScrollUp() {
            checkViewVisibleChanged();
            if (mScrollDirectionListener != null) {
                mScrollDirectionListener.onScrollUp();
            }
        }

        private void checkViewVisibleChanged() {
            if (!isVisible && getViewVisible()) {
                onShow();
                isVisible = true;
            } else if (isVisible && !getViewVisible()) {
                onHide();
                isVisible = false;
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            if (mOnScrollListener != null) {
                mOnScrollListener.onScrolled(recyclerView, dx, dy);
            }

            super.onScrolled(recyclerView, dx, dy);
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            if (mOnScrollListener != null) {
                mOnScrollListener.onScrollStateChanged(recyclerView, newState);
            }

            super.onScrollStateChanged(recyclerView, newState);
        }

        public void setRecyclerView(RecyclerView recyclerView) {
            this.recyclerView = recyclerView;
        }

        public boolean getViewVisible() {
            LinearLayoutManager layoutManager = ((LinearLayoutManager) recyclerView.getLayoutManager());
            int firstVisiblePosition = layoutManager.findFirstVisibleItemPosition();
            int lastVisiblePosition = layoutManager.findLastVisibleItemPosition();

            for (int position = firstVisiblePosition; position <= lastVisiblePosition; position++) {
                View v = recyclerView.getLayoutManager().findViewByPosition(position);
                if (v == ImpressionView.this) {
                    return true;
                }
            }
            return false;
        }
    }

}

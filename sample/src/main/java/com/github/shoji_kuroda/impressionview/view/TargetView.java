package com.github.shoji_kuroda.impressionview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Toast;

import com.github.shoji_kuroda.impression_view.ImpressionView;


/**
 * Created by kuroda02 on 2016/07/15.
 */
public class TargetView extends ImpressionView {


    public TargetView(Context context) {
        this(context, null);
    }

    public TargetView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TargetView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    public void onShow() {
        Toast.makeText(getContext(), "show", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onHide() {
        Toast.makeText(getContext(), "hide", Toast.LENGTH_SHORT).show();
    }
}

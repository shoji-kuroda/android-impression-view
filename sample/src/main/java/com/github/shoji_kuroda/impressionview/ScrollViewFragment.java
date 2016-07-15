package com.github.shoji_kuroda.impressionview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.shoji_kuroda.impression_view.ObservableScrollView;
import com.github.shoji_kuroda.impressionview.view.TargetView;

/**
 * Created by kuroda02 on 2016/07/15.
 */
public class ScrollViewFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scrollview, container, false);

        ObservableScrollView scrollView = (ObservableScrollView) view.findViewById(R.id.scroll_view);
        TargetView impView = (TargetView) view.findViewById(R.id.target);

        impView.attachScrollView(scrollView);

        return view;
    }
}

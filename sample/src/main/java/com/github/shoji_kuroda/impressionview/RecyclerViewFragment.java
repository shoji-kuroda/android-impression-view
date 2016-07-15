package com.github.shoji_kuroda.impressionview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.shoji_kuroda.impression_view.ImpressionView;

/**
 * Created by kuroda02 on 2016/07/15.
 */
public class RecyclerViewFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recyclerview, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getContext()));

        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getActivity(), getResources()
                .getStringArray(R.array.currencies), recyclerView);
        recyclerView.setAdapter(adapter);


        return view;
    }


    private class SimpleDividerItemDecoration extends RecyclerView.ItemDecoration {
        private Drawable mDivider;

        public SimpleDividerItemDecoration(Context context) {
            mDivider = context.getResources().getDrawable(R.drawable.simple_line_divider);
        }

        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();

            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = parent.getChildAt(i);

                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

                int top = child.getBottom() + params.bottomMargin;
                int bottom = top + mDivider.getIntrinsicHeight();

                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }
    }


    private class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {


        private static final int VIEW_TYPE_NORMAL = 0;
        private static final int VIEW_TYPE_TARGET = 1;
        private final Context context;
        private final String[] dataset;
        private final LayoutInflater inflater;
        private RecyclerView recyclerView;

        public RecyclerViewAdapter(Context context, String[] dataset, RecyclerView recyclerView) {
            this.context = context;
            this.dataset = dataset;
            this.recyclerView = recyclerView;
            this.inflater = LayoutInflater.from(context);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view;
            if (viewType == VIEW_TYPE_NORMAL) {
                view = inflater.inflate(R.layout.normal_list_item, parent, false);
            } else {
                view = inflater.inflate(R.layout.view_target, parent, false);
                ((ImpressionView) view).attachRecyclerView(recyclerView);
            }
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, int position) {
            viewHolder.textView.setText(dataset[position]);
        }

        @Override
        public int getItemCount() {
            return dataset.length;
        }

        @Override
        public int getItemViewType(int position) {
            if (dataset[position].equals("JPY")) {
                return VIEW_TYPE_TARGET;
            }
            return VIEW_TYPE_NORMAL;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView textView;

            public ViewHolder(View v) {
                super(v);
                textView = (TextView) v.findViewById(R.id.text);
            }
        }
    }
}

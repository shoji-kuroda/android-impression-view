package com.github.shoji_kuroda.impressionview;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.github.shoji_kuroda.impressionview.view.TargetView;

/**
 * Created by kuroda02 on 2016/07/15.
 */
public class ListViewFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_listview, container, false);

        ListView list = (ListView) view.findViewById(R.id.list_view);
        ListViewAdapter listAdapter = new ListViewAdapter(getActivity(),
                getResources().getStringArray(R.array.currencies), list);
        list.setAdapter(listAdapter);

        return view;
    }


    private class ListViewAdapter extends ArrayAdapter {

        private final LayoutInflater inflater;
        private String[] dataset;
        private ListView listView;

        public ListViewAdapter(FragmentActivity activity, String[] dataset, ListView listView) {
            super(activity, 0, dataset);
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.dataset = dataset;
            this.listView = listView;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if (dataset[position].equals("JPY")) {
                view = inflater.inflate(R.layout.view_target, null);
                ((TargetView) view).attachListView(listView, position);
            } else {
                view = inflater.inflate(R.layout.normal_list_item, null);
            }
            TextView textView = (TextView) view.findViewById(R.id.text);
            textView.setText(dataset[position]);
            return view;
        }
    }

}

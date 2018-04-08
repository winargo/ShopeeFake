package com.fake.shopee.shopeefake.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fake.shopee.shopeefake.R;

/**
 * Created by riandy on 1/1/2018.
 */

public class fragment_batal extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.row_belanjaanku, container, false);
        TextView a = (TextView) rootView.findViewById(R.id.text);
        a.setText("Batal");
        return rootView;
    }

}

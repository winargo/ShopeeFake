package com.fake.shopee.shopeefake.recyclerviews;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class recycler_sent extends RecyclerView.Adapter<recycler_sent.MyViewHolder>{
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(View view) {
            super(view);
        }
    }
}

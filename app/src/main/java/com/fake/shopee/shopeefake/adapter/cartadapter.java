package com.fake.shopee.shopeefake.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fake.shopee.shopeefake.cartview.cart;

import java.util.List;

/**
 * Created by Riandy on 5/22/2018.
 */

public class cartadapter extends RecyclerView.Adapter<cartadapter.MyViewHolder>
{
    List<cart> cartlist ;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre;

        public MyViewHolder(View view) {
            super(view);
        }
    }

    @Override
    public cartadapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(cartadapter.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}

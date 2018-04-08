package com.fake.shopee.shopeefake.ShopeePay;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fake.shopee.shopeefake.R;

/**
 * Created by ifanzal on 1/7/18.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecycleViewHolder> {

    private final Context context;

    String [] tgl={"29-12-2017","28-12-2017","26-12-2017","24-12-2017"};

    String [] nominal = {"-Rp.264.500","-Rp226.000","-Rp140.050","-Rp183.000"};

    LayoutInflater inflater;

    public RecyclerAdapter (Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public RecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.layout_riwayat_transaksi, parent, false);

        RecycleViewHolder viewHolder = new RecycleViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecycleViewHolder holder, int position) {
        holder.tglPenarikan.setText(tgl[position]);
        holder.nominalPenarikan.setText(nominal[position]);
    }

    @Override
    public int getItemCount() {
        return tgl.length;
    }
}

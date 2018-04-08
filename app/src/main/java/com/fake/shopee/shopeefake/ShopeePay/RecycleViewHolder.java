package com.fake.shopee.shopeefake.ShopeePay;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fake.shopee.shopeefake.R;

/**
 * Created by ifanzal on 1/7/18.
 */

public class RecycleViewHolder extends RecyclerView.ViewHolder {

    TextView penarikan, rekPenarikan, tglPenarikan, nominalPenarikan;
    ImageView imageView;

    public RecycleViewHolder(View itemView) {
        super(itemView);

        rekPenarikan = (TextView) itemView.findViewById(R.id.txt_penarikan);
        tglPenarikan = (TextView) itemView.findViewById(R.id.txt_tglPenarikan);
        rekPenarikan = (TextView) itemView.findViewById(R.id.txt_rekPenarikan);
        nominalPenarikan = (TextView) itemView.findViewById(R.id.txt_nominalPenarikan);

        imageView = (ImageView) itemView.findViewById(R.id.img_riwayatPenarikan);

    }
}

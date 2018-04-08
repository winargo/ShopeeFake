package com.fake.shopee.shopeefake.ProductSearch;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fake.shopee.shopeefake.R;

import java.util.List;

/**
 * Created by ifanzal on 12/26/17.
 */

public class ProductSearchAdapter extends RecyclerView.Adapter<ProductSearchAdapter.MyViewHolder> {

    private Context mContext;
    private List<ProductSearchRV> productSearchRVList;

    public ProductSearchAdapter(Context mContext, List<ProductSearchRV> productSearchRVList) {
        this.mContext = mContext;
        this.productSearchRVList = productSearchRVList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.template_produk_pencarian, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        ProductSearchRV productSearchRV = productSearchRVList.get(position);
        holder.JudulProduk.setText(productSearchRV.getName());
        Glide.with(mContext).load(productSearchRV.getThumbnail()).into(holder.FotoProduk);

    }

    @Override
    public int getItemCount() {
        return productSearchRVList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView JudulProduk;
        public ImageView FotoProduk;

        public MyViewHolder(View view) {
            super(view);
            JudulProduk = (TextView) view.findViewById(R.id.txt_namaProdukPencarianProduk);
            FotoProduk = (ImageView) view.findViewById(R.id.image_produkPencarianProduk);
        }
    }
}

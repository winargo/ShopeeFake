package com.fake.shopee.shopeefake.ProductSearch;

/**
 * Created by riandy on 1/13/2018.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fake.shopee.shopeefake.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class resultadapter extends RecyclerView.Adapter<resultadapter.MyViewHolder> {

        private List<result> results;


        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView nama , harga , likecount;
            public ImageView gambarproduk;


            public MyViewHolder(View view) {
                super(view);
                gambarproduk = (ImageView) view.findViewById(R.id.image_namaproduk);
                nama = (TextView) view.findViewById(R.id.namaproduk);
                harga = (TextView) view.findViewById(R.id.hargaproduk);
                likecount = (TextView) view.findViewById(R.id.likecount);
            }

        }


        public resultadapter(List<result> moviesList) {
            this.results = moviesList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycleview_resultsearch, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            final Context context = holder.gambarproduk.getContext();
            result movie = results.get(position);
            holder.nama.setText(movie.getNama());
            holder.nama.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent a = new Intent(context,stock_detail.class);
                }
            });
            holder.harga.setText("Rp"+String.valueOf(movie.getHarga()));
            holder.likecount.setText(String.valueOf(movie.getLikecount()));
            Picasso.with(context).load(movie.getImagedir()).resize(400,400).centerCrop().into(holder.gambarproduk);
            Log.e("data image dir", movie.getImagedir());
       //     Picasso.with(context).load(movie.getImagedir()).into(holder.gambarproduk);
        }

        @Override
        public int getItemCount() {
            return results.size();
        }
    }
package com.fake.shopee.shopeefake.recyclerviews;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fake.shopee.shopeefake.R;
import com.fake.shopee.shopeefake.generator;

import java.util.List;

public class recycler_alamat extends RecyclerView.Adapter<recycler_alamat.MyViewHolder>{

    List<String> alladress;
    Context context;

    public recycler_alamat(Context context ,List<String> alladress){

        this.context = context;
        this.alladress = alladress;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_pilih_alamat_items, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.penerima.setText(generator.userlogin);
        holder.address.setText(alladress.get(position));
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generator.chosenadress.setText(alladress.get(position).replace("'"," "));
                ((Activity)context).finish();
            }
        });



    }

    @Override
    public int getItemCount() {
        return alladress.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView address,penerima;
        LinearLayout layout;

        public MyViewHolder(View view) {
            super(view);
            penerima = view.findViewById(R.id.receiver);
            address = view.findViewById(R.id.addressfull);
            layout = view.findViewById(R.id.layout_alamat);
        }
    }
}

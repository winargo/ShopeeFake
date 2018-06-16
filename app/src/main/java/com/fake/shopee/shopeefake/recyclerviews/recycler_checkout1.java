package com.fake.shopee.shopeefake.recyclerviews;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fake.shopee.shopeefake.Main_pages.main_cart;
import com.fake.shopee.shopeefake.R;
import com.fake.shopee.shopeefake.generator;
import com.fake.shopee.shopeefake.objects.cartsubitems;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.List;

public class recycler_checkout1 extends RecyclerView.Adapter<recycler_checkout1.MyViewHolder> {
    public Context context;

    TextView textView,totalall;
    List<cartsubitems> items ;
    DecimalFormat formatter = new DecimalFormat("###,###,###.00");

    public recycler_checkout1(Context con, List<cartsubitems> sel,TextView sellertotal,TextView total){
        this.context=con;
        totalall=total;
        items=sel;
        textView=sellertotal;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_checkout_body, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {;

        if(textView.getText().toString().equals("") || textView.getText().toString().equals("Rp 0")){
            textView.setText(textView.getText().toString().replace("Rp ",""));
            textView.setText(textView.getText().toString().replace(",",""));
            textView.setText("Rp "+formatter.format(items.get(position).getPrice()*Double.parseDouble(items.get(position).getQty())));

        }
        else {
            textView.setText(textView.getText().toString().replace("Rp ",""));
            textView.setText(textView.getText().toString().replace(",",""));
            textView.setText("Rp "+formatter.format(Double.parseDouble(textView.getText().toString())+(items.get(position).getPrice()*Double.parseDouble(items.get(position).getQty()))));

        }
        if(totalall.getText().toString().equals("") || totalall.getText().toString().equals("Rp 0")){
            totalall.setText(totalall.getText().toString().replace("Rp ",""));
            totalall.setText(totalall.getText().toString().replace(",",""));
            totalall.setText("Rp "+formatter.format(items.get(position).getPrice()*Double.parseDouble(items.get(position).getQty())));
        }
        else {
            totalall.setText(totalall.getText().toString().replace("Rp ",""));
            totalall.setText(totalall.getText().toString().replace(",",""));
            totalall.setText("Rp "+formatter.format(Double.parseDouble(totalall.getText().toString())+(items.get(position).getPrice()*Double.parseDouble(items.get(position).getQty()))));
        }
        holder.qty.setText("x "+items.get(position).getQty());
        final String temp=items.get(position).getStockid();
        holder.product.setText(items.get(position).getIproductname());
        holder.price.setText("Rp " + formatter.format(items.get(position).getPrice()));
        Picasso.with(context).load(items.get(position).getImagedir()).resize(200,200).centerCrop().into(holder.imageitem);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView price;
        TextView qty;
        ImageView imageitem;
        TextView product;

        public MyViewHolder(View view) {
            super(view);
            price = view.findViewById(R.id.itemcheckout_priceitem);
            qty = view.findViewById(R.id.itemcheckout_qty);
            product =view.findViewById(R.id.itemcheckout_itemname);
            imageitem = view.findViewById(R.id.itemcheckout_imagedata);
        }
    }
}

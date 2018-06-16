package com.fake.shopee.shopeefake.recyclerviews;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fake.shopee.shopeefake.Main_pages.main_cart;
import com.fake.shopee.shopeefake.ProductSearch.stock_detail;
import com.fake.shopee.shopeefake.R;
import com.fake.shopee.shopeefake.generator;
import com.fake.shopee.shopeefake.objects.cartsubitems;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class recycler_cart1 extends RecyclerView.Adapter<recycler_cart1.MyViewHolder> {
    TextView angkatotal;
    public Context context;
    List<cartsubitems> items ;
    DecimalFormat formatter = new DecimalFormat("###,###,###.00");

    public recycler_cart1(Context con, List<cartsubitems> sel, TextView angka){
        this.context=con;
        items=sel;
        angkatotal=angka;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_layout_carts, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        if(angkatotal.getText().toString().equals("") || angkatotal.getText().toString().equals("Rp 0") || angkatotal.getText().toString().equals("Rp")){
            angkatotal.setText(angkatotal.getText().toString().replace("Rp ",""));
            angkatotal.setText(angkatotal.getText().toString().replace(",",""));
            angkatotal.setText("Rp "+formatter.format(items.get(position).getPrice()*Double.parseDouble(items.get(position).getQty())));
        }
        else {
            angkatotal.setText(angkatotal.getText().toString().replace("Rp ",""));
            angkatotal.setText(angkatotal.getText().toString().replace(",",""));
            angkatotal.setText("Rp "+formatter.format(Double.parseDouble(angkatotal.getText().toString())+((items.get(position).getPrice())*Double.parseDouble(items.get(position).getQty()))));
        }
        holder.qty.setText(items.get(position).getQty());
        final String temp=items.get(position).getStockid();
        holder.product.setText(items.get(position).getIproductname());
        holder.check.setVisibility(View.INVISIBLE);
        holder.price.setText("Rp " + formatter.format(items.get(position).getPrice()));
        Picasso.with(context).load(items.get(position).getImagedir()).resize(200,200).centerCrop().into(holder.imageitem);

        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Integer.parseInt(holder.qty.getText().toString())-1==0){

                    RequestQueue queue = Volley.newRequestQueue(context);
                    String[] url = {""};


                    url[0] ="http://"+ generator.ip+":3000/delitem?pemilik="+generator.userlogin+"&stockid="+temp+"&penjual="+items.get(position).getSellet();
                    Log.e("URl", url[0]);
                    JsonObjectRequest jsonobject = new JsonObjectRequest(url[0], null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    if(response!=null){
                                        holder.hide.setVisibility(View.GONE);
                                        angkatotal.setText("Rp 0");
                                        Intent a = new Intent(context,main_cart.class);
                                        ((Activity) context).startActivity(a);
                                        ((Activity) context).finish();
                                    }
                                    else {
                                        Toast.makeText(context, "gagal", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(context, "not working "+error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    queue.add(jsonobject);
                }
                else {
                    RequestQueue queue = Volley.newRequestQueue(context);
                    String[] url = {""};
                    url[0] ="http://"+ generator.ip+":3000/reduceitem?pemilik="+generator.userlogin+"&stockid="+temp+"&jumlah="+holder.qty.getText().toString()+"&penjual="+items.get(position).getSellet();
                    Log.e("URl", url[0]);
                    JsonObjectRequest jsonobject = new JsonObjectRequest(url[0], null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    if(response!=null){
                                        angkatotal.setText(angkatotal.getText().toString().replace("Rp ",""));
                                        angkatotal.setText(angkatotal.getText().toString().replace(",",""));
                                        angkatotal.setText("Rp "+formatter.format(Double.parseDouble(angkatotal.getText().toString())-(items.get(position).getPrice())));
                                        holder.qty.setText(String.valueOf(Integer.parseInt(holder.qty.getText().toString())-1));
                                    }
                                    else {
                                        Toast.makeText(context, "gagal", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(context, "not working "+error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                    queue.add(jsonobject);

                }
            }
        });

        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestQueue queue = Volley.newRequestQueue(context);
                String[] url = {""};


                url[0] ="http://"+ generator.ip+":3000/plusitem?pemilik="+generator.userlogin+"&stockid="+temp+"&jumlah="+holder.qty.getText().toString()+"&penjual="+items.get(position).getSellet();
                Log.e("URl", url[0]);
                JsonObjectRequest jsonobject = new JsonObjectRequest(url[0], null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                if(response!=null){
                                    angkatotal.setText(angkatotal.getText().toString().replace("Rp ",""));
                                    angkatotal.setText(angkatotal.getText().toString().replace(",",""));
                                    angkatotal.setText("Rp "+formatter.format(Double.parseDouble(angkatotal.getText().toString())+(items.get(position).getPrice())));
                                    holder.qty.setText(String.valueOf(Integer.parseInt(holder.qty.getText().toString())+1));
                                }
                                else {
                                    Toast.makeText(context, "gagal", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "not working "+error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
                queue.add(jsonobject);

            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView price;
        TextView qty;
        ImageView imageitem;
        LinearLayout hide;
        TextView product;
        Button plus,minus;
        CheckBox check;

        public MyViewHolder(View view) {
            super(view);
            check = view.findViewById(R.id.itemcart_checkbox);
            hide = view.findViewById(R.id.cart_keepaddingitem);
            price = view.findViewById(R.id.itemcart_priceitem);
            qty = view.findViewById(R.id.itemcart_itemqty);
            product =view.findViewById(R.id.itemcart_itemname);
            plus = view.findViewById(R.id.itemcart_plus);
            imageitem = view.findViewById(R.id.itemcart_imagedata);
            minus = view.findViewById(R.id.itemcart_minus);
        }
    }
}

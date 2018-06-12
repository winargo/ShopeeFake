package com.fake.shopee.shopeefake.cartview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fake.shopee.shopeefake.Main_pages.MainActivity;
import com.fake.shopee.shopeefake.R;
import com.fake.shopee.shopeefake.SQLclass;
import com.fake.shopee.shopeefake.ShopeePay.RecyclerAdapter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class cartadapter extends RecyclerView.Adapter<cartadapter.MyViewHolder>{

    SQLclass sqlclass;
    private Context mContext;
    private List<cart> productSearchRVList;

    public cartadapter(Context mContext, List<cart> productSearchRVList) {
        this.mContext = mContext;
        this.productSearchRVList = productSearchRVList;
        sqlclass = new SQLclass();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_belanjaanku, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
       cart productSearchRV = productSearchRVList.get(position);
        holder.JudulProduk.setText(productSearchRV.getItem_id());

    }

    @Override
    public int getItemCount() {
        return productSearchRVList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView JudulProduk;
        public ImageView FotoProduk;
        public TextView Hargaproduk;
        public TextView Penjual;

        public MyViewHolder(View view) {
            super(view);
            Hargaproduk = (TextView) view.findViewById(R.id.cart_itemprice);
            Penjual = (TextView) view.findViewById(R.id.cartpenjual);
            JudulProduk = (TextView) view.findViewById(R.id.cart_item_name);
            FotoProduk = (ImageView) view.findViewById(R.id.image_cartitem);
        }

        public void setFotoProduk(ImageView fotoProduk) {
            FotoProduk = fotoProduk;
        }

        public void setHargaproduk(TextView hargaproduk) {
            Hargaproduk = hargaproduk;
        }

        public void setJudulProduk(TextView judulProduk) {
            JudulProduk = judulProduk;
        }

        public void setPenjual(TextView penjual) {
            Penjual = penjual;
        }

        public ImageView getFotoProduk() {
            return FotoProduk;
        }

        public TextView getHargaproduk() {
            return Hargaproduk;
        }

        public TextView getJudulProduk() {
            return JudulProduk;
        }

        public TextView getPenjual() {
            return Penjual;
        }
    }

    public String getidname(String id){
        ResultSet result= sqlclass.querydata("select * from stock where stock_id='"+id+"'");
        String temp="";
        try {
            while (result.next()){
                    temp=result.getString("namaproduk");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return temp;
    }
}


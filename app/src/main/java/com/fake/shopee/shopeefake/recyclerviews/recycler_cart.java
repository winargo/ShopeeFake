package com.fake.shopee.shopeefake.recyclerviews;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fake.shopee.shopeefake.Main_pages.main_cart;
import com.fake.shopee.shopeefake.R;
import com.fake.shopee.shopeefake.generator;
import com.fake.shopee.shopeefake.objects.cartsubitems;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class recycler_cart extends RecyclerView.Adapter<recycler_cart.MyViewHolder> {
    public Context context;
    TextView angkatotal;
    List<String> penjual ;
    List<String[]> itempenjual;

    public recycler_cart(Context con,List<String> sel,List<String[]> items,TextView angka){
        this.context=con;
        penjual=sel;
        itempenjual=items;
        angkatotal=angka;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_carts_header, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        holder.seller.setText(penjual.get(position).replace("@gmail.com",""));
        LayoutInflater inflater = LayoutInflater.from(context);

        View v = inflater.inflate(R.layout.row_layout_carts,null);

        List<cartsubitems> subitems=new ArrayList<>();

        CheckBox all,peritem;
        ImageView imageitem;

        int count=0;
        List<Integer> theposition=new ArrayList<>();

        for (int i =0 ; i<itempenjual.size();i++){
            if(penjual.get(position).equals(itempenjual.get(i)[4])){
                count++;
                theposition.add(i);
            }
        }
        for(int j=0;j<count;j++){
            for(int k=0;k<generator.stockdata.size();k++){
                 if(itempenjual.get(theposition.get(j))[1].equals(generator.stockdata.get(k).getStockid())){
                     subitems.add(new cartsubitems(generator.stockdata.get(k).getImagedir(),generator.stockdata.get(k).getProductname(),itempenjual.get(theposition.get(j))[3],generator.stockdata.get(k).getPrice(),itempenjual.get(theposition.get(j))[1],itempenjual.get(theposition.get(j))[4]));
                    break;
                 }
            }

        }
        recycler_cart1 adapter = new recycler_cart1(context,subitems,angkatotal);
        holder.recycler.setLayoutManager(new GridLayoutManager(context, 1));
        holder.recycler.setItemAnimator(new DefaultItemAnimator());
        holder.recycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        holder.all.setVisibility(View.GONE);

    }

    @Override
    public int getItemCount() {
        return penjual.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView seller;
        CheckBox all;
        RecyclerView recycler;

        public MyViewHolder(View view) {
            super(view);
            seller = view.findViewById(R.id.itemcart_sellername);
            recycler = view.findViewById(R.id.cart_subitems);
            all = view.findViewById(R.id.itemcart_tickall);
        }
    }
}

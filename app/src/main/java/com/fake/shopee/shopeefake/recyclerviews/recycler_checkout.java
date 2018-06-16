package com.fake.shopee.shopeefake.recyclerviews;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fake.shopee.shopeefake.R;
import com.fake.shopee.shopeefake.generator;
import com.fake.shopee.shopeefake.objects.cartsubitems;

import java.util.ArrayList;
import java.util.List;

public class recycler_checkout extends RecyclerView.Adapter<recycler_checkout.MyViewHolder> {
    public Context context;
    TextView totalall;
    List<String> penjual ;
    List<String[]> itempenjual;

    public recycler_checkout(Context con, List<String> sel, List<String[]> items,TextView total){
        totalall=total;
        this.context=con;
        penjual=sel;
        itempenjual=items;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_checkout_header, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        holder.seller.setText(penjual.get(position).replace("@gmail.com",""));
        LayoutInflater inflater = LayoutInflater.from(context);

        View v = inflater.inflate(R.layout.row_layout_carts,null);

        List<cartsubitems> subitems=new ArrayList<>();

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

        holder.expedition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                holder.expeditiontxt.setText("");
            }
        });

        recycler_checkout1 adapter = new recycler_checkout1(context,subitems,holder.totalseller,totalall);
        holder.recycler.setLayoutManager(new GridLayoutManager(context, 1));
        holder.recycler.setItemAnimator(new DefaultItemAnimator());
        holder.recycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }

    @Override
    public int getItemCount() {
        return penjual.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView seller,expeditiontxt,totalseller;
        RecyclerView recycler;
        LinearLayout expedition;

        public MyViewHolder(View view) {
            super(view);
            seller = view.findViewById(R.id.itemcart_sellername);
            recycler = view.findViewById(R.id.checked_subitems);

            expedition=view.findViewById(R.id.linear_expedition);

            totalseller = view.findViewById(R.id.total_seller);

            expeditiontxt = view.findViewById(R.id.checkout_expedition);
        }
    }
}

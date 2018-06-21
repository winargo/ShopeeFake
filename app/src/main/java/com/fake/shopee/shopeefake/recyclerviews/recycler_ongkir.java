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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class recycler_ongkir extends RecyclerView.Adapter<recycler_ongkir.MyViewHolder>{

    List<String> ongkir;
    DecimalFormat formatter = new DecimalFormat("###,###,###.00");
    List<String> harga;
    List<String> detail;
    Context context;
    TextView chosne;

    public recycler_ongkir(Context context,TextView chosenongkir){
        this.context = context;
        ongkir = new ArrayList<>();
        harga = new ArrayList<>();
        detail = new ArrayList<>();

        ongkir.add("JNE Reguler");
        ongkir.add("JNE OKE");
        ongkir.add("POS KILAT KHUSUS");
        ongkir.add("J&T EXPRESS");

        harga.add("Rp 30.000");
        harga.add("Rp 26.000");
        harga.add("Rp 20.000");
        harga.add("Rp 30.000");

        detail.add("Deterima dalam 3-4 Hari");
        detail.add("Deterima dalam 4-5 Hari");
        detail.add("Deterima dalam 5-6 Hari");
        detail.add("Deterima dalam 3-4 Hari");

        chosne = chosenongkir;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_pilih_ongkir, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.ongkirprice.setText(harga.get(position));
        holder.ongkirtxt.setText(ongkir.get(position));
        holder.detailongkir.setText(detail.get(position));

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(generator.chosenongkir.getText().equals("Pilih Pengiriman")) {
                    generator.subtotalcheckout.setText("Rp " + formatter.format(Double.parseDouble(generator.subtotalcheckout.getText().toString().replace(",", "").replace("Rp ", "")) + Double.parseDouble(holder.ongkirprice.getText().toString().replace("Rp ", "").replace(".", ""))));
                    generator.totalcheckout.setText("Rp " + formatter.format(Double.parseDouble(generator.totalcheckout.getText().toString().replace(",", "").replace("Rp ", "")) + Double.parseDouble(holder.ongkirprice.getText().toString().replace("Rp ", "").replace(".", ""))));
                    generator.hargaongkirtemp = Double.parseDouble(holder.ongkirprice.getText().toString().replace("Rp ", "").replace(".", ""));
                }
                else {
                    if (generator.hargaongkirtemp == 0.0d) {
                        generator.subtotalcheckout.setText("Rp " + formatter.format(Double.parseDouble(generator.subtotalcheckout.getText().toString().replace(",", "").replace("Rp ", "")) + Double.parseDouble(holder.ongkirprice.getText().toString().replace("Rp ", "").replace(".", ""))));
                        generator.totalcheckout.setText("Rp " + formatter.format(Double.parseDouble(generator.totalcheckout.getText().toString().replace(",", "").replace("Rp ", "")) + Double.parseDouble(holder.ongkirprice.getText().toString().replace("Rp ", "").replace(".", ""))));
                        generator.hargaongkirtemp = Double.parseDouble(holder.ongkirprice.getText().toString().replace("Rp ", "").replace(".", ""));

                    } else {
                        generator.subtotalcheckout.setText("Rp " + formatter.format(Double.parseDouble(generator.subtotalcheckout.getText().toString().replace(",", "").replace("Rp ", "")) - generator.hargaongkirtemp));
                        generator.subtotalcheckout.setText("Rp " + formatter.format(Double.parseDouble(generator.subtotalcheckout.getText().toString().replace(",", "").replace("Rp ", "")) + Double.parseDouble(holder.ongkirprice.getText().toString().replace("Rp ", "").replace(".", ""))));
                        generator.totalcheckout.setText("Rp " + (Double.parseDouble(generator.totalcheckout.getText().toString().replace(",", "").replace("Rp ", "")) - generator.hargaongkirtemp));
                        generator.totalcheckout.setText("Rp " + formatter.format(Double.parseDouble(generator.totalcheckout.getText().toString().replace(",", "").replace("Rp ", "")) + Double.parseDouble(holder.ongkirprice.getText().toString().replace("Rp ", "").replace(".", ""))));
                        generator.hargaongkirtemp = Double.parseDouble(holder.ongkirprice.getText().toString().replace("Rp ", "").replace(".", ""));

                    }
                }
                chosne.setText(ongkir.get(position));
                ((Activity)context).finish();
            }
        });



    }

    @Override
    public int getItemCount() {
        return detail.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView ongkirtxt, ongkirprice,detailongkir;
        LinearLayout layout;

        public MyViewHolder(View view) {
            super(view);
            layout = view.findViewById(R.id.linearongkir);
            ongkirtxt = view.findViewById(R.id.ongkirtxt);
            ongkirprice = view.findViewById(R.id.ongkirprice);
            detailongkir = view.findViewById(R.id.ongkirdetail);
        }
    }
}

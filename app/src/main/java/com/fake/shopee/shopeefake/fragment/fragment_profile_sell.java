package com.fake.shopee.shopeefake.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fake.shopee.shopeefake.ProductSearch.result;
import com.fake.shopee.shopeefake.ProductSearch.resultadapter;
import com.fake.shopee.shopeefake.ProductSearch.searchresult;
import com.fake.shopee.shopeefake.ProductSearch.searchresultlistener;
import com.fake.shopee.shopeefake.ProductSearch.stock_detail;
import com.fake.shopee.shopeefake.ProductSearch.stock_detail_seller;
import com.fake.shopee.shopeefake.R;
import com.fake.shopee.shopeefake.SQLclass;
import com.fake.shopee.shopeefake.session_class;
import com.fake.shopee.shopeefake.upload.activity_galery;
import com.fake.shopee.shopeefake.upload.camera_test;

import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Riandy on 12/1/2017.
 */


public class fragment_profile_sell extends Fragment{
    SQLclass sqlclass;
    TextView texxt;
    session_class session;
    resultadapter mAdapter;

    List<result> movieList = new ArrayList<result>();

    public static int page=1;
    public void oncreate(Bundle state){
        super.onCreate(state);

        final Bundle args = getArguments();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView =(ViewGroup) inflater.inflate(
                        R.layout.fragment_beli_jual, container, false);

        sqlclass = new SQLclass();
        session = new session_class(getActivity());

        texxt = rootView.findViewById(R.id.xtview);

        RecyclerView myproduct = rootView.findViewById(R.id.listmysolditem);

        Button a = (Button) rootView.findViewById(R.id.buttonjual);
        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] item = {"Kamera","Foto"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("Select");
                dialog.setItems(item,new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int position) {

                        if(position==0) {
                            Intent a = new Intent(getActivity(), camera_test.class);
                            startActivity(a);
                        }
                        if(position==1) {
                            Intent a = new Intent(getActivity(), activity_galery.class);
                            startActivity(a);
                        }
                        Toast.makeText(getContext(),"selected Item:"+position, Toast.LENGTH_SHORT).show();
                    }

                });
                dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = dialog.create();
                alert.show();
            }
        });

        if(session.getusename().equals("")){
            myproduct.setVisibility(View.GONE);
            texxt.setVisibility(View.GONE);
        }
        else {
            texxt.setVisibility(View.VISIBLE);
            myproduct.setVisibility(View.VISIBLE);
        }


        mAdapter = new resultadapter(movieList);
        myproduct.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        myproduct.setItemAnimator(new DefaultItemAnimator());
        myproduct.setAdapter(mAdapter);

        myproduct.addOnItemTouchListener(new searchresultlistener(getActivity().getApplicationContext(), myproduct, new searchresultlistener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                result movie = movieList.get(position);
                movie.setLikecount(movie.getLikecount()+1);
                Intent z = new Intent(getActivity(), stock_detail_seller.class);
                z.putExtra("request",movie.getNama());
                startActivity(z);
                // Toast.makeText(getApplicationContext(), movie.getTitle() + " is selected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        YourAsyncTask item = new YourAsyncTask(getActivity());
        item.execute();



        return rootView;
    }

    private class YourAsyncTask extends AsyncTask<Void, Void, Void> {
        private ProgressDialog dialog;

        public YourAsyncTask(Activity a) {
            dialog = new ProgressDialog(a, R.style.AppCompatAlertDialogStyle);
        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Loading....");
            dialog.show();
        }

        protected Void doInBackground(Void... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ResultSet result = sqlclass.querydata("select * from stock where pemilik='"+session.getusename()+"'");
                    NumberFormat formatter = new DecimalFormat("#,###,###");
                    String yourFormattedString="";
                    try {
                        while (result.next()){
                            result movie = new result(result.getInt("likecount"),result.getString("harga"),result.getString("imagedir"),result.getString("namaproduk"));
                            movieList.add(movie);
                        }
                    }catch (Exception e){
                        Log.e("error occured",e.getMessage());
                    }


                }
            });
            prepareMovieData();
            onPostExecute(true,"completed");
            return null;
        }

        protected void onPostExecute(Boolean b, String a) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }
    private void prepareMovieData() {
        mAdapter.notifyDataSetChanged();
        // result movie = new result("Mad Max: Fury Road", "Action & Adventure", "2015");
        // movieList.add(movie);

        // mAdapter.notifyDataSetChanged();
    }
}

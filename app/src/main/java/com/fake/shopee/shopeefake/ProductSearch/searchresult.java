package com.fake.shopee.shopeefake.ProductSearch;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ToggleButton;

import com.fake.shopee.shopeefake.R;
import com.fake.shopee.shopeefake.SQLclass;
import com.fake.shopee.shopeefake.generator;

import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class searchresult extends AppCompatActivity {

    private RecyclerView recyclerView;
    private String passedkey;
    Button closeback ;
    ToggleButton toggleprice;
    EditText searchbox;
    ImageButton searchback;
    SQLclass sqlclass;

    private resultadapter mAdapter;
    private List<result> movieList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchresult);

        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            passedkey = extras.getString("KEY","");
        }


         sqlclass = new SQLclass();

        closeback = (Button) findViewById(R.id.resultbackback);
        searchbox = (EditText) findViewById(R.id.resultsearchbox);
        searchback = (ImageButton) findViewById(R.id.resultback);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        toggleprice = (ToggleButton) findViewById(R.id.lowhigh);
        toggleprice.setText("Aktifkan Filter Harga");


        toggleprice.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (toggleprice.isChecked()==(true)) {
                    toggleprice.setText("Harga dari Termurah");
                }
                else {
                    toggleprice.setText("Harga dari Termahal");
                }
            }
        });


        searchbox.setText(passedkey);

        searchbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(searchresult.this ,searching.class);
                a.putExtra("KEY",searchbox.getText().toString());
                startActivity(a);
                finish();
            }
        });
        searchback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        closeback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mAdapter = new resultadapter(movieList);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new searchresultlistener(getApplicationContext(), recyclerView, new searchresultlistener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                result movie = movieList.get(position);
                movie.setLikecount(movie.getLikecount()+1);
                Intent z = new Intent(searchresult.this, stock_detail.class);
                z.putExtra("request",movie.getNama());
                startActivity(z);
               // Toast.makeText(getApplicationContext(), movie.getTitle() + " is selected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        YourAsyncTask a = new YourAsyncTask(this);
        a.execute();

    }
    private void prepareMovieData() {
            mAdapter.notifyDataSetChanged();
       // result movie = new result("Mad Max: Fury Road", "Action & Adventure", "2015");
       // movieList.add(movie);

       // mAdapter.notifyDataSetChanged();
    }
    private class YourAsyncTask extends AsyncTask<Void, Void, Void> {
        private ProgressDialog dialog;

        public YourAsyncTask(searchresult a) {
            dialog = new ProgressDialog(a, R.style.AppCompatAlertDialogStyle);
        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Loading....");
            dialog.show();
        }

        protected Void doInBackground(Void... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    ResultSet result = sqlclass.querydata("select * from stock where namaproduk like '%"+passedkey+"%' and pemilik!='"+ generator.userlogin+"'");
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
}

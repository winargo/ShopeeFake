package com.fake.shopee.shopeefake.ProductSearch;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.fake.shopee.shopeefake.R;

import java.util.ArrayList;
import java.util.List;


public class ProductSearchActivity extends Activity {

    private RecyclerView recyclerView;
    private ProductSearchAdapter adapter;
    private List<ProductSearchRV> productSearchRVList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cari_produk);

        recyclerView = (RecyclerView) findViewById(R.id.lst_item_product);

        productSearchRVList = new ArrayList<>();
        adapter = new ProductSearchAdapter(this, productSearchRVList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.hasFixedSize();
        kirimProduk();
    }

    private void kirimProduk() {
        int[] fotoProduk = new int[]{R.drawable.produk_1, R.drawable.produk_2, R.drawable.produk_3};
        String[] judulProduk = new String[]{"Produk1", "Produk2", "Produk3"};

        for (int i = 0; i < 20; i++) {
            ProductSearchRV a = new ProductSearchRV(judulProduk[i % 3], fotoProduk[i % 3]);
            productSearchRVList.add(a);
        }
        adapter.notifyDataSetChanged();
    }

}

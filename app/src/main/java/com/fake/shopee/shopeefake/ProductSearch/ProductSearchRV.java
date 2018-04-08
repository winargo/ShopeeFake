package com.fake.shopee.shopeefake.ProductSearch;

/**
 * Created by ifanzal on 12/26/17.
 */

public class ProductSearchRV {
    private String judulProduk;
    private int fotoProduk;

    public ProductSearchRV(String judulproduk, int fotoproduk) {
        this.judulProduk = judulproduk;
        this.fotoProduk = fotoproduk;
    }

    public String getName() {
        return judulProduk;
    }

    public int getThumbnail() {
        return fotoProduk;
    }

}
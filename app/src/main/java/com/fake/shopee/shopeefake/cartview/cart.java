package com.fake.shopee.shopeefake.cartview;

/**
 * Created by Riandy on 5/22/2018.
 */

public class cart {

    String item_seller,item_count,item_id,pemilik;
    int itm_qty;

    public cart(String isel,String icou,String iid,String pem){
        item_seller=isel;
        item_count=icou;
        item_id=iid;
        pemilik=pem;
    }

    public cart(){

    }

    public void setItem_count(String item_count) {
        this.item_count = item_count;
    }

    public void setItem_seller(String item_seller) {
        this.item_seller = item_seller;
    }

    public String getItem_count() {
        return item_count;
    }

    public void setItm_qty(int itm_qty) {
        this.itm_qty = itm_qty;
    }

    public String getItem_seller() {
        return item_seller;
    }

    public int getItm_qty() {
        return itm_qty;
    }


    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public void setPemilik(String pemilik) {
        this.pemilik = pemilik;
    }

    public String getItem_id() {
        return item_id;
    }

    public String getPemilik() {
        return pemilik;
    }
}

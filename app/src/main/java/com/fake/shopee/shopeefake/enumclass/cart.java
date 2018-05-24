package com.fake.shopee.shopeefake.enumclass;

/**
 * Created by Riandy on 5/22/2018.
 */

public class cart {

    String item_seller,item_count,item_name,item_specification,item_color;
    int itm_qty;

    public cart(String isel,String icou,String inam,String ispec,String icol,int iqty){
        item_color=icol;
        item_count=icou;
        item_name=inam;
        item_specification=ispec;
        item_seller=isel;
    }

    public cart(){

    }

    public void setItem_count(String item_count) {
        this.item_count = item_count;
    }

    public void setItem_seller(String item_seller) {
        this.item_seller = item_seller;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_count() {
        return item_count;
    }

    public void setItem_color(String item_color) {
        this.item_color = item_color;
    }

    public void setItem_specification(String item_specification) {
        this.item_specification = item_specification;
    }

    public void setItm_qty(int itm_qty) {
        this.itm_qty = itm_qty;
    }

    public String getItem_name() {
        return item_name;
    }

    public String getItem_seller() {
        return item_seller;
    }

    public int getItm_qty() {
        return itm_qty;
    }

    public String getItem_color() {
        return item_color;
    }

    public String getItem_specification() {
        return item_specification;
    }

}

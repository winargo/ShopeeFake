package com.fake.shopee.shopeefake.objects;

public class cartsubitems {
    String imagedir,iproductname,qty,stockid,sellet;
    Double price;

    public cartsubitems(){}

    public cartsubitems(String idir,String inam,String qt,Double pri,String stockid,String seller){
        imagedir=idir;
        iproductname=inam;
        qty=qt;
        price=pri;
        this.stockid=stockid;
        sellet=seller;
    }

    public String getImagedir() {
        return imagedir;
    }

    public Double getPrice() {
        return price;
    }

    public String getIproductname() {
        return iproductname;
    }

    public String getQty() {
        return qty;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setIproductname(String iproductname) {
        this.iproductname = iproductname;
    }

    public void setImagedir(String imagedir) {
        this.imagedir = imagedir;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getStockid() {
        return stockid;
    }

    public void setStockid(String stockid) {
        this.stockid = stockid;
    }

    public String getSellet() {
        return sellet;
    }

    public void setSellet(String sellet) {
        this.sellet = sellet;
    }
}

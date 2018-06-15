package com.fake.shopee.shopeefake.objects;

public class stocklist {
    String stockid,imagedir,imagefile,productname,category,seller,info;
    Double price,weight;
    int stockleftt;

    public stocklist (){}
    public stocklist(String sid,String idir,String ifil,String pnam,String cat,String sel,String inf,Double pric,Double wei,int slef){
        stockid=sid;
        imagedir=idir;
        imagefile=ifil;
        productname=pnam;
        category=cat;
        seller=sel;
        info=inf;
        price=pric;
        weight=wei;
        stockleftt=slef;
    }

    public Double getPrice() {
        return price;
    }

    public Double getWeight() {
        return weight;
    }

    public String getCategory() {
        return category;
    }

    public int getStockleftt() {
        return stockleftt;
    }

    public String getImagedir() {
        return imagedir;
    }

    public String getImagefile() {
        return imagefile;
    }

    public String getInfo() {
        return info;
    }

    public String getProductname() {
        return productname;
    }

    public String getSeller() {
        return seller;
    }

    public String getStockid() {
        return stockid;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setImagedir(String imagedir) {
        this.imagedir = imagedir;
    }

    public void setImagefile(String imagefile) {
        this.imagefile = imagefile;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setStockid(String stockid) {
        this.stockid = stockid;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public void setStockleftt(int stockleftt) {
        this.stockleftt = stockleftt;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

}

package com.fake.shopee.shopeefake.objects;

public class addresobj {

    String address,header,nama,telephon;

    public addresobj(String add,String head,String nam,String tele){

        address = add;
        header=head;
        nama =nam;
        telephon=tele;

    }

    public String getAddress() {
        return address;
    }

    public String getHeader() {
        return header;
    }

    public String getNama() {
        return nama;
    }

    public String getTelephon() {
        return telephon;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setTelephon(String telephon) {
        this.telephon = telephon;
    }

    public void setHeader(String header) {
        this.header = header;
    }
}

package com.fake.shopee.shopeefake.objects;

/**
 * Created by riandy on 1/12/2018.
 */


public class sqliteclass {


    //private variables
    int _id;
    String _searchhistory;

    // Empty constructor
    public sqliteclass(){

    }
    // constructor
    public sqliteclass(int id, String searchhistory){
        this._id = id;
        this._searchhistory = searchhistory;
    }

    // getting ID
    public int getID(){
        return this._id;
    }

    // setting id
    public void setID(int id){
        this._id = id;
    }

    // getting name
    public String get_searchhistory(){
        return this._searchhistory;
    }

    // setting name
    public void set_searchhistory(String name){
        this._searchhistory = name;
    }

}

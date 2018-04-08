package com.fake.shopee.shopeefake;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Riandy on 7/7/2017.
 */
public class SQLclass {

    public String status = "";
    public static String ip = "192.168.18.1";
    public static String db = "shopee";
    public static String un = "sa";
    public static String password = "12345";
    public static String port = "1433";
    public static String instance="";
    Connection conn = null;


    public SQLclass() {
        if(conn==null){
            conn=CONN(ip,db,un,password,port,instance);
        }
    }


    public Connection CONN(String Ip, String Db, String Un, String Password, String Port, String Instance) {
        ip = Ip;
        db = Db;
        un = Un;
        port=Port;
        String ConnURL = null;
        password = Password;
        instance=Instance;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            if ((!instance.equals("") && (port.equals("")))){
                Log.i("info","1");
                Log.i("port",port);
                Log.i("instance",instance);
                ConnURL = "jdbc:jtds:sqlserver://" + Ip +";instance="+instance+";instancename="+instance
                        + ";databaseName=" + Db + ";user=" + Un + ";password="
                        + Password + ";";
            }
            if ((!instance.equals("") )&& (port.length()>=1)){
                Log.i("info","2");
                Log.i("info",port);
                Log.i("info",instance);
                ConnURL = "jdbc:jtds:sqlserver://" + Ip +":"+port+ ";"
                        + "instance="+instance+";instancename="+instance
                        + ";databaseName=" + Db + ";user=" + Un + ";password="
                        + Password + ";";
            }
            if (((instance.equals("")) && (port.length()>=1))){
                Log.i("info","3");
                Log.i("info",port);
                Log.i("info",instance);
                ConnURL = "jdbc:jtds:sqlserver://" + Ip +":"+port+ ";databaseName=" + Db + ";user=" + Un + ";password="
                        + Password + ";";
            }
            if ((instance.equals("")) && (port.equals(""))){
                Log.i("info","4");
                Log.i("info",port);
                Log.i("info",instance);
                ConnURL = "jdbc:jtds:sqlserver://" + Ip +";databaseName=" + Db + ";user=" + Un + ";password="
                        + Password + ";";}
            conn = DriverManager.getConnection(ConnURL);
        }catch (Exception e) {
            Log.e("ERROers", e.getMessage()+ConnURL);
        }
        return conn;
    }


    public ResultSet querydata(String data) {
        String z;
        ResultSet result = null;
        try {
            String query = data;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            result = rs;
            /*
            reparedStatement pstmt = con.prepareStatement(query);
pstmt.setString(1,itemid);
java.sql.ResultSet rs = pstmt.executeQuery();*/
            if (conn == null || result == null) {
                result = null;
            }
        } catch (SQLException e) {
            Log.e("ERRO", e.getMessage());
        }
        return result;

    }
    public int queryexecute(String data) {
        String z;
        int result = 0;
        try {
            Connection con = CONN(ip, db, un, password, port,instance);
            String query = data;
            Statement stmt = con.createStatement();
            int rs = stmt.executeUpdate(query);
            result = rs;
            /*
            reparedStatement pstmt = con.prepareStatement(query);
pstmt.setString(1,itemid);
java.sql.ResultSet rs = pstmt.executeQuery();*/
            if (con == null || result == 0) {
                result = 0;
            }
        } catch (SQLException e) {
            Log.e("ERRO", e.getMessage());
        }
        return result;

    }
}
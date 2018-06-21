var express = require('express')
var mysql = require('mysql')
var bodyParser = require('body-parser')
var app = express()
var port = 3000

var admin = require("firebase-admin");

var serviceAccount = require("service.json");

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount),
  databaseURL: "https://shopeef-5eda6.firebaseio.com"
});





// parse application/x-www-form-urlencoded
app.use(bodyParser.urlencoded({ extended: false }))
// parse application/json
app.use(bodyParser.json())

//connection
var conn = mysql.createConnection({
  host : 'localhost',
  user : 'root',
  password : '',
  database : 'shopee'
})

app.get('/',function(req,res,next){
      res.send('server is active');
})

app.get('/sendinfo/:pemilik/:pengirim',function(req,res,next){
  var pemilik = req.params.pemilik;
  var pengirim = req.params.pengirim;
  var sql = 'select * from usertoken where pemilik = ?';
  conn.query(sql,pemilik,function(err,result){
    if(err)
      throw err
    else{
      if(result.length!=0){
      for (var i = 0; i < result.length; i++) {
          console.log(result[i].token);
          var registrationToken = result[i].token;

          // See documentation on defining a message payload.
          var message = {
              android: {
                ttl: 3600 * 1000, // 1 hour in milliseconds
                priority: 'normal',
                notification: {
                  title: 'Shopee',
                  body: 'Anda mendapat pesanan dari '+pengirim.replace("@gmail.com",""),
                  color: '#f45342'
                }
              },token : registrationToken
            };

          console.log("token data"+registrationToken);
          // Send a message to the device corresponding to the provided
          // registration token.
          admin.messaging().send(message)
            .then((response) => {
              // Response is a message ID string.
              console.log('Successfully sent message:', response);

            })
            .catch((error) => {
              console.log('Error sending message:', error);
            });
          res.end();
      }
    }else {
      res.send("error");
    }
    }
  })
})

// This registration token comes from the client FCM SDKs.


app.get('/allitem',function(req,res) {
    var sql = 'select * from cart';
    conn.query(sql,function(err,row){
      if(err)
        throw err;
      else{
        res.json(row);
      }
    })
})

app.get('/getitem/:name',function(req,res){

  var pemilik = req.params.name;
  var sql = 'select * from cart where pemilik = ? order by penjual_pemilik asc';
  conn.query(sql,pemilik,function(err,result){
    if(err)
      throw err
    else{
      res.json(result);
    }
  })
})

app.get('/checktoken/:name',function(req,res){

  var pemilik = req.params.name;
  var sql = 'select * from usertoken where token = ?';
  conn.query(sql,pemilik,function(err,result){
    if(err)
      throw err
    else{
      res.json(result);
    }
  })
})

app.get('/deltoken',function(req,res){
  var pemilik = req.query.pemilik;
  var stockid = req.query.token;
  sql = 'delete from usertoken where pemilik = ? AND token= ?';
  conn.query(sql,[pemilik,stockid],function(err,row){
    if(err)
      throw err
    else{
      res.json(row);
    }
  })
})

app.get('/inserttoken',function(req,res){

  var pemilik = req.query.pemilik;
  var stockid = req.query.token;

  sql = 'insert into usertoken (pemilik,token) values (?,?)';

  conn.query(sql,[pemilik,stockid],function(err,row){
    if(err)
      throw err
    else{
      res.json(row);
    }
  })

})

app.get('/insertitem',function(req,res){

  var pemilik = req.query.pemilik;
  var stockid = req.query.stockid;
  var stock = req.query.jumlah;
  var penjual = req.query.penjual;
  var imagedata = req.query.imagedata;

  sql = 'insert into cart (pemilik,stock_id,jumlah,penjual_pemilik,imagedata) values (?,?,?,?,?)';

  conn.query(sql,[pemilik,stockid,stock,penjual,imagedata],function(err,row){
    if(err)
      throw err
    else{
      res.json(row);
    }
  })

})

app.get('/checkout',function(req,res){

  var pemilik = req.query.pemilik;
  var stockid = req.query.stockid;
  var stock = req.query.jumlah;
  var penjual = req.query.penjual;
  var imagedata = req.query.imagedata;
  var address = req.query.address;

  sql = 'insert into order (order_id,order_status,order_resi,order_date,pemilik,stock_id,jumlah,penjual,imagedata) values (?,1,?,?,?,?,?,?,?,?)';

  conn.query(sql,[makeid(),new Date(),pemilik,stockid,stock,penjual,imagedata],function(err,row){
    if(err)
      throw err
    else{
      res.json(row);
    }
  })

})

app.get('/checkoutez',function(req,res){

  var pemilik = req.query.pemilik;
  var stockid = req.query.stockid;
  var stock = req.query.jumlah;
  var penjual = req.query.penjual;
  var imagedata = req.query.imagedata;
  var address = req.query.address;

  sql = 'insert into order (order_id,order_status,order_resi,order_date,pemilik,stock_id,jumlah,penjual,imagedata) select  alues (?,1,?,?,?,?,?,?,?,?)';

  conn.query(sql,[makeid(),'',new Date(),pemilik,stockid,stock,penjual,imagedata],function(err,row){
    if(err)
      throw err
    else{
      res.json(row);
    }
  })

})

app.get('/checkinsertitem',function(req,res){

  var pemilik = req.query.pemilik;
  var stockid = req.query.stockid;
  var penjual = req.query.penjual;

  sql = 'select * from cart where pemilik = ? and stock_id =? and penjual_pemilik=?';

  conn.query(sql,[pemilik,stockid,penjual],function(err,row){
    if(err)
      throw err
    else{
      res.json(row);
    }
  })

})

app.get('/delitem',function(req,res){
  var pemilik = req.query.pemilik;
  var stockid = req.query.stockid;
  var penjual = req.query.penjual;
  sql = 'delete from cart where pemilik = ? AND stock_id = ? AND penjual_pemilik = ?';
  conn.query(sql,[pemilik,stockid,penjual],function(err,row){
    if(err)
      throw err
    else{
      res.json(row);
    }
  })
})

app.get('/additem',function(req,res){
  var pemilik = req.query.pemilik;
  var stockid = req.query.stockid;
  var jumlah  = req.query.jumlah;
  var penjual = req.query.penjual;
  sql = 'update cart set jumlah = ?  where pemilik = ? AND stock_id = ? AND penjual_pemilik = ?';
  conn.query(sql,[jumlah,pemilik,stockid,penjual],function(err,row){
    if(err)
      throw(err)
    else{
      res.json(row);
    }
  })
})

app.get('/plusitem',function(req,res){
  var pemilik = req.query.pemilik;
  var stockid = req.query.stockid;
  var jumlah  = req.query.jumlah;
  var penjual = req.query.penjual;
  sql = 'update cart set jumlah = ?+1  where pemilik = ? AND stock_id = ? AND penjual_pemilik = ?';
  conn.query(sql,[jumlah,pemilik,stockid,penjual],function(err,row){
    if(err)
      throw(err)
    else{
      res.json(row);
    }
  })
})

app.get('/reduceitem',function(req,res){
  var pemilik = req.query.pemilik;
  var stockid = req.query.stockid;
  var jumlah  = req.query.jumlah;
  var penjual = req.query.penjual;
  sql = 'update cart set jumlah = ?-1  where pemilik = ? AND stock_id = ? AND penjual_pemilik = ?';
  conn.query(sql,[jumlah,pemilik,stockid,penjual],function(err,row){
    if(err)
      throw(err)
    else{
      res.json(row);
    }
  })
})

app.get('/getaddress',function(req,res){

  var pemilik = req.query.pemilik;

  sql = 'select * from address where address_pemilik = ?';

  conn.query(sql,[pemilik],function(err,row){
    if(err)
      throw err
    else{
      res.json(row);
    }
  })

})

app.get('/insertaddress',function(req,res){

  var pemilik = req.query.pemilik;
  var street = req.query.street;
  var postal = req.query.postal;
  var number = req.query.number;

  sql = 'insert into address (address_pemilik,address_street,address_postal,address_number) values (?,?,?,?)';

  conn.query(sql,[pemilik,street,postal,number],function(err,row){
    if(err)
      throw err
    else{
      res.json(row);
    }
  })

})

app.listen(port,()=>{
  console.log('server up on port : ' + port);
})

function makeid() {
  var text = "";
  var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

  for (var i = 0; i < 5; i++)
    text += possible.charAt(Math.floor(Math.random() * possible.length));

  return text;
}

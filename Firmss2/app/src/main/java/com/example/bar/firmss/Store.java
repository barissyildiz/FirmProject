package com.example.bar.firmss;

public class Store {

  private String storename,storetype,storemiktarı,storefiyatı,storealışfiyatı;

  public Store() {

  }

  public Store(String storename,String storetype,String storemiktarı,String storefiyatı,String storealışfiyatı) {

    this.storename = storename;
    this.storetype = storetype;
    this.storemiktarı = storemiktarı;
    this.storefiyatı = storefiyatı;
    this.storealışfiyatı = storealışfiyatı;
  }

  public String getStorename() {

    return storename;
  }

  public void setStorename(String storename) {

    this.storename = storename;
  }

  public String getStoretype() {

    return storetype;
  }

  public void setStoretype(String storetype) {

    this.storetype = storetype;
  }

  public String getStoremiktarı() {

    return storemiktarı;
  }

  public void setStoremiktarı(String storemiktarı) {

    this.storemiktarı = storemiktarı;
  }

  public String getStorefiyatı() {

    return storefiyatı;
  }

  public void setStorefiyatı(String storefiyatı) {

    this.storefiyatı = storefiyatı;
  }

  public String getStorealışfiyatı() {
    return storealışfiyatı;
  }

  public void setStorealışfiyatı(String storealışfiyatı) {
    this.storealışfiyatı = storealışfiyatı;
  }

  public String getInfo() {

    String info = "STORE NAME : "+storename+"\n"+
            "STORE TYPE : "+storetype+"\n"+
            "STORE MİKTARI : "+storemiktarı+"\n"+
            "STORE FİYATI : "+storefiyatı+"\n"+
            "STORE ALIŞFİYATI :"+storealışfiyatı;

    return info;

  }
}
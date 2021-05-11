package com.example.bar.firmss;

public class FİRMS {

  private int postakodu,money,storemiktar,store_sell_or_buy_alışfiyatı,store_sell_or_buy_satışfiyatı;
  private String name,tel,address,mail,country,city,ilçe,storename,sellstoredate;

  public FİRMS(String name,String address,String tel,String mail,String country,String city,String ilçe,int postakodu,int money) {
    this.name = name;
    this.address = address;
    this.tel = tel;
    this.mail = mail;
    this.country = country;
    this.city = city;
    this.ilçe = ilçe;
    this.postakodu = postakodu;
    this.money = money;
  }

  public FİRMS() {


  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getTel() {
    return tel;
  }

  public void setTel(String tel) {
    this.tel = tel;
  }

  public String getMail() {
    return mail;
  }

  public void setMail(String mail) {
    this.mail = mail;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getIlçe() {
    return ilçe;
  }

  public void setIlçe(String ilçe) {
    this.ilçe = ilçe;
  }

  public int getPostakodu() {
    return postakodu;
  }

  public void setPostakodu(int postakodu) {
    this.postakodu = postakodu;
  }

  public int getMoney() {
    return money;
  }

  public void setMoney(int money) {
    this.money = money;
  }

  public String getStorename() {
    return storename;
  }

  public void setStorename(String storename) {
    this.storename = storename;
  }

  public int getStoremiktar() {
    return storemiktar;
  }

  public void setStoremiktar(int storemiktar) {
    this.storemiktar = storemiktar;
  }

  public String getSellstoredate() {
    return sellstoredate;
  }

  public void setSellstoredate(String sellstoredate) {
    this.sellstoredate = sellstoredate;
  }

  public int getStore_sell_or_buy_alışfiyatı() {
    return store_sell_or_buy_alışfiyatı;
  }

  public void setStore_sell_or_buy_alışfiyatı(int store_sell_or_buy_alışfiyatı) {
    this.store_sell_or_buy_alışfiyatı = store_sell_or_buy_alışfiyatı;
  }

  public int getStore_sell_or_buy_satışfiyatı() {
    return store_sell_or_buy_satışfiyatı;
  }

  public void setStore_sell_or_buy_satışfiyatı(int store_sell_or_buy_satışfiyatı) {
    this.store_sell_or_buy_satışfiyatı = store_sell_or_buy_satışfiyatı;
  }

  public String firminfo() {

    String info;

    info = "FİRM NAME :"+name+ "\n"+ "FİRM TEL :"+tel+ "\n"+ "FİRM ADDRESS :"+address+ "\n"+ "FİRM MAİL :"+mail+ "\n"
            + "FİRM COUNTRY :"+country+ "\n"+ "FİRM CİTY :"+city+ "\n"+ "FİRM İLÇE :"+ilçe+ "\n"+ "FİRM POSTA KODU :"+postakodu;

    return info;
  }
}
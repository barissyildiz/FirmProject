package com.example.bar.firmss;

public class Users {

  private String username,password,result,mail;

  public Users(String username,String password,String result,String mail) {

    this.username = username;
    this.password = password;
    this.result = result;
    this.mail = mail;
  }

  public Users() {


  }

  public String getUserName() {

    return username;
  }

  public void setUserName(String username) {

    this.username = username;

  }

  public String getUserPassword() {

    return password;
  }

  public void setUserPassword(String password) {

    this.password = password;
  }

  public String getResult() {

    return result;
  }

  public void setResult(String result) {

    this.result = result;
  }

  public String getMail() {

    return mail;
  }

  public void setMail(String mail) {

    this.mail = mail;
  }
}
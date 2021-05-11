package com.example.bar.firmss;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.creativityapps.gmailbackgroundlibrary.BackgroundMail;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;

public class QueryPanel extends AppCompatActivity implements View.OnClickListener,CompoundButton.OnCheckedChangeListener {

  Context context = this;
  TextView text_forget_password,text_save_user;
  EditText text_users,text_password;
  Button btn_enter,btn_clear,btn_take_password;
  CheckBox remember_users;
  RadioButton radio_one,radio_second;
  TextInputLayout text_input_name,text_input_question_result,user_new_password;
  SharedPreferences sharedPreferences;
  SharedPreferences.Editor editor;
  SQLLiteHelperr database;
  Hashtable<String,String> hashtable;
  View view;
  Boolean remember = false;
  Boolean click = false;
  public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 100;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.users);

    identifiers();
    listeners();

    sharedPreferences = context.getSharedPreferences("USERS", Context.MODE_PRIVATE);
    editor = sharedPreferences.edit();
    text_users.setText(sharedPreferences.getString("username",""));
    text_password.setText(sharedPreferences.getString("userpassword",""));

    database = new SQLLiteHelperr(context);
    //database.createdatabase();
    //database.opendatabase();

    Hashtable<String,String> hashtable1 = database.questionresultcontrol("admin","ankara");

    if(hashtable1.isEmpty()) {

      database.PersonelEkle(new Users("admin","100","ankara","yönetici@hotmail.com"));

    }
  }

  @Override
  protected void onPause() {
    super.onPause();

    BroadCastReceiver broadCastReceiver = new BroadCastReceiver();

    IntentFilter ıntentFilter = new IntentFilter();
    ıntentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
    ıntentFilter.addAction(Intent.ACTION_POWER_CONNECTED);
    registerReceiver(broadCastReceiver,ıntentFilter);

  }

  @Override
  protected void onDestroy() {
    super.onDestroy();

    unregisterReceiver(new BroadCastReceiver());

  }

  @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
  public boolean checkPermission3 ( final Context context){
    int currentAPIVersion = Build.VERSION.SDK_INT;
    if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
      if (ContextCompat.checkSelfPermission(context, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.INTERNET)) {
          AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
          alertBuilder.setCancelable(true);
          alertBuilder.setTitle("Permission necessary");
          alertBuilder.setMessage("External storage permission is necessary");
          alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            public void onClick(DialogInterface dialog, int which) {

              ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.INTERNET}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
            }
          });
          AlertDialog alert = alertBuilder.create();
          alert.show();

        } else {
          ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.INTERNET}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        }
        return false;
      } else {
        return true;
      }
    } else {
      return true;
    }
  }

  public void identifiers() {

    text_users = findViewById(R.id.users);
    text_password = findViewById(R.id.password);
    text_save_user = findViewById(R.id.save_user);
    btn_enter = findViewById(R.id.btn_users_enter);
    btn_clear = findViewById(R.id.btn_users_clear);
    remember_users = findViewById(R.id.remember);
    text_forget_password = findViewById(R.id.forget_password);

    view = LayoutInflater.from(context).inflate(R.layout.alertdialog,null,false);

    text_input_name = view.findViewById(R.id.user_forget_name);
    text_input_question_result = view.findViewById(R.id.user_forget_question);
    btn_take_password = view.findViewById(R.id.btn_take_password);
    radio_one = view.findViewById(R.id.radio_one);
    radio_second = view.findViewById(R.id.radio_second);
    user_new_password = view.findViewById(R.id.user_new_password);

  }

  public void listeners() {

    btn_enter.setOnClickListener(this);
    btn_clear.setOnClickListener(this);
    btn_take_password.setOnClickListener(this);
    text_forget_password.setOnClickListener(this);
    text_save_user.setOnClickListener(this);
    remember_users.setOnCheckedChangeListener(this);
    radio_one.setOnCheckedChangeListener(this);
    radio_second.setOnCheckedChangeListener(this);
  }

  @Override
  public void onClick(View v) {

    if(v.getId() == R.id.btn_users_enter) {

      //Login arayüzünde personel girişinin kontrolü sağlanır
      boolean enter = false;

      int length = database.PersonelListele()[0].size();

      for (int i = 0; i < length; i++) {

        //database.Listele()[] dizisinin ilk elemanı personel adlarının listesini,ikinci elemanı personel şifresini tutar
        if (text_users.getText().toString().equalsIgnoreCase(database.PersonelListele()[0].get(i)) && text_password.getText().toString().equalsIgnoreCase(database.PersonelListele()[1].get(i))) {

          Intent intent = new Intent(QueryPanel.this, MainActivity.class);

          startActivity(intent);

          WritingPersonalFile(i);

          enter = true;

          ActivePersonalSave();

          break;
        }
      }

      if (enter != true) {

        Toast.makeText(context, "USERNAME OR PASSWORD WRONG", Toast.LENGTH_SHORT).show();
      }
      if (remember == true) {

        editor.putString("username", text_users.getText().toString());
        editor.putString("userpassword", text_password.getText().toString());
        editor.commit();

      } else if (remember != true) {

        editor.remove("username");
        editor.remove("userpassword");
        editor.commit();
      }
    }
    else if (v.getId() == R.id.btn_users_clear) {

      text_users.setText("");
      text_password.setText("");

    }

    else if(v.getId() == R.id.btn_take_password) {

      SQLLiteHelperr database = new SQLLiteHelperr(context);

      hashtable = database.questionresultcontrol(text_input_name.getEditText().getText().toString(),text_input_question_result.getEditText().getText().toString());

      if(!hashtable.isEmpty()) {

        click = true;
        radio_one.setVisibility(View.VISIBLE);
        radio_second.setVisibility(View.VISIBLE);

      }
      else {

        Toast.makeText(context,"KULLANICI ADI VEYA GÜVENLİK CEVABI YANLIŞ",Toast.LENGTH_SHORT).show();

      }
    }

    else if(v.getId() == R.id.forget_password) {

      AlertDialog.Builder alert = new AlertDialog.Builder(context);

      alert.setTitle("KULLANICI İŞLEMLERİ");
      alert.setMessage("PAROLANIZI ALMAK İÇİN GÜVENLİK SORUNUZU CEVAPLAYINIZ");
      alert.setCancelable(true);

      if(view.getParent()!= null) {

        ((ViewGroup)view.getParent()).removeView(view);

      }
      alert.setView(view);
      alert.setPositiveButton("TAMAM", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

              if (radio_one.isChecked()) {

                /*
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT,"PASSWORD ALMA İSTEĞİ");
                intent.putExtra(Intent.EXTRA_TEXT," SAYIN KULLANICIMIZ ŞİFRENİZ "+hashtable.get("password")+" şifrenizi lütfen kimseyle paylaşmayınız ");
                intent.putExtra(Intent.EXTRA_EMAIL,new String[] {hashtable.get("mail")});
                startActivity(intent);
                */

                BackgroundMail.newBuilder(context)
                        .withUsername("denemehesap10011@gmail.com")
                        .withPassword("deneme13579")
                        .withMailto("fura@next2cloud.info")
                        .withType(BackgroundMail.TYPE_PLAIN)
                        .withSubject("PASSWORD ALMA İSTEĞİ")
                        .withBody("SAYIN KULLANICIÖIZ ŞİFRENİZ "+hashtable.get("password")+" ŞİFRENİZİ LÜTFEN KİMSEYLE PAYLAŞMAYINIZ ")
                        .withOnSuccessCallback(new BackgroundMail.OnSuccessCallback() {
                              @Override
                              public void onSuccess() {
                                Toast.makeText(context,"mail adresinize şifreniz gönderilmiştir",Toast.LENGTH_SHORT).show(); }
                            })
                        .withOnFailCallback(new BackgroundMail.OnFailCallback() {

                              @Override
                              public void onFail() {

                                Toast.makeText(context,"mail adresine şifre gönderilemedi",Toast.LENGTH_SHORT).show();
                              }
                            })
                        .send();

              } else if (radio_second.isChecked()) {

                database.updatePersonelInfo(text_input_name.getEditText().getText().toString(), "password", user_new_password.getEditText().getText().toString());

                AlertDialog.Builder alert = new AlertDialog.Builder(context);

                alert.setTitle("PAROLA DEĞİŞİMİ");

                alert.setMessage("YENİ PAROLANIZ " + user_new_password.getEditText().getText().toString());

                alert.show();
              }
        }
      });

      alert.show();

      click = false;

      if((click == false )) {
        text_input_name.getEditText().setText("");
        text_input_question_result.getEditText().setText("");
        user_new_password.setVisibility(View.INVISIBLE);
        user_new_password.getEditText().setText("");
        radio_one.setVisibility(View.INVISIBLE);
        radio_second.setVisibility(View.INVISIBLE);
        radio_one.setChecked(false);
        radio_second.setChecked(false);
      }
    }
    else if(v.getId() == text_save_user.getId()) {

        Intent intent = new Intent(context,Personnel.class);
        intent.putExtra("menuitem","PERSONEL EKLE");
        startActivity(intent);
    }
  }

  @Override
  public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    if(buttonView.getId() == R.id.remember) {

      if(isChecked) {

        remember = true;
      }
      else {

        remember = false;
      }
    }
    else if(buttonView.getId() == R.id.radio_one) {

      if(isChecked) {

        user_new_password.setVisibility(View.INVISIBLE);
      }
    }
    else if(buttonView.getId() == R.id.radio_second) {

      if(isChecked) {

        user_new_password.setVisibility(View.VISIBLE);
      }
    }
  }

  public void ActivePersonalSave() {

    sharedPreferences = context.getSharedPreferences("USERS", Context.MODE_PRIVATE);
    editor = sharedPreferences.edit();
    editor.putString("personalname",text_users.getText().toString());
    editor.putString("personalpassword",text_password.getText().toString());
  }

  public void WritingPersonalFile(int i) {

    String username = database.PersonelListele()[0].get(i);
    String userpassword = database.PersonelListele()[1].get(i);

    File file = new File(context.getFilesDir().getAbsolutePath(), "PERSONELLER");

    if (!file.exists()) {

      try {
        file.createNewFile();

      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    try {
      BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
      bufferedWriter.write("GİRİŞ YAPAN PERSONEL : ");
      bufferedWriter.write(username);
      bufferedWriter.newLine();
      bufferedWriter.write("GİRİŞ YAPAN PERSONEL ŞİFRESİ : ");
      bufferedWriter.write(userpassword);
      bufferedWriter.newLine();
      bufferedWriter.write("PROĞRAMA GİRİŞ YAPILAN ZAMAN : ");
      bufferedWriter.write(Calendar.getInstance().getTime().toGMTString());
      bufferedWriter.newLine();
      bufferedWriter.flush();
      bufferedWriter.close();
    } catch (Exception e) {

      e.printStackTrace();
    }
  }
}

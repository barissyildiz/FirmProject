package com.example.bar.firmss;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class Personnel extends AppCompatActivity implements View.OnClickListener {

    Context context = this;
    TextView personnel_textheader,personnel_text;
    EditText personnel_edittext;
    Button btn_confirm,btn_cancel;
    int count = 0;
    String personnelname,personnelpassword,personnelsecurityresult,personnelmailaddress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.personnel);

        identifiers();
        listeners();

        //Personel ekleme yada silme olmasını belirler
        Intent intent = getIntent();
        String personnelheader = intent.getStringExtra("menuitem");
        personnel_textheader.setText(personnelheader);

    }

    public void identifiers() {

        personnel_textheader = findViewById(R.id.textview_personnel_header);
        personnel_text = findViewById(R.id.textview_personnel_name);
        personnel_edittext = findViewById(R.id.edittext_personnel_info);
        btn_confirm = findViewById(R.id.personnel_continue);
        btn_cancel = findViewById(R.id.personnel_cancel);

    }

    public void listeners() {

        btn_confirm.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == btn_confirm.getId()) {

            count++;

            if(personnel_textheader.getText().toString().equalsIgnoreCase("PERSONEL EKLE")) {

                if (count == 1) {

                    personnelname = personnel_edittext.getText().toString();
                    personnel_text.setText("PERSONEL ŞİFRESİ");
                    personnel_edittext.setText("");
                    personnel_edittext.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    personnel_edittext.setHint("ŞİFRE");
                } else if (count == 2) {

                    personnelpassword = personnel_edittext.getText().toString();

                    if(validatepassword(personnelpassword)) {

                        personnel_edittext.setHint("GÜVENLİK CEVABI");
                        personnel_text.setText("PERSONEL GÜVENLİK CEVABI");
                        personnel_edittext.setText("");
                    }
                    else {

                        count--;
                        AlertDialog.Builder alert = new AlertDialog.Builder(context);
                        alert.setTitle("PAROLA ALMA");
                        alert.setMessage("EN AZ 1 BÜYÜK HARF \n EN AZ 1 KÜÇÜK HARF \n EN AZ 1 RAKAM \n EN AZ 9 KARAKTER \n RAKAM VEYA HARF ile bitmeli");
                        alert.show();
                    }

                } else if (count == 3) {

                    personnel_text.setText("PERSONEL E-MAİL ADRESİ");
                    personnel_edittext.setText("");
                    //personnel_edittext.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                    personnel_edittext.setHint("E-MAİL ADDRESS");
                    btn_confirm.setText("PERSONEL KAYDINI TAMAMLA");

                    personnelsecurityresult = personnel_edittext.getText().toString();

                } else if (count > 4) {

                    personnelmailaddress = personnel_edittext.getText().toString();

                    if (validatemail(personnelmailaddress)) {

                        Users user = new Users(personnelname, personnelpassword, personnelsecurityresult, personnelmailaddress);
                        SQLLiteHelperr database = new SQLLiteHelperr(context);
                        database.TempPersonelEkle(user);
                        AlertDialog.Builder alert = new AlertDialog.Builder(context);
                        alert.setTitle("KAYIT OLMA İSTEĞİ");
                        alert.setMessage("HESABINIZ ONAYLANMA SÜRECİNDE");
                        alert.setCancelable(false);
                        alert.setPositiveButton("TAMAM", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Intent intent = new Intent(context, QueryPanel.class);
                                startActivity(intent);
                            }
                        });
                        alert.show();
                    }
                }
                else {

                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
                    alert.setTitle("MAİL ALMA");
                    alert.setMessage("FARKLI BİR MAİL ADRESİ GİRİN");
                    alert.show();
                }
            }
            else if(personnel_textheader.getText().toString().equalsIgnoreCase("PERSONEL SİL")) {

                SQLLiteHelperr database = new SQLLiteHelperr(context);
                database.deletePersonel(personnel_edittext.getText().toString());
                Toast.makeText(context,"PERSONEL SİLİNDİ",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
            }
        }
        else if(v.getId() == btn_cancel.getId()) {

            Intent intent = new Intent(context,QueryPanel.class);
            startActivity(intent);
        }
    }

    public boolean validatepassword(String personnelpassword) {

        String code = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}[a-zA-Z0-9]$";

        Pattern pattern = Pattern.compile(code);

        boolean confirm = pattern.matcher(personnelpassword).matches();

        return confirm;
    }


    public boolean validatemail(String personnelmailaddress) {

        Pattern pattern = Patterns.EMAIL_ADDRESS;

        boolean confirm;

        if(pattern.matcher(personnelmailaddress).matches()) {

            confirm = true;
        }
        else {

            confirm = false;
        }

        return confirm;
    }
}

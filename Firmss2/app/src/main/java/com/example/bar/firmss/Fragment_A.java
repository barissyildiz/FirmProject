package com.example.bar.firmss;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.santalu.maskedittext.MaskEditText;

import java.util.regex.Pattern;

public class Fragment_A extends Fragment implements View.OnClickListener {

  Context context;
  Button save,clear;
  EditText firmname,firmaddress,firmmail,firmcountry,firmcity,firmilçe,firmpostakodu;
  MaskEditText firmtel;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.fragment_firms,container,false);

    save = view.findViewById(R.id.btn_firm_kaydet);
    clear = view.findViewById(R.id.btn_firm_clear);
    firmname = view.findViewById(R.id.text_firm);
    firmaddress = view.findViewById(R.id.text_address);
    firmtel = view.findViewById(R.id.text_tel);
    firmmail = view.findViewById(R.id.text_mail);
    firmcountry = view.findViewById(R.id.text_country);
    firmcity = view.findViewById(R.id.text_city);
    firmilçe = view.findViewById(R.id.text_ilçe);
    firmpostakodu = view.findViewById(R.id.text_postakodu);

    Listeners();

    context = container.getContext();

    return view;
  }

  public void Listeners() {

    save.setOnClickListener(this);
    clear.setOnClickListener(this);

  }

  @Override
  public void onClick(View v) {

    if(v.getId() == R.id.btn_firm_kaydet) {

      if(validatemail(firmmail.getText().toString())) {

        FİRMS firm1 = new FİRMS();
        firm1.setName(firmname.getText().toString());
        firm1.setAddress(firmaddress.getText().toString());
        firm1.setTel(firmtel.getText().toString());
        firm1.setMail(firmmail.getText().toString());
        firm1.setCountry(firmcountry.getText().toString());
        firm1.setCity(firmcity.getText().toString());
        firm1.setIlçe(firmilçe.getText().toString());
        firm1.setPostakodu(Integer.parseInt(firmpostakodu.getText().toString()));
        new SQLLiteHelperr(getActivity().getApplicationContext()).FirmEkle(firm1);
        Toast.makeText(context, "FİRMA KAYDI YAPILDI", Toast.LENGTH_SHORT).show();

      }
      else {

        Toast.makeText(context,"İNVALİD MAİL ADDRESS",Toast.LENGTH_LONG).show();
      }
    }

    else if(v.getId() == R.id.btn_firm_clear) {

      firmname.setText("");
      firmaddress.setText("");
      firmtel.setText("");
      firmmail.setText("");
      firmcountry.setText("");
      firmcity.setText("");
      firmilçe.setText("");
      firmpostakodu.setText("");
    }
  }

  public boolean validatemail(String firmmailaddress) {

    Pattern pattern = Patterns.EMAIL_ADDRESS;

    return pattern.matcher(firmmailaddress).matches();
  }
}
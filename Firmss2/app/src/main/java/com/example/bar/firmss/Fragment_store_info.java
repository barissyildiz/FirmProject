package com.example.bar.firmss;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class Fragment_store_info extends Fragment implements AdapterView.OnClickListener {

  Context context;
  TextView textstore;
  TextInputLayout textstoreinfo;
  Spinner spinner;
  Button btn_update,btn_delete;
  ArrayAdapter adapter;
  String storename;

  @Override
  public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.listview_item_store,container,false);

    context = container.getContext();

    identifiers(view);

    listeners();

    SQLLiteHelperr database = new SQLLiteHelperr(context);

    //
    List<Store> liste = database.StoreListele();

    //ürün seçildikten sonra açılan sayfadan ürün bilgilerinin görünütlenmesini sağlar
    getStroreInfo();

    return view;

  }

  public void identifiers(View view) {

    textstore = view.findViewById(R.id.text_store_listview_item_info);
    textstoreinfo = view.findViewById(R.id.update_text_listview_item_info);
    spinner = view.findViewById(R.id.store_listview_item_spinner);
    btn_update = view.findViewById(R.id.update_store);
    btn_delete = view.findViewById(R.id.delete_store);

    SQLLiteHelperr database = new SQLLiteHelperr(context);

    String[] liste = database.StoreColumnList();
    adapter = new ArrayAdapter(context,android.R.layout.simple_list_item_1,liste);
    spinner.setAdapter(adapter);

  }

  public void listeners() {

    btn_update.setOnClickListener(this);
    btn_delete.setOnClickListener(this);
  }

  public void getStroreInfo() {

    Bundle arguments = getArguments();

    String store_info = arguments.getString("store_info","0");

    storename = arguments.getString("store_name","0");

    textstore.setText(store_info);
  }

  @Override
  public void onClick(View v) {

    if(v.getId() == btn_update.getId()) {

      SQLLiteHelperr database = new SQLLiteHelperr(context);

      Boolean available = database.updateStoreInfo(spinner.getSelectedItem().toString(), textstoreinfo.getEditText().getText().toString(), storename);

      if (available == false) {

        Toast.makeText(context, "ÜRÜN GÜNCELLEMESİ YAPILDI", Toast.LENGTH_SHORT).show();

      } else {

        Toast.makeText(context,"AYNI İSİMDE STORE MEVCUT",Toast.LENGTH_SHORT).show();
      }
    }
    else if(v.getId() == btn_delete.getId()) {

      SQLLiteHelperr database = new SQLLiteHelperr(context);

      database.deleteStore(storename);

      Toast.makeText(context,"ÜRÜN SİLİNDİ",Toast.LENGTH_SHORT).show();
    }
  }
}
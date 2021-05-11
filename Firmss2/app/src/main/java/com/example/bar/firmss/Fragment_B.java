package com.example.bar.firmss;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;


public class Fragment_B extends Fragment implements View.OnClickListener {

    Context context;
    Spinner spinner;
    ArrayAdapter<String> adapter;
    AppCompatButton storesave,storeclear;
    TextInputLayout storename,storecount,storevalue,storealışvalue;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_store,container,false);

        context = container.getContext();

        adapter = new ArrayAdapter<>(context,R.layout.list_task,getResources().getStringArray(R.array.spinnerlist));
        spinner = view.findViewById(R.id.store_spinner);
        spinner.setAdapter(adapter);

        storesave = view.findViewById(R.id.store_save);
        storeclear = view.findViewById(R.id.store_clear);
        storename = view.findViewById(R.id.store_name);
        storecount = view.findViewById(R.id.store_count);
        storevalue = view.findViewById(R.id.store_value);
        storealışvalue = view.findViewById(R.id.store_alışvalue);

        listeners();

        return view;
    }

    public void listeners() {

        storesave.setOnClickListener(this);
        storeclear.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == storesave.getId()) {

            Store store = new Store();
            store.setStorename(storename.getEditText().getText().toString());
            store.setStoretype(spinner.getSelectedItem().toString());
            store.setStoremiktarı(storecount.getEditText().getText().toString());
            store.setStorealışfiyatı(storealışvalue.getEditText().getText().toString());
            store.setStorefiyatı(storevalue.getEditText().getText().toString());
            Boolean available = new SQLLiteHelperr(context).StoreEkle(store);

            if (available == false) {

                Toast.makeText(context, "STOK KAYIT YAPILDI", Toast.LENGTH_SHORT).show();

            }

            else {

                Toast.makeText(context, "AYNI İSİMDE ÜRÜN KAYDI MEVCUT", Toast.LENGTH_SHORT).show();
            }
        }
        else if(v.getId() == storeclear.getId()) {

            storename.getEditText().setText("");
            storecount.getEditText().setText("");
            storevalue.getEditText().setText("");
            storealışvalue.getEditText().setText("");
        }
    }
}

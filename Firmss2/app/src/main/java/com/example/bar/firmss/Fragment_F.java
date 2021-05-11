package com.example.bar.firmss;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Fragment_F extends Fragment implements CompoundButton.OnCheckedChangeListener,AdapterView.OnItemClickListener {

    Context context;
    ListView listView;
    CheckBox satılanürünler,alınanürünler;
    Bundle bundle;
    TextView sellorbuy;
    String tempfirmname;
    ArrayList<Integer> satışoralışfiyatılist;
    int stokecode = 120;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_f,container,false);

        context = container.getContext();

        identifiers(view);

        listeners();

        return view;

    }

    public void identifiers(View view) {

        SQLLiteHelperr database = new SQLLiteHelperr(context);
        listView = view.findViewById(R.id.listview_sellfirms);
        satılanürünler = view.findViewById(R.id.checkbox_satılan_ürünler);
        alınanürünler = view.findViewById(R.id.checkbox_alınan_ürünler);
        sellorbuy = view.findViewById(R.id.textview_sell_or_buy);


        Bundle bundle = getArguments();
        tempfirmname = bundle.getString("string");

        List<FİRMS> arrayList = new ArrayList<>();
/*
        for(int i=0;i<bundle.getStringArrayList("StringArray").size();i++) {

            FİRMS firms = new FİRMS();
            firms.setStorename(bundle.getStringArrayList("StringArray").get(i));
            firms.setStoremiktar(bundle.getIntegerArrayList("IntegerArray").get(i));
            arrayList.add(firms);

        }
*/
        arrayList = database.getSatılanorAlınanFirmInfo(bundle.getString("string"),stokecode);

        if(arrayList.isEmpty()) {

            stokecode = 300;
        }

        arrayList = database.getSatılanorAlınanFirmInfo(bundle.getString("string"),stokecode);
        if(arrayList.isEmpty()) {

            sellorbuy.setText("FİRMADAN ALINAN YADA SATILAN ÜRÜN BULUNMAMAKTADIR");
            satılanürünler.setVisibility(View.INVISIBLE);
            alınanürünler.setVisibility(View.INVISIBLE);
        }
    }

    public void listeners() {

        satılanürünler.setOnCheckedChangeListener(this);
        alınanürünler.setOnCheckedChangeListener(this);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        if(satılanürünler.getId() == buttonView.getId()) {

            if(isChecked) {

                alınanürünler.setChecked(false);

                stokecode = 120;

                satışoralışfiyatılist = new ArrayList<>();
                sellorbuy.setText("FİRMAYA SATILAN STOKLAR");
                SQLLiteHelperr database = new SQLLiteHelperr(context);
                List<FİRMS> arrayList;
                arrayList = database.getSatılanorAlınanFirmInfo(tempfirmname,stokecode);
                CustomAdapter3 listviewadapter = new CustomAdapter3(context,arrayList);
                listView.setAdapter(listviewadapter);
                int size = arrayList.size();
                for(int i=0;i<size;i++) {

                    satışoralışfiyatılist.add(arrayList.get(i).getStore_sell_or_buy_satışfiyatı() * (arrayList.get(i).getStoremiktar()));
                }
            } }

        else if(alınanürünler.getId() == buttonView.getId()) {

            if(isChecked) {

                satılanürünler.setChecked(false);

                stokecode = 300;

                satışoralışfiyatılist = new ArrayList<>();
                sellorbuy.setText("FİRMADAN ALINAN STOKLAR");
                SQLLiteHelperr database = new SQLLiteHelperr(context);
                List<FİRMS> arrayList;
                arrayList = database.getSatılanorAlınanFirmInfo(tempfirmname,stokecode);
                CustomAdapter3 listviewadapter = new CustomAdapter3(context,arrayList);
                listView.setAdapter(listviewadapter);
                int size = arrayList.size();
                for(int i=0;i<size;i++) {

                    satışoralışfiyatılist.add(arrayList.get(i).getStore_sell_or_buy_alışfiyatı() * (arrayList.get(i).getStoremiktar()));
                } } } }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Toast.makeText(context,"GERÇEKLEŞTİRİLEN İŞLEM TUTARI :"+satışoralışfiyatılist.get(position) + "TL",Toast.LENGTH_SHORT).show();
    }
}

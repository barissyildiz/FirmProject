package com.example.bar.firmss;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Hashtable;

public class Fragment_E extends Fragment implements TextWatcher,AdapterView.OnItemClickListener {

  Context context;
  EditText editText_filter_money;
  Spinner spinner_money;
  ArrayAdapter<String> adapterspinner;
  ListView listView;
  int index;
  SQLLiteHelperr database;
  ArrayList<Hashtable> filter;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.fragment_listview_money,container,false);

    context = container.getContext();

    identifiers(view);

    listeners();

    return view;
  }

  public void identifiers(View view) {

    editText_filter_money = view.findViewById(R.id.edittext_filter_money);
    spinner_money = view.findViewById(R.id.spinner_money);

    ArrayList<String> list = new ArrayList<>();
    list.add("firma adı");
    list.add("borçlu olan firmalar");
    list.add("borçlu olunan firmalar");
    adapterspinner = new ArrayAdapter<>(context,android.R.layout.simple_list_item_1,list);
    spinner_money.setAdapter(adapterspinner);

    listView = view.findViewById(R.id.listview_money);

    database = new SQLLiteHelperr(context);
  }

  public void listeners() {

    editText_filter_money.addTextChangedListener(this);
    listView.setOnItemClickListener(this);

    spinner_money.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        index = spinner_money.getSelectedItemPosition();
        SQLLiteHelperr database = new SQLLiteHelperr(context);
        CustomAdapter2 listviewadapter = new CustomAdapter2(context,database.CustomAdapterHashTable(index));
        filter = new ArrayList<>();
        filter = database.CustomAdapterHashTable(index);
        listView.setAdapter(listviewadapter);
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });
  }

  @Override
  public void beforeTextChanged(CharSequence s, int start, int count, int after) {

  }

  @Override
  public void onTextChanged(CharSequence s, int start, int before, int count) {

    ArrayList<Hashtable> temptable = new ArrayList<>();

    for(int i=0;i<filter.size();i++) {

      if(filter.get(i).get("firm_name").toString().startsWith(s.toString())) {

        Hashtable<String,String> hashtable2 = new Hashtable();
        hashtable2.put("firm_name",filter.get(i).get("firm_name").toString());
        hashtable2.put("money_value",filter.get(i).get("money_value").toString());
        temptable.add(hashtable2);
      }
    }

    CustomAdapter2 listviewadapter = new CustomAdapter2(context,temptable);
    listView.setAdapter(listviewadapter);
  }

  @Override
  public void afterTextChanged(Editable s) {

  }

  @Override
  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    FragmentManager fragmentManager = getFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    Fragment_F fragment_f = new Fragment_F();
    fragmentTransaction.replace(R.id.fragment,fragment_f);
    fragmentTransaction.commit();

    String tempfirmname = filter.get(position).get("firm_name").toString();
    Bundle bundle2 = new Bundle();
    bundle2.putString("string",tempfirmname);
    fragment_f.setArguments(bundle2);
/*
        Bundle bundle = new Bundle();
        SQLLiteHelperr database = new SQLLiteHelperr(context);
        List<FİRMS> list = database.getSatılanFirmInfo(tempfirmname);
        ArrayList<String> storenamelist = new ArrayList<>();
        ArrayList<Integer> storemiktarlist = new ArrayList<>();
        for(int i=0;i<list.size();i++) {

            storenamelist.add(list.get(i).getStorename());
            storemiktarlist.add(list.get(i).getStoremiktar());
        }
        bundle.putStringArrayList("StringArray",storenamelist);
        bundle.putIntegerArrayList("IntegerArray",storemiktarlist);
        fragment_f.setArguments(bundle);
        */
  }
}

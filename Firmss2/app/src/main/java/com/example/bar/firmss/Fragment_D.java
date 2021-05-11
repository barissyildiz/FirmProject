package com.example.bar.firmss;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
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
import java.util.List;

public class Fragment_D extends Fragment implements AdapterView.OnItemSelectedListener,AdapterView.OnItemClickListener,TextWatcher {


  Context context;
  EditText text_store;
  Spinner spinner;
  ArrayList<String> storenames;
  ArrayAdapter<String> adapterlistview;
  ArrayAdapter<String> adapterspinner;
  List<Store> store;
  ListView listView;
  int index;
  String item;

  @Override
  public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.fragment_listview_store,container,false);

    context = container.getContext();

    text_store = view.findViewById(R.id.text_stroke_info);
    spinner = view.findViewById(R.id.store_spinner2);
    listView = view.findViewById(R.id.stroke_listview);

    SQLLiteHelperr database = new SQLLiteHelperr(context);

    String[] list = database.StoreColumnList();

    adapterspinner = new ArrayAdapter<>(context,android.R.layout.simple_list_item_1,list);
    spinner.setAdapter(adapterspinner);

    store = database.StoreListele();

    storenames = database.StoreName();

    adapterlistview = new ArrayAdapter<>(context,android.R.layout.simple_list_item_1,storenames);

    listView.setAdapter(adapterlistview);

    spinner.setOnItemSelectedListener(this);
    text_store.addTextChangedListener(this);
    listView.setOnItemClickListener(this);

    return view;
  }

  @Override
  public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    item = spinner.getSelectedItem().toString();

    index = spinner.getSelectedItemPosition();

    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("USERS",Context.MODE_PRIVATE);

    SharedPreferences.Editor editor = sharedPreferences.edit();

    editor.putString("item",item);

    editor.commit();
  }

  @Override
  public void onNothingSelected(AdapterView<?> parent) {


  }


  @Override
  public void beforeTextChanged(CharSequence s, int start, int count, int after) {


  }

  @Override
  public void onTextChanged(CharSequence s, int start, int before, int count) {

    SQLLiteHelperr database = new SQLLiteHelperr(context);

    List<String> liste = new ArrayList<>();

    liste = database.getFilter(s.toString());

    adapterlistview = new ArrayAdapter<>(context,android.R.layout.simple_list_item_1,liste);

    listView.setAdapter(adapterlistview);
  }

  @Override
  public void afterTextChanged(Editable s) {


  }

  @Override
  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    Fragment_store_info fragment_store_info = new Fragment_store_info();

    Bundle arguments = new Bundle();

    arguments.putString("store_info",store.get(position).getInfo());

    arguments.putString("store_name",store.get(position).getStorename());

    fragment_store_info.setArguments(arguments);

    FragmentManager fragmentManager = getFragmentManager();

    android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

    fragmentTransaction.replace(R.id.fragment,fragment_store_info);

    fragmentTransaction.commit();

  }
}

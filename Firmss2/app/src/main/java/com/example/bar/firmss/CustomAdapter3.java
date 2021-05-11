package com.example.bar.firmss;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter3 extends BaseAdapter {

  Context context;
  List<FİRMS> list;

  public CustomAdapter3(Context context,List<FİRMS> list) {
    this.context = context;
    this.list = list;
  }

  @Override
  public int getCount() {

    return list.size();
  }

  @Override
  public Object getItem(int position) {

    return position;
  }

  @Override
  public long getItemId(int position) {

    return position;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {

    LayoutInflater inflater = LayoutInflater.from(context);

    View view = inflater.inflate(R.layout.listview_sell_firms_items,null);

    TextView storename = view.findViewById(R.id.textview_sell_firms_stoke_name);
    TextView storemiktarı = view.findViewById(R.id.textview_sell_firms_stoke_miktar);
    TextView storeselltime = view.findViewById(R.id.textview_sell_store_time);

    storename.setText(list.get(position).getStorename());
    storemiktarı.setText(String.valueOf(list.get(position).getStoremiktar())+" ADET ");
    storeselltime.setText(list.get(position).getSellstoredate());

    return view;
  }
}
package com.example.bar.firmss;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Hashtable;

public class CustomAdapter2 extends BaseAdapter {

  Context context;
  ArrayList<Hashtable> list;

  public CustomAdapter2(Context context, ArrayList<Hashtable> list) {

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

    View view = inflater.inflate(R.layout.customlist_task2, null);

    TextView firmname = view.findViewById(R.id.textview_customlist_task2_firmname);
    TextView money = view.findViewById(R.id.textview_customlist_task2_money);
    ImageView image = view.findViewById(R.id.image_plus_or_minus);

    firmname.setText(list.get(position).get("firm_name").toString());
    money.setText(list.get(position).get("money_value").toString());
    int value = Integer.parseInt(list.get(position).get("money_value").toString());

    if (value >= 0) {

      image.setBackground(context.getResources().getDrawable(R.mipmap.greenplus));

    } else if(value < 0){

      image.setBackground(context.getResources().getDrawable(R.mipmap.redminus));
      money.setText(String.valueOf(value * (-1)));

    }

    return view;
  }
}

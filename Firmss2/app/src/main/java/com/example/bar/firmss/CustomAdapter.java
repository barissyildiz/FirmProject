package com.example.bar.firmss;

import android.content.Context;

import android.view.LayoutInflater;

import android.view.View;

import android.view.ViewGroup;

import android.widget.BaseAdapter;

import android.widget.TextView;


import java.util.List;


public class CustomAdapter extends BaseAdapter {

  
Context context;
  
List<FİRMS> firms;

 
 
public CustomAdapter(Context context, List<FİRMS> firms) {

   
 this.context = context;
    
this.firms = firms;
 

 }

  
@Override
 
 public int getCount() {

    
return firms.size();
 
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

    LayoutInflater layoutInflater = LayoutInflater.from(context);
    View view = layoutInflater.inflate(R.layout.customlist_task, null);

    TextView firmname = view.findViewById(R.id.listview_item_firmname);
    TextView firmtel = view.findViewById(R.id.listview_item_firmtel);
    TextView firmmail = view.findViewById(R.id.listview_item_firmmail);

    FİRMS firm = firms.get(position);

    firmname.setText(firm.getName());
    firmtel.setText(firm.getTel());
    firmmail.setText(firm.getMail());

    return view;

  }
}
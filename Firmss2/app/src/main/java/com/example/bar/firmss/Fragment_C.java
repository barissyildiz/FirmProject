package com.example.bar.firmss;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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

public class Fragment_C extends Fragment implements AdapterView.OnItemClickListener,AdapterView.OnItemSelectedListener {

  Context context;
  ListView listView;
  Spinner firmspinner;
  EditText firmcall;
  ArrayList<String> firmname;
  List<FİRMS> firms;
  int index;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.fragment_listview,container,false);

    context = container.getContext();
    listView = view.findViewById(R.id.listview_item);
    firmspinner = view.findViewById(R.id.firm_spinner);
    firmcall = view.findViewById(R.id.firm_call);

    SQLLiteHelperr database = new SQLLiteHelperr(context);
    firms = new ArrayList<>();
    firms = database.FirmaListele();
    CustomAdapter adapter = new CustomAdapter(context,firms);

    firmname = new ArrayList<>(firms.size());

    for(int i=0;i<firms.size();i++) {

      firmname.add(firms.get(i).getName());
    }

        /*List<String> firmname = new ArrayList<>();
        for(int i=0;i<firms.size();i++) {

            firmname.add(firms.get(i).getName());
        }
        adapter = new ArrayAdapter<>(context,android.R.layout.simple_list_item_1,firmname);
        */

    listView.setAdapter(adapter);
    ArrayAdapter<String> spinneradapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.spinnerlist2));
    firmspinner.setAdapter(spinneradapter);

    listeners();

    return view;
  }

  public void listeners() {

    listView.setOnItemClickListener(this);
    firmspinner.setOnItemSelectedListener(this);

    firmcall.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {


      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {

        ArrayList<FİRMS> tempfirms = new ArrayList<>();

        if (index == 0) {

          int y = 0;

          for (int i = 0; i < firmname.size(); i++) {

            if (firmname.get(i).toUpperCase().startsWith(s.toString().toUpperCase())) {

              tempfirms.add(new FİRMS());
              tempfirms.get(y).setName(firms.get(i).getName());
              tempfirms.get(y).setTel(firms.get(i).getTel());
              tempfirms.get(y).setMail(firms.get(i).getMail());

              y++;
            }
          }

          if(!tempfirms.isEmpty()) {

            listView.setAdapter(new CustomAdapter(context, tempfirms));

          }
        }

        else if(index == 1) {

          int y = 0;

          for(int i=0;i<firms.size();i++) {

            if(firms.get(i).getTel().startsWith(s.toString())) {

              tempfirms.add(new FİRMS());
              tempfirms.get(y).setName(firms.get(i).getName());
              tempfirms.get(y).setTel(firms.get(i).getTel());
              tempfirms.get(y).setMail(firms.get(i).getMail());

              y++;
            }
          }

          if(!tempfirms.isEmpty()) {

            listView.setAdapter(new CustomAdapter(context, tempfirms));

          }
        }
        else if(index == 2) {

          int y = 0;

          for(int i=0;i<firms.size();i++) {

            if(firms.get(i).getCity().toUpperCase().startsWith(s.toString().toUpperCase())) {

              tempfirms.add(new FİRMS());
              tempfirms.get(y).setName(firms.get(i).getName());
              tempfirms.get(y).setTel(firms.get(i).getTel());
              tempfirms.get(y).setMail(firms.get(i).getMail());

              y++;
            }
          }

          if(!tempfirms.isEmpty()) {

            listView.setAdapter(new CustomAdapter(context,tempfirms));
          }
        }
      }

      @Override
      public void afterTextChanged(Editable s) {


      }
    });
  }

  @Override
  public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    if(parent.getId() == firmspinner.getId()) {

      index = firmspinner.getSelectedItemPosition();
    }
  }

  @Override
  public void onNothingSelected(AdapterView<?> parent) {


  }

  @Override
  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    if(parent.getId() == listView.getId()) {

      Fragment fragment_firm_info = new Fragment_firm_info();
      Bundle bundle = new Bundle();
      bundle.putString("id",firms.get(position).firminfo());
      fragment_firm_info.setArguments(bundle);
      FragmentManager fragment = getFragmentManager();
      FragmentTransaction fragmentTransaction = fragment.beginTransaction();
      fragmentTransaction.replace(R.id.fragment,fragment_firm_info);
      fragmentTransaction.commit();

      SharedPreferences sharedPreferences = context.getSharedPreferences("USERS",Context.MODE_PRIVATE);
      SharedPreferences.Editor editor = sharedPreferences.edit();
      editor.putInt("firm_id",position+1);
      editor.putString("firm_name",firms.get(position).getName());
      editor.putString("firm_tel",firms.get(position).getTel());
      editor.putString("firm_mail",firms.get(position).getMail());
      editor.commit();
    }
  }
}
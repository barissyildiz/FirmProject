package com.example.bar.firmss;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter4 extends BaseAdapter {

    Context context;
    ArrayList<String> list;
    SQLLiteHelperr database = new SQLLiteHelperr(context);
    ArrayList<String> checkBoxlistpositions = new ArrayList<>();

    public CustomAdapter4(Context context, ArrayList<String> list) {

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
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view = LayoutInflater.from(context).inflate(R.layout.save_personnel_task, null);

        final TextView personnelname = view.findViewById(R.id.listview_item_personnel_name);

        personnelname.setText(list.get(position));

        CheckBox personellcheckbox = view.findViewById(R.id.listview_item_personnel_checkbox);

        personellcheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked) {

                    personnelname.setTag("b");
                    personnelname.setTag(R.string.tag,personnelname.getText().toString());
                }
                else {

                    personnelname.setTag("a");

                }
            }
        });
        return view;
    }

}
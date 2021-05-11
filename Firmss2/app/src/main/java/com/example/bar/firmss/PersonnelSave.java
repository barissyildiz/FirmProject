package com.example.bar.firmss;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class PersonnelSave extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemClickListener {

    Context context;
    Button btn_save;
    ListView listView;
    ArrayList<String> list;
    ArrayList<CheckBox> checkBoxeslist;
    CustomAdapter4 customAdapter4;
    SQLLiteHelperr database;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.savepersonnel);

        identifiers();
        listeners();

        context = this;
        checkBoxeslist = new ArrayList<>();

        SQLLiteHelperr database = new SQLLiteHelperr(context);
        list = database.TempPersonelNameList();

        int childCount = list.size();

        if(childCount != 0) {

            customAdapter4 = new CustomAdapter4(context, list);
            listView.setAdapter(customAdapter4);
        }
    }

    public void identifiers() {

        btn_save = findViewById(R.id.btn_personnel_save);
        listView = findViewById(R.id.personnel_listview);
        list = new ArrayList<>();
        database = new SQLLiteHelperr(context);
    }

    public void listeners() {

        btn_save.setOnClickListener(this);
        listView.setOnItemClickListener(this);

    }

    @Override
    public void onClick(View v) {

        int size = listView.getChildCount();

        for(int i=0;i<size;i++) {

            if(String.valueOf(listView.getChildAt(i).findViewById(R.id.listview_item_personnel_name).getTag()).equalsIgnoreCase("b")) {

                list.remove(i);
                database.TempPersonelDelete(listView.getChildAt(i).findViewById(R.id.listview_item_personnel_name).getTag(R.string.tag).toString());
            }
        }

        listView.setAdapter(new CustomAdapter4(context,list));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}

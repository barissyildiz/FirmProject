package com.example.bar.firmss;

import android.app.Fragment;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class Fragment_firm_info extends Fragment implements AdapterView.OnItemSelectedListener,View.OnClickListener {

    Context context;
    TextView textfirminfo;
    Spinner spinnerupdate,spinnerbuy,spinnersell;
    ArrayList<String> arrayList;
    ArrayAdapter<String> adapterupdate,adaptersellorbuy;
    Button update,delete,buystoke,sellstoke;
    EditText editTextupdate,editTextbuymiktar,editTextsellmiktar;
    FloatingActionButton floatingActionButtonPhone,floatingActionButtonEmail;
    String item,tel,mail;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.listview_item_fragment,null,false);

        context = container.getContext();

        identifiers(view);

        getAdapterSpinner();
        SharedPreferences sharedPreferences = context.getSharedPreferences("USERS",Context.MODE_PRIVATE);
        tel = sharedPreferences.getString("firm_tel","0");
        mail = sharedPreferences.getString("firm_mail","0");

        floatingActionButtonPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_DIAL);

                intent.setData(Uri.parse("tel:"+tel));

                startActivity(intent);
            }
        });

        floatingActionButtonEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean state;
                Intent intent2 = new Intent();
                intent2.setAction(ConnectivityManager.CONNECTIVITY_ACTION);
                state = intent2.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);

                if (state == false) {

                    Toast.makeText(context,"MA??L G??NDERMEK ??????N ??NTERNET BA??LANTISI GEREKL??",Toast.LENGTH_SHORT).show();

                } else {

                    SharedPreferences sharedPreferences = context.getSharedPreferences("USERS", Context.MODE_PRIVATE);

                    Intent intent = new Intent(Intent.ACTION_SEND);

                    intent.setType("messages/rfc822");

                    intent.putExtra(Intent.EXTRA_EMAIL, new String[]{mail});

                    intent.putExtra(Intent.EXTRA_SUBJECT, "ANDRO??D MA??L");

                    intent.putExtra(Intent.EXTRA_TEXT, "F??RMA");

                    startActivity(intent);

                }
            }
        });

        listeners();

        textfirminfo.setText(getArguments().getString("id"));

        return view;
    }

    public void identifiers(View view) {

        textfirminfo = view.findViewById(R.id.list_item_info);
        spinnerupdate = view.findViewById(R.id.firm_column_spinner);
        spinnerbuy = view.findViewById(R.id.spinner_store_buy);
        spinnersell = view.findViewById(R.id.spinner_store_sell);
        update = view.findViewById(R.id.btn_update);
        delete = view.findViewById(R.id.btn_delete);
        buystoke = view.findViewById(R.id.btn_buy);
        sellstoke = view.findViewById(R.id.btn_sell);
        editTextupdate = view.findViewById(R.id.update_value);
        editTextbuymiktar = view.findViewById(R.id.edittext_buy_miktar);
        editTextsellmiktar = view.findViewById(R.id.edittext_sell_miktar);
        floatingActionButtonPhone = view.findViewById(R.id.floating_action_button_phone);
        floatingActionButtonEmail = view.findViewById(R.id.floating_action_button_email);
    }

    public void listeners() {

        spinnerupdate.setOnItemSelectedListener(this);
        update.setOnClickListener(this);
        delete.setOnClickListener(this);
        buystoke.setOnClickListener(this);
        sellstoke.setOnClickListener(this);
    }

    public void getAdapterSpinner() {

        arrayList = new ArrayList<>();
        arrayList.add("firm_name");
        arrayList.add("firm_tel");
        arrayList.add("firm_mail");
        arrayList.add("firm_address");
        arrayList.add("firm_city");
        arrayList.add("firm_country");
        arrayList.add("firm_postakodu");
        arrayList.add("firm_il??e");

        adapterupdate = new ArrayAdapter<>(context,android.R.layout.simple_list_item_1,arrayList);
        spinnerupdate.setAdapter(adapterupdate);

        SQLLiteHelperr database = new SQLLiteHelperr(context);
        ArrayList<String> storename = database.StoreName();
        adaptersellorbuy = new ArrayAdapter<>(context,android.R.layout.simple_list_item_1,storename);
        spinnerbuy.setAdapter(adaptersellorbuy);
        spinnersell.setAdapter(adaptersellorbuy);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if(parent.getId() == spinnerupdate.getId()) {

            item = spinnerupdate.getSelectedItem().toString();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {


    }

    @Override
    public void onClick(View v) {

        if(v.getId() == update.getId()) {

                SQLLiteHelperr database = new SQLLiteHelperr(context);

                SharedPreferences sharedPreferences = context.getSharedPreferences("USERS",Context.MODE_PRIVATE);
                String name = sharedPreferences.getString("firm_name","0");

                database.updateFirmInfo(editTextupdate.getText().toString(),item,name);

                Toast.makeText(context,"G??NCELLEME YAPILDI",Toast.LENGTH_SHORT).show();

        }

        else if(v.getId() == delete.getId()) {

            SQLLiteHelperr database = new SQLLiteHelperr(context);

            SharedPreferences sharedPreferences = context.getSharedPreferences("USERS",Context.MODE_PRIVATE);
            String name = sharedPreferences.getString("firm_name","0");

            database.deleteFirm(name);

            Toast.makeText(context,"F??RMA S??L??ND??",Toast.LENGTH_SHORT).show();
        }

        else if(v.getId() == buystoke.getId()) {

            SharedPreferences sharedPreferences = context.getSharedPreferences("USERS",Context.MODE_PRIVATE);

            SQLLiteHelperr database = new SQLLiteHelperr(context);

            String sat??nal??nacakstoke = spinnerbuy.getSelectedItem().toString();
            String sat??nal??nanfirma = sharedPreferences.getString("firm_name","0");
            String sat??nal??nacakstokeal????fiyat?? = database.storefiyat(sat??nal??nacakstoke,"store_al????fiyat??");
            String sat??nal??nacakstokesat????fiyat?? = database.storefiyat(sat??nal??nacakstoke,"store_fiyat??");

            int miktar = Integer.parseInt(editTextbuymiktar.getText().toString());

            String firmmoney = database.buyStore(sat??nal??nanfirma,sat??nal??nacakstoke,miktar);

            Toast.makeText(context,"SATIN ALINAN ??R??NLER??N TOPLAM F??YATI "+firmmoney+"TL",Toast.LENGTH_SHORT).show();

            if(Integer.parseInt(firmmoney) > 20) {

                Intent intent = new Intent(context,MainActivity.class);
                intent.putExtra("firm",sat??nal??nanfirma);
                PendingIntent pendingIntent = PendingIntent.getActivity(context,1,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                NotificationCompat.Builder notificationCompat = new NotificationCompat.Builder(context);
                notificationCompat.setContentTitle("B??LD??R??M");
                notificationCompat.setContentText("????LEM M??KTARI " +firmmoney+ " M??KTARI SINIRINI GE??T??");
                notificationCompat.setWhen(System.currentTimeMillis());
                notificationCompat.setColor(Color.BLUE);
                notificationCompat.setSmallIcon(android.R.drawable.sym_def_app_icon);
                notificationCompat.setContentIntent(pendingIntent);
                notificationCompat.setTicker("B??LD??R??M GELD??");
                manager.notify(1,notificationCompat.build());
            }

            TimeZone.setDefault(TimeZone.getTimeZone("GMT+3"));
            Calendar calendar = Calendar.getInstance();
            Date date = calendar.getTime();
            String temp = null;
            if(date.getSeconds() < 10) {

                temp = "0"+String.valueOf(date.getSeconds());
            }
            else {

                temp = String.valueOf(date.getSeconds());
            }

            String nowdate = date.getHours() + ":" + date.getMinutes()+ ":" + temp;

            database.InsertSat??lanorAl??nanFirmInfo(sat??nal??nanfirma,sat??nal??nacakstoke,miktar,sat??nal??nacakstokeal????fiyat??,sat??nal??nacakstokesat????fiyat??,nowdate,300);
        }
        else if(v.getId() == sellstoke.getId()) {

            SharedPreferences sharedPreferences = context.getSharedPreferences("USERS",Context.MODE_PRIVATE);

            SQLLiteHelperr database = new SQLLiteHelperr(context);

            String sat??lacakstoke = spinnersell.getSelectedItem().toString();
            String sat??lanfirma = sharedPreferences.getString("firm_name","0");
            String sat??nal??nacakstokeal????fiyat?? = database.storefiyat(sat??lacakstoke,"store_al????fiyat??");
            String sat??nal??nacakstokesat????fiyat?? = database.storefiyat(sat??lacakstoke,"store_fiyat??");

            //F??RMALARIN ??R??NLER?? NE ZAMAN SATIN ALDI??I B??LG??S?? TUTULUR
            TimeZone.setDefault(TimeZone.getTimeZone("GMT+3"));
            TimeZone timeZone = TimeZone.getDefault();
            Locale trlocale= new Locale("tr","TR");
            Calendar calendar = Calendar.getInstance(trlocale);
            Date date = calendar.getTime();
            String seconds = null;
            String minutes = null;
            String hours = null;
            if(date.getSeconds() < 10) {

                seconds = "0"+String.valueOf(date.getSeconds());
            }
            else {

                seconds = String.valueOf(date.getSeconds());
            }

            if(date.getMinutes() < 10) {

                minutes = "0"+String.valueOf(date.getMinutes());
            }
            else {

                minutes = String.valueOf(date.getMinutes());
            }

            if(date.getHours() < 10) {

                hours = "0"+String.valueOf(date.getHours());
            }
            else {

                hours = String.valueOf(date.getHours());
            }

            String nowdate = hours + ":" + minutes+ ":" + seconds;

            int miktar = Integer.parseInt(editTextsellmiktar.getText().toString());

            String firmmoney = database.sellStore(sat??lanfirma,sat??lacakstoke,miktar);

            if(firmmoney.isEmpty()) {

                Toast.makeText(context,"DEPODA YETERL?? M??KTARDA ??R??N YOK",Toast.LENGTH_SHORT).show();
            }
            else {

                Toast.makeText(context, "SATILAN ??R??NLER??N TOPLAM F??YATI " + firmmoney + "TL", Toast.LENGTH_SHORT).show();

                if(Integer.parseInt(firmmoney) > 20) {

                    Intent intent = new Intent(context,MainActivity.class);
                    PendingIntent pendingIntent = PendingIntent.getActivity(context,1,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                    NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    NotificationCompat.Builder notificationCompat = new NotificationCompat.Builder(context);
                    notificationCompat.setContentTitle("B??LD??R??M");
                    notificationCompat.setContentText("????LEM M??KTARI " +firmmoney+ " M??KTARI SINIRINI GE??T??");
                    notificationCompat.setWhen(System.currentTimeMillis());
                    notificationCompat.setColor(Color.BLUE);
                    notificationCompat.setSmallIcon(android.R.drawable.sym_def_app_icon);
                    notificationCompat.setContentIntent(pendingIntent);
                    notificationCompat.setTicker("B??LD??R??M GELD??");
                    manager.notify(1,notificationCompat.build());
                }

                SQLLiteHelperr database2 = new SQLLiteHelperr(context);

                database2.InsertSat??lanorAl??nanFirmInfo(sat??lanfirma,sat??lacakstoke,miktar,sat??nal??nacakstokeal????fiyat??,sat??nal??nacakstokesat????fiyat??,nowdate,120);

            }
        }
    }}

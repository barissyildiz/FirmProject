package com.example.bar.firmss;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

public class SQLLiteHelperr extends SQLiteOpenHelper {

    Context context;
    SQLiteDatabase sqLiteDatabase;

    private static final String database = "FİRM.db";
    private static String pathname = "/data/data/com.example.bar.firmss/databases/";
    private static final int version = 1;
    private static final String table_personels = "users";
    private static final String user_id = "id";
    private static final String user_name = "username";
    private static final String user_mail = "usermail";
    private static final String user_security_question = "user_security_question";
    private static final String user_password = "password";
    private static final String create_user_table = "CREATE TABLE "
            + table_personels + " ("
            + user_id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + user_name + " TEXT, "
            + user_mail + " TEXT, "
            + user_security_question + " TEXT, "
            + user_password + " TEXT )";
    private static final String table_firms = "firms";
    private static final String firm_id = "firm_id";
    private static final String firm_name = "firm_name";
    private static final String firm_address = "firm_address";
    private static final String firm_tel = "firm_tel";
    private static final String firm_mail = "firm_mail";
    private static final String firm_country = "firm_country";
    private static final String firm_city = "firm_city";
    private static final String firm_ilçe = "firm_ilçe";
    private static final String firm_postakodu = "firm_postakodu";
    private static final String create_firm_table = "CREATE TABLE "
            + table_firms + " ("
            + firm_id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + firm_name + " TEXT, "
            + firm_address + " TEXT, "
            + firm_tel + " INTEGER, "
            + firm_mail + " TEXT, "
            + firm_country + " TEXT, "
            + firm_city + " TEXT, "
            + firm_ilçe + " TEXT, "
            + firm_postakodu + " INTEGER )";
    private static final String table_store = "store";
    private static final String store_id = "store_id";
    private static final String store_name = "store_name";
    private static final String store_type = "store_type";
    private static final String store_miktarı = "store_miktarı";
    private static final String store_fiyatı = "store_fiyatı";
    private static final String store_alışfiyatı = "store_alışfiyatı";
    private static final String create_store_table = "CREATE TABLE "
            + table_store + " ("
            + store_id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + store_name + " TEXT, "
            + store_type + " TEXT, "
            + store_miktarı + " INTEGER, "
            + store_fiyatı + " TEXT, "
            + store_alışfiyatı + " TEXT )";
    private static final String table_money = "money";
    private static final String money_id = "money_id";
    private static final String money_value = "money_value";
    private static final String create_money_table = "CREATE TABLE "
            + table_money + " ("
            + money_id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + firm_name + " TEXT, "
            + money_value + " INTEGER )";
    private static final String table_sell_firm = "sellfirm";
    private static final String sell_firm_id = "sell_firm_id";
    private static final String sell_firm_store_name = "sell_store_name";
    private static final String sell_store_time = "sell_store_time";
    private static final String sell_store_alış_fiyatı = "sell_store_alış_fiyatı";
    private static final String sell_store_satış_fiyatı = "sell_store_satış_fiyatı";
    private static final String sell_store_miktar = "sell_store_miktar";
    private static final String sell_store_code = "sell_store_code";
    private static final String create_sell_table = "CREATE TABLE "
            + table_sell_firm + " ("
            + sell_firm_id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + firm_name + " TEXT, "
            + sell_firm_store_name + " TEXT, "
            + sell_store_code + " INTEGER, "
            + sell_store_alış_fiyatı + " INTEGER, "
            + sell_store_satış_fiyatı + " INTEGER, "
            + sell_store_time + " DATE, "
            + sell_store_miktar + " INTEGER )";
    private static final String table_admin = "admin";
    private static final String user_save_id = "id";
    private static final String user_save_name = "username";
    private static final String user_save_mail = "usermail";
    private static final String user_save_security_question = "userresult";
    private static final String user_save_password = "userpassword";
    private static final String create_table_admin = "CREATE TABLE "
            + table_admin + " ("
            + user_save_id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + user_save_name + " TEXT, "
            + user_save_mail + " TEXT, "
            + user_save_security_question + " TEXT, "
            + user_save_password + " TEXT )";


    public SQLLiteHelperr(Context context) {

        super(context,database, null, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL(create_user_table);
        db.execSQL(create_firm_table);
        db.execSQL(create_store_table);
        db.execSQL(create_money_table);
        db.execSQL(create_sell_table);
        db.execSQL(create_table_admin);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


        db.execSQL("DROP TABLE İF EXISTS " + table_personels);
        this.onCreate(db);
        db.execSQL("DROP TABLE İF EXİSTS " + table_firms);
        this.onCreate(db);
        db.execSQL("DROP TABLE İF EXISTS " + table_store);
        this.onCreate(db);
        db.execSQL("DROP TABLE İF EXİSTS " + table_money);
        this.onCreate(db);
        db.execSQL("DROP TABLE İF EXİSTS " + table_sell_firm);
        this.onCreate(db);
        db.execSQL("DROP TABLE İF EXİSTS " + table_admin);
        this.onCreate(db);

    }

    public boolean checkdatabase() throws SQLException {

        SQLiteDatabase checkDb = null;
        boolean value = false;

        try {

            File file = new File(pathname+database);

            if (file.exists() && !file.isDirectory()) {

                checkDb = SQLiteDatabase.openDatabase(pathname + database, null, SQLiteDatabase.OPEN_READONLY);

            }

            if(checkDb != null) {

                value = false;
            }
            else {

                value = true;
            }

        }catch (SQLException e) {

            e.printStackTrace();
        }

        return value;

    }

    public void createdatabase() throws SQLException {

        boolean check = checkdatabase();

        if (check == false) {


        } else {

            copydatabase();
        }
    }


    public void copydatabase() throws SQLException {

        try {

            int length;
            InputStream stream = context.getAssets().open(database);
            byte[] buffer = new byte[1024];
            String path = pathname + database;
            OutputStream outputStream = new FileOutputStream(path);
            while ((length = stream.read(buffer)) > 0) {

                outputStream.write(buffer, 0, length);

            }
            outputStream.flush();
            outputStream.close();
            stream.close();

        } catch (Exception e) {

            e.printStackTrace();
        }
        }

    public void opendatabase() {

        String path = pathname+database;
        sqLiteDatabase = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
    }

    @Override
    public synchronized void close() {
        if(sqLiteDatabase != null)
            sqLiteDatabase.close();
        super.close();
    }

    public void PersonelEkle(Users user) {

        opendatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", user.getUserName());
        contentValues.put("password", user.getUserPassword());
        contentValues.put(user_security_question,user.getResult());
        contentValues.put(user_mail,user.getMail());
        sqLiteDatabase.insert("Users", null, contentValues);
        close();
    }

    public List<String>[] PersonelListele() {

        List<String> userlist = new ArrayList<>();
        List<String> passwordlist = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM Users", null);
        cursor.moveToFirst();
        do {
            userlist.add(cursor.getString(cursor.getColumnIndex("username")));
            passwordlist.add(cursor.getString(cursor.getColumnIndex("password")));
        } while (cursor.moveToNext());

        List<String>[] totallist = new List[2];
        totallist[0] = userlist;
        totallist[1] = passwordlist;

        return totallist;
    }

    public Hashtable<String, String> questionresultcontrol(String name,String result) {

        SQLiteDatabase database = this.getReadableDatabase();

        Hashtable<String,String> hashtable = new Hashtable<>();

        //Cursor cursor = database.rawQuery("SELECT "+user_password+","+user_mail+" FROM "+table_personels+ " WHERE "+user_name+" = " + "'" + name + "'" + " AND user_security_question  = "+result,null);

        Cursor cursor = database.rawQuery("SELECT "+user_password+","+user_mail+" FROM "+table_personels+ " WHERE "+user_name+" = ? "+" AND user_security_question  = ? ",new String[] {name,result});

        cursor.moveToFirst();

        if(cursor.getCount() != 0) {

            hashtable.put("password", cursor.getString(cursor.getColumnIndex(user_password)));
            hashtable.put("mail", cursor.getString(cursor.getColumnIndex(user_mail)));

        }

        return hashtable;
    }

    public void updatePersonelInfo(String name,String password,String value) {

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("password",value);
        database.update(table_personels,contentValues,user_name +"= ?",new String[] {name});
        database.close();
    }

    public void deletePersonel(String name) {

        opendatabase();
        sqLiteDatabase.delete(table_personels,user_name +" = ?",new String[]{name});
        close();
    }

    public void TempPersonelEkle(Users user) {

        opendatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(user_save_name,user.getUserName());
        contentValues.put(user_save_password,user.getUserPassword());
        contentValues.put(user_save_security_question,user.getResult());
        contentValues.put(user_save_mail,user.getMail());
        sqLiteDatabase.insert(table_admin,null,contentValues);
        close();
    }

    public ArrayList<String> TempPersonelNameList() {

        opendatabase();
        //Cursor cursor = database.query(table_admin, new String[]{user_save_name}, null, null, null, null, null);
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT "+user_save_name+ " FROM "+table_admin,null);
        cursor.moveToFirst();
        int size = cursor.getCount();
        ArrayList<String> temppersonnelnamelist = new ArrayList<>(size);

        if (size != 0) {

            do {

                temppersonnelnamelist.add(cursor.getString(cursor.getColumnIndex(user_save_name)));

            } while (cursor.moveToNext());
        }

        close();
        return temppersonnelnamelist;
    }

    public void TempPersonelDelete(String name) {

        opendatabase();
        Cursor cursor = sqLiteDatabase.query(table_admin,new String[] {user_save_name,user_save_password,user_save_security_question,user_save_mail},user_save_name+" = ?",new String[] {name},null,null,null);
        cursor.moveToFirst();
        Users user = new Users();
        user.setUserName(cursor.getString(cursor.getColumnIndex(user_save_name)));
        user.setUserPassword(cursor.getString(cursor.getColumnIndex(user_save_password)));
        user.setResult(cursor.getString(cursor.getColumnIndex(user_save_security_question)));
        user.setMail(cursor.getString(cursor.getColumnIndex(user_save_mail)));
        PersonelEkle(user);

        opendatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(user_save_name,name);
        //sqLiteDatabase.execSQL("DELETE FROM admin WHERE user_save_name = '"+name+"'");
        sqLiteDatabase.delete(table_admin,user_save_name+" = ?",new String[] {name});
        close();
    }

    public void FirmEkle(FİRMS firms) {

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("firm_name", firms.getName());
        contentValues.put("firm_address", firms.getAddress());
        contentValues.put("firm_tel", firms.getTel());
        contentValues.put("firm_mail", firms.getMail());
        contentValues.put("firm_country", firms.getCountry());
        contentValues.put("firm_city", firms.getCity());
        contentValues.put("firm_ilçe", firms.getIlçe());
        contentValues.put("firm_postakodu", firms.getPostakodu());
        database.insert(table_firms, null, contentValues);

        ContentValues contentValues2 = new ContentValues();
        contentValues2.put("firm_name", firms.getName());
        contentValues2.put("money_value", 0);
        database.insert(table_money, null, contentValues2);
        database.close();
    }

    public List<FİRMS> FirmaListele() {

        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT firm_name,firm_address,firm_mail,firm_tel,firm_city,firm_country,firm_ilçe,firm_postakodu FROM firms", null);
        Cursor cursor2 = database.rawQuery("SELECT money_value FROM " + table_money, null);
        int size = cursor.getCount();
        List<FİRMS> firmlist = new ArrayList(size);

        cursor.moveToFirst();
        cursor2.moveToFirst();

        for (int i = 0; i < size; i++) {

            FİRMS firms = new FİRMS();
            firms.setName(cursor.getString(cursor.getColumnIndex(firm_name)));
            firms.setAddress(cursor.getString(cursor.getColumnIndex(firm_address)));
            firms.setTel(cursor.getString(cursor.getColumnIndex(firm_tel)));
            firms.setMail(cursor.getString(cursor.getColumnIndex(firm_mail)));
            firms.setCity(cursor.getString(cursor.getColumnIndex(firm_city)));
            firms.setCountry(cursor.getString(cursor.getColumnIndex(firm_country)));
            firms.setIlçe(cursor.getString(cursor.getColumnIndex(firm_ilçe)));
            firms.setPostakodu(cursor.getInt(cursor.getColumnIndex(firm_postakodu)));
            firms.setMoney(cursor2.getInt(cursor2.getColumnIndex(money_value)));

            firmlist.add(firms);

            cursor.moveToNext();
            cursor2.moveToNext();
        }

        return firmlist;
    }

    public void updateFirmInfo(String text, String column, String name) {

        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(column,text);

        database.update(table_firms, contentValues, "firm_name = ?", new String[]{name});
        if (column.equals("firm_name")) {

            database.update(table_money, contentValues, "firm_name = ?", new String[]{name});
        }
        database.close();
    }

    public List<String> getFirmNameFilter(String info) {

        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM " + table_firms + " WHERE firm_name LIKE '%" + info + "%'", null);

        cursor.moveToFirst();

        int size = cursor.getCount();

        ArrayList<String> list = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {

            list.add(cursor.getString(cursor.getColumnIndex("firm_name")));

            cursor.moveToNext();
        }

        return list;
    }

    public void deleteFirm(String name) {

        SQLiteDatabase database = this.getWritableDatabase();

        //database.execSQL("DELETE FROM "+table_firms+" WHERE firm_id = "+id);
        database.delete(table_firms, "firm_name = ?", new String[]{name});
        database.delete(table_money, "firm_name = ?", new String[]{name});
        database.close();
    }

    public Boolean StoreEkle(Store store) {

        SQLiteDatabase database = this.getWritableDatabase();

        int size = this.StoreListele().size();

        Boolean available = false;

        for (int i = 0; i < size; i++) {

            if (this.StoreListele().get(i).getStorename().equalsIgnoreCase(store.getStorename())) {

                available = true;
            }
        }
        if (available == false) {

            ContentValues contentValues = new ContentValues();
            contentValues.put("store_name", store.getStorename());
            contentValues.put("store_type", store.getStoretype());
            contentValues.put("store_miktarı", store.getStoremiktarı());
            contentValues.put("store_alışfiyatı", store.getStorealışfiyatı());
            contentValues.put("store_fiyatı", store.getStorefiyatı());
            database.insert(table_store, null, contentValues);
            database.close();
        }

        return available;
    }

    public List<Store> StoreListele() {

        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM " + table_store, null);
        cursor.moveToFirst();
        int size = cursor.getCount();
        ArrayList<Store> list = new ArrayList<>(size);

        for (int y = 0; y < size; y++) {

            Store store = new Store();
            store.setStorename(cursor.getString(cursor.getColumnIndex(store_name)));
            store.setStoretype(cursor.getString(cursor.getColumnIndex(store_type)));
            store.setStoremiktarı(cursor.getString(cursor.getColumnIndex(store_miktarı)));
            store.setStorealışfiyatı(cursor.getString(cursor.getColumnIndex(store_alışfiyatı)));
            store.setStorefiyatı(cursor.getString(cursor.getColumnIndex(store_fiyatı)));
            list.add(store);

            cursor.moveToNext();
        }

        return list;
    }

    public ArrayList<String> StoreName() {

        List<Store> storelist = this.StoreListele();

        storelist = this.StoreListele();

        ArrayList<String> storenames = new ArrayList<>();

        for (int i = 0; i < storelist.size(); i++) {

            storenames.add(storelist.get(i).getStorename());
        }

        return storenames;
    }

    public String[] StoreColumnList() {

        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT store_name,store_type,store_miktarı,store_alışfiyatı,store_fiyatı FROM " + table_store, null);

        cursor.moveToFirst();

        String[] list = cursor.getColumnNames();

        return list;
    }

    public Boolean updateStoreInfo(String column, String text, String name) {

        SQLiteDatabase database = this.getWritableDatabase();

        int size = this.StoreListele().size();

        Boolean available = false;

        for (int i = 0; i < size; i++) {

            if (this.StoreListele().get(i).getStorename().equalsIgnoreCase(text)) {

                available = true;
            }
        }

        if (available == false) {

            ContentValues contentValues = new ContentValues();
            contentValues.put(column, text);
            database.update(table_store, contentValues, store_name + " = ?", new String[]{name});
            database.close();
        }

        return available;
    }

    public void deleteStore(String name) {

        SQLiteDatabase database = this.getWritableDatabase();

        database.delete(table_store, store_name + " = ?", new String[]{name});
        database.close();
    }

    public String storefiyat(String storename,String alışorsatış) {

        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT store_fiyatı,store_alışfiyatı FROM "+table_store+" WHERE store_name = " + "'" + storename + "'",null);
        cursor.moveToFirst();
        String storefiyat = cursor.getString(cursor.getColumnIndex(alışorsatış));

        return storefiyat;
    }

    public List<String> getFilter(String info) {

        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM " + table_store + " WHERE store_name LIKE '%" + info + "%'", null);

        cursor.moveToFirst();

        int size = cursor.getCount();

        ArrayList<String> list = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {

            list.add(cursor.getString(cursor.getColumnIndex("store_name")));

            cursor.moveToNext();
        }

        return list;
    }

    public String buyStore(String firm, String store, int miktar) {

        List<Store> storelist = this.StoreListele();

        String storefiyatı = null;
        String storemiktarı = null;

        int size = storelist.size();

        //ÜRÜN İSMİNE GÖRE ARAMA YAPIP ÜRÜN MİKTARI VE FİYATI BULUNUR
        for (int i = 0; i < size; i++) {

            if (storelist.get(i).getStorename().equalsIgnoreCase(store)) {

                storefiyatı = storelist.get(i).getStorealışfiyatı();
                storemiktarı = storelist.get(i).getStoremiktarı();

                break;
            }
        }

            SQLiteDatabase database = this.getWritableDatabase();

            Cursor cursor = database.rawQuery("SELECT money_value FROM " + table_money + " WHERE firm_name = " + "'" + firm + "'", null);
            cursor.moveToFirst();
            int moneyvalue = cursor.getInt(cursor.getColumnIndex("money_value"));
            int lastmoneyvalue = moneyvalue + Integer.parseInt(storefiyatı) * miktar;
            ContentValues contentValues = new ContentValues();
            contentValues.put(money_value, String.valueOf(lastmoneyvalue));
            database.update(table_money, contentValues, firm_name + " = ?", new String[]{firm});

            //DEPODAKİ ÜRÜN MİKTARINDA GÜNCELLEME YAPAR
            ContentValues contentValues2 = new ContentValues();
            contentValues2.put("store_miktarı",String.valueOf(Integer.parseInt(storemiktarı)+miktar));
            database.update(table_store,contentValues2,store_name+" = ?",new String[] {store});
            database.close();

            return String.valueOf(Integer.parseInt(storefiyatı) * miktar);
    }

    public String sellStore(String firm, String store, int miktar) {

        List<Store> storelist = this.StoreListele();

        String storefiyatı = null;
        String storemiktarı = null;

        int size = storelist.size();

            //ÜRÜN İSMİNE GÖRE ARAMA YAPIP ÜRÜN MİKTARI VE FİYATI BULUNUR
            for (int i = 0; i < size; i++) {

                if (storelist.get(i).getStorename().equalsIgnoreCase(store)) {

                    storefiyatı = storelist.get(i).getStorefiyatı();
                    storemiktarı = storelist.get(i).getStoremiktarı();

                    break;
                }
            }

            //SATILMAK İSTENİLEN ÜRÜN MİKTARININ DEPODA OLUP OLMADIĞININ KONTROLÜ YAPILIR
        if (Integer.parseInt(storemiktarı) >= miktar) {

            SQLiteDatabase database = this.getWritableDatabase();

            Cursor cursor = database.rawQuery("SELECT money_value FROM " + table_money + " WHERE firm_name = " + "'" + firm + "'", null);
            cursor.moveToFirst();
            int moneyvalue = cursor.getInt(cursor.getColumnIndex("money_value"));
            int lastmoneyvalue = moneyvalue - Integer.parseInt(storefiyatı) * miktar;
            ContentValues contentValues = new ContentValues();
            contentValues.put(money_value, String.valueOf(lastmoneyvalue));
            database.update(table_money, contentValues, firm_name + " = ?", new String[]{firm});

            //DEPODAKİ ÜRÜN MİKTARINDA GÜNCELLEMEYE YARAR
            ContentValues contentValues2 = new ContentValues();
            contentValues2.put("store_miktarı",String.valueOf(Integer.parseInt(storemiktarı)-miktar));
            database.update(table_store,contentValues2,store_name+" = ?",new String[] {store});
            database.close();

            return String.valueOf(Integer.parseInt(storefiyatı) * miktar);

        } else {

            return "";
        }
    }

    public ArrayList<Hashtable> CustomAdapterHashTable(int index) {

        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.rawQuery("SELECT * FROM money",null);
        cursor.moveToFirst();
        int size = cursor.getCount();
        ArrayList<Hashtable> list = new ArrayList<>(size);

        do{

            Hashtable<String,String> hashtable = new Hashtable<>();
            hashtable.put("firm_name",cursor.getString(cursor.getColumnIndex("firm_name")));
            hashtable.put("money_value",String.valueOf(cursor.getInt(cursor.getColumnIndex("money_value"))));
            list.add(hashtable);

        }while (cursor.moveToNext());

        ArrayList<Hashtable> templist;

        templist = list;

        if(index == 1) {

            for(int y=0;y<list.size();y++) {

                if(Integer.parseInt(list.get(y).get("money_value").toString()) < 0) {

                    templist.remove(y);
                }
            }
        }

        else if(index == 2) {

            for (int y = 0; y < list.size(); y++) {

                if (Integer.parseInt(list.get(y).get("money_value").toString()) > 0) {

                    templist.remove(y);
                }
            }

            for (int z = 0; z < list.size(); z++) {

                if (Integer.parseInt(list.get(z).get("money_value").toString()) == 0) {

                    templist.remove(z);
                }
            }
        }

        return templist;
    }

    public void InsertSatılanorAlınanFirmInfo(String satanfirma,String satılacakstore,int satılacakmiktar,String storealışfiyatı,String storesatışfiyatı,String kayıttarihi,int savecode) {

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(firm_name,satanfirma);
        contentValues.put(sell_firm_store_name,satılacakstore);
        contentValues.put(sell_store_miktar,satılacakmiktar);
        contentValues.put(sell_store_alış_fiyatı,storealışfiyatı);
        contentValues.put(sell_store_satış_fiyatı,storesatışfiyatı);
        contentValues.put(sell_store_time,kayıttarihi);
        contentValues.put(sell_store_code,savecode);
        database.insert(table_sell_firm,null,contentValues);

    }

    public List<FİRMS> getSatılanorAlınanFirmInfo(String tempfirmname,int stokecode) {

        List<FİRMS> list = new ArrayList<>();

        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM "+table_sell_firm+" WHERE firm_name = " + "'" + tempfirmname + "'" + " AND sell_store_code = "+stokecode ,null);
        cursor.moveToFirst();
        int size = cursor.getCount();

        if(size != 0) {

            do {

                FİRMS firms = new FİRMS();

                firms.setStorename(cursor.getString(cursor.getColumnIndex(sell_firm_store_name)));

                firms.setStoremiktar(cursor.getInt(cursor.getColumnIndex(sell_store_miktar)));

                firms.setSellstoredate(cursor.getString(cursor.getColumnIndex(sell_store_time)));

                firms.setStore_sell_or_buy_alışfiyatı(cursor.getInt(cursor.getColumnIndex(sell_store_alış_fiyatı)));

                firms.setStore_sell_or_buy_satışfiyatı(cursor.getInt(cursor.getColumnIndex(sell_store_satış_fiyatı)));

                list.add(firms);

            } while (cursor.moveToNext());

        }

        return list;
    }

    public ArrayList<StoreDecision> getStoreDecisionList() {

        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.query("decisiontree", new String[]{"record_id", "industrial_risk", "management_risk", "financial_flexibility", "credibility", "competitiveness,operating_risk,result"},null,null,null,null,null,null);
        cursor.moveToFirst();
        int length = cursor.getCount();

        ArrayList<StoreDecision> storeDecisionlist = new ArrayList<>(length);

        System.out.println(cursor.getCount());

        do {

            StoreDecision storeDecision = new StoreDecision(cursor.getColumnCount());

            int y = 0;

            for(int i = 1 ; i < cursor.getColumnCount() ; i++) {

                if (i != cursor.getColumnIndex("result")) {

                    storeDecision.storedecisionVerileri[y] = Integer.parseInt(cursor.getString(i));

                    y++;
                }

                if (i == cursor.getColumnIndex("result")) {

                    storeDecision.başarılı = Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex("result")));
                }
            }

            storeDecisionlist.add(storeDecision);

        }while (cursor.moveToNext());

        return storeDecisionlist;
    }
    public void InsertStoreDecision(HashMap<String,String> hashMap) {

        SQLiteDatabase database = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("record_id",hashMap.get("record_id"));
        contentValues.put("industrial_risk",hashMap.get("industrial_risk"));
        contentValues.put("management_risk",hashMap.get("management_risk"));
        contentValues.put("financial_flexibility",hashMap.get("financial_flexibility"));
        contentValues.put("credibility",hashMap.get("credibility"));
        contentValues.put("competitiveness",hashMap.get("competitiveness"));
        contentValues.put("operating_risk",hashMap.get("operating_risk"));
        contentValues.put("result",hashMap.get("result"));
        database.insert("decisiontree",null,contentValues);
        database.close();

    }

    public ArrayList<String> CustomAdapterMoneyvalue(SQLiteDatabase database) {

        ArrayList<String> firmname = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT firm_name FROM money WHERE money_value >"+0,null);
        cursor.moveToFirst();
        do {

            firmname.add(cursor.getString(cursor.getColumnIndex("firm_name")));

        }while (cursor.moveToNext());

        return firmname;
    }
}

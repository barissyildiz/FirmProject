package com.example.bar.firmss;

public class StoreDecision {

    // girdiler
    int[] storedecisionVerileri;

    // çıktı
    boolean başarılı;

    public StoreDecision(int length) {

        storedecisionVerileri = new int[length];

    }

    public StoreDecision(int[] gelenVeriler, boolean value ) {

        storedecisionVerileri = new int[gelenVeriler.length];

        for(int i = 0; i < gelenVeriler.length; i++) {
            storedecisionVerileri[i] = gelenVeriler[i];
        }

        başarılı = value;

        /*
        if(gelenVeriler[veriBoyutu] == 2) {
            zararliMi = false;
        } else {
            zararliMi = true;
        }*/
    }

    public StoreDecision( int[] gelenVeriler,String value ) {

        storedecisionVerileri = new int[gelenVeriler.length];

        for(int i = 0; i < gelenVeriler.length; i++) {
            storedecisionVerileri[i] = gelenVeriler[i];
        }
    }

    public String toString() {
        String sonuc = "";

        for(int i = 0; i < storedecisionVerileri.length; i++) {
            sonuc += "" + storedecisionVerileri[i] + " ";
        }

        if(başarılı) {
            sonuc += "zararlı.";
        } else {
            sonuc += "zararsız.";
        }

        return sonuc;
    }

    public boolean sonuc() {
        return başarılı;
    }
}

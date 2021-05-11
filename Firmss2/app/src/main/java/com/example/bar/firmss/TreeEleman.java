package com.example.bar.firmss;

import java.util.ArrayList;

public class TreeEleman {

    int storedecisionVeriIndeksi;
    int ayrimNoktasi;

    ArrayList<StoreDecision> liste;
    TreeEleman kucuklerElemani;
    TreeEleman buyuklerElemani;

    boolean[] kullanilabilirVeriIndeksleri;

    boolean başarılı;

    public TreeEleman(ArrayList gelenListe, boolean[] anadanGelenVeriIndeksleri) {

        this.liste = gelenListe;

        kullanilabilirVeriIndeksleri = new boolean[anadanGelenVeriIndeksleri.length];

        for(int i = 0; i < anadanGelenVeriIndeksleri.length; i++) {
            kullanilabilirVeriIndeksleri[i] = anadanGelenVeriIndeksleri[i];
        }

        ayrimNoktasi = -1;
        storedecisionVeriIndeksi = -1;
        başarılı = false;
    }
}

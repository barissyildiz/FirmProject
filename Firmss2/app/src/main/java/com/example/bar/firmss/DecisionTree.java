package com.example.bar.firmss;

import java.util.ArrayList;

public class DecisionTree {

    TreeEleman kokEleman;
    int length;

    public DecisionTree(ArrayList gelenListe) {

        boolean[] kullanilabilirVeriIndeksleri = new boolean[6];
        for (int i = 0; i < 6; i++) {
            kullanilabilirVeriIndeksleri[i] = true;
        }

        kullanilabilirVeriIndeksleri[0] = false;
        //kullanilabilirVeriIndeksleri[2] = false;

        length = kullanilabilirVeriIndeksleri.length;
        kokEleman = new TreeEleman(gelenListe, kullanilabilirVeriIndeksleri);
    }

    public void elemanSonuclariniBelirle(TreeEleman e) {
        // sadece leaf (yaprak/en uç) elemanlar için belirlemeli
        if (e.kucuklerElemani == null && e.buyuklerElemani == null) {
            // leaf elemandayız!
            // sonuçları belirleyeceğiz

            int başarılılar = 0;
            int başarısızlar = 0;

            for (StoreDecision h : e.liste) {
                if (h.başarılı) {
                    başarılılar++;
                } else {
                    başarısızlar++;
                }
            }

            if (başarılılar >= başarısızlar) {
                e.başarılı = true;
            } else {
                e.başarılı = false;
            }
        } else {
            // bu elemanın çocukları var
            // çocuklar için aynı metodu çağır
            elemanSonuclariniBelirle(e.kucuklerElemani);
            elemanSonuclariniBelirle(e.buyuklerElemani);
        }
    }

    public boolean StoreDecisionTestEt(StoreDecision h) {
        return testEt(h, kokEleman);
    }

    private boolean testEt(StoreDecision h, TreeEleman e) {
        if (e.kucuklerElemani == null && e.buyuklerElemani == null) {
            // leaf eleman, yani bu noktada karar verebiliriz
            return e.başarılı;
        } else {
            if (h.storedecisionVerileri[e.storedecisionVeriIndeksi] <= e.ayrimNoktasi) {
                return testEt(h, e.kucuklerElemani);
            } else {
                return testEt(h, e.buyuklerElemani);
            }
        }
    }

    public void RunTree1() {
        RunTree2(kokEleman);
        elemanSonuclariniBelirle(kokEleman);
    }

    public void RunTree2(TreeEleman mevcutEleman) {
        // bütün kullanılabilir veri indeksleri için
        // en iyi ayrımı bul, ve bu ayrımların arasından
        // en iyi entropy degerine sahip ayrımı gerçekleştir
        int enIyiVeriIndeksi = -1;
        float enIyiEntropy = 5;
        int enIyiAyrimNoktasi = -1;

        for (int i = 0; i < length; i++) {
            if (mevcutEleman.kullanilabilirVeriIndeksleri[i]) {
                float[] ayrimNokVeEnt = enIyiAyrimiBul(mevcutEleman.liste, i);
                if (ayrimNokVeEnt[1] <= enIyiEntropy) {
                    enIyiVeriIndeksi = i;
                    enIyiEntropy = ayrimNokVeEnt[1];
                    enIyiAyrimNoktasi = (int) ayrimNokVeEnt[0];
                }
            }
        }

        if (enIyiVeriIndeksi != -1) {

            System.out.println("İki liste oluştu, ayrım noktası: " + enIyiAyrimNoktasi);
            System.out.println("enIyiVeriIndeksi: " + enIyiVeriIndeksi + " E: " + enIyiEntropy);

            mevcutEleman.kullanilabilirVeriIndeksleri[enIyiVeriIndeksi] = false;
            mevcutEleman.ayrimNoktasi = enIyiAyrimNoktasi;
            mevcutEleman.storedecisionVeriIndeksi = enIyiVeriIndeksi;

            mevcutEleman.kucuklerElemani = new TreeEleman(new ArrayList(),
                    mevcutEleman.kullanilabilirVeriIndeksleri);
            mevcutEleman.buyuklerElemani = new TreeEleman(new ArrayList(),
                    mevcutEleman.kullanilabilirVeriIndeksleri);

            listeyiAyir(mevcutEleman.liste, enIyiVeriIndeksi, enIyiAyrimNoktasi,
                    mevcutEleman.kucuklerElemani.liste, mevcutEleman.buyuklerElemani.liste);

            RunTree2(mevcutEleman.kucuklerElemani);
            RunTree2(mevcutEleman.buyuklerElemani);
        }
    }

    public ArrayList<int[]> FindAyrımNoktasıVeriİndex(ArrayList<StoreDecision> list) {

        int veriindexcount = list.get(0).storedecisionVerileri.length;

        int length = list.size();

        int[] büyükayrımnoktaları = new int[veriindexcount];
        int[] küçükayrımnoktaları = new int[veriindexcount];

        for(int i = 0 ; i < veriindexcount ; i++) {

            büyükayrımnoktaları[i] = 0;
        }

        for(int i = 0 ; i < veriindexcount ; i++) {

            küçükayrımnoktaları[i] = 1000;
        }

        for(int y = 0 ; y < veriindexcount ; y++) {

            for (int z = 0; z < length; z++) {

                if (büyükayrımnoktaları[y] < list.get(z).storedecisionVerileri[y]) {

                    büyükayrımnoktaları[y] = list.get(z).storedecisionVerileri[y];
                }
            }
        }

        for(int n = 0 ; n < veriindexcount ; n++) {

            for (int m = 0; m < length; m++) {

                if (küçükayrımnoktaları[n] > list.get(m).storedecisionVerileri[n]) {

                    küçükayrımnoktaları[n] = list.get(m).storedecisionVerileri[n];
                }
            }
        }

        ArrayList<int[]> liste = new ArrayList<>(2);

        liste.add(büyükayrımnoktaları);
        liste.add(küçükayrımnoktaları);

        return liste;
    }

    public float[] enIyiAyrimiBul(ArrayList gelenListe, int veriIndeks ) {

        ArrayList<int[]> list = FindAyrımNoktasıVeriİndex(gelenListe);

        int enbüyükeleman = 0,enküçükeleman = 0;

        for(int i = 0 ; i < list.get(0).length ; i++) {

            if(i == veriIndeks) {

                enbüyükeleman = list.get(0)[i];
                enküçükeleman = list.get(1)[i];
            }
        }

        float[] ayrimNokVeEnt= new float[2];

        int enIyiAyrimNoktasi = enküçükeleman;

        int[] hSayilar = listeyiAyirHayali(gelenListe, veriIndeks, enIyiAyrimNoktasi);

        // entropy hesapla
        float enIyiEntropy = entropyHesapla(hSayilar);

        float geciciEntropy;

        for(int i = enküçükeleman; i <= enbüyükeleman; i++) {

            hSayilar = listeyiAyirHayali(gelenListe, veriIndeks, i);
            geciciEntropy = entropyHesapla(hSayilar);
            if(geciciEntropy < enIyiEntropy) {
                enIyiEntropy = geciciEntropy;
                enIyiAyrimNoktasi = i;
                //System.out.println(" EN İYİ ENTROPY : "+enIyiEntropy +" EN İYİ AYRIM NOKTASI : "+enIyiAyrimNoktasi +" EN İYİ İNDEX : "+veriIndeks);
            }
        }

        ayrimNokVeEnt[0] = enIyiAyrimNoktasi;
        ayrimNokVeEnt[1] = enIyiEntropy;

        return ayrimNokVeEnt;
    }

    public float entropyHesapla(int[] hSayilar) {

        float başarılıSayisiKucukler = hSayilar[0];
        float başarısızSayisiKucukler = hSayilar[1];
        float başarılıSayisiBuyukler = hSayilar[2];
        float başarısızSayisiBuyukler = hSayilar[3];

        float entropy;

        float kucuklerToplam = başarılıSayisiKucukler + başarısızSayisiKucukler;
        float buyuklerToplam = başarılıSayisiBuyukler + başarısızSayisiBuyukler;

        float kucuklerBaşarılıOrani = başarılıSayisiKucukler / kucuklerToplam;
        float kucuklerBaşarısızOrani = 1f - kucuklerBaşarılıOrani;

        float buyuklerBaşarılıOrani = başarılıSayisiBuyukler / buyuklerToplam;
        float buyuklerBaşarısızOrani = 1f - buyuklerBaşarılıOrani;

        // Entropy = - p(a) * log(p(a)) - p(b) * log(p(b))

        boolean küçüklerBaşarılı = kucuklerBaşarılıOrani >= buyuklerBaşarılıOrani;

        if(küçüklerBaşarılı) {
            // küçükler kümesindekilere zararsız, büyüklerdekine zararlı demiş olduk
            float pa = kucuklerBaşarılıOrani;
            float pb = buyuklerBaşarısızOrani;
            entropy = -pa * log2(pa) - pb * log2(pb);
        } else {
            // küçükler zararlı, büyükler zararsızdır
            float pa = kucuklerBaşarısızOrani;
            float pb = buyuklerBaşarılıOrani;
            entropy = -pa * log2(pa) - pb * log2(pb);
        }
        return entropy;
    }

    private float log2(float x) {
        if(x == 0f) {
            return 0f;
        } else {
            return (float) (Math.log((double)x)/Math.log((double)2));
        }
    }

    public int[] listeyiSay(ArrayList<StoreDecision> gelenListe) {

        int başarılıSayısı = 0;
        int başarısızSayısı = 0;

        for(StoreDecision h : gelenListe) {

            if(h.başarılı) {
                başarılıSayısı++;
            } else {
                başarısızSayısı++;
            }
        }

        int[] sayilar = new int[2];

        sayilar[0] = başarılıSayısı;
        sayilar[1] = başarısızSayısı;

        return sayilar;
    }

    public void listeyiAyir(ArrayList<StoreDecision> gelenListe, int veriIndeks,
                            int ayrimNoktasi, ArrayList kucuklerReturn,
                            ArrayList buyuklerReturn) {

        if(veriIndeks >= length || veriIndeks < 0) {
            System.out.println("Veri indeksi yanlış!");
            return;
        }

        for(StoreDecision h : gelenListe) {

            if(h.storedecisionVerileri[veriIndeks] <= ayrimNoktasi) {
                kucuklerReturn.add(h);
            } else {
                buyuklerReturn.add(h);
            }
        }
    }

    public int[] listeyiAyirHayali(ArrayList<StoreDecision> gelenListe, int veriIndeks,
                                   int ayrimNoktasi) {

        if(veriIndeks >= length || veriIndeks < 0) {
            System.out.println("Veri indeksi yanlış!");
            return null;
        }

        int başarılıSayisiKucukler = 0;
        int başarısızSayisiKucukler = 0;
        int başarılıSayisiBuyukler = 0;
        int başarısızSayisiBuyukler = 0;

        for(StoreDecision h : gelenListe) {

            if(h.storedecisionVerileri[veriIndeks] <= ayrimNoktasi) {
                if(h.başarılı) {
                    başarılıSayisiKucukler++;
                } else {
                    başarısızSayisiKucukler++;
                }
            } else {
                if(h.başarılı) {
                    başarılıSayisiBuyukler++;
                } else {
                    başarısızSayisiBuyukler++;
                }
            }
        }
        int[] sayilar = new int[4];
        sayilar[0] = başarılıSayisiKucukler;
        sayilar[1] = başarısızSayisiKucukler;
        sayilar[2] = başarılıSayisiBuyukler;
        sayilar[3] = başarısızSayisiBuyukler;
        return sayilar;
    }
}

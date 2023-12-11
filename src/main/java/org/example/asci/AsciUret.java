package org.example.asci;

import org.example.AsciPanel;
import org.example.Siparis;

import java.util.Vector;

public class AsciUret {
    private static Vector<Siparis> hazirlanacakSiparisler;

    public AsciUret(AsciPanel asciPanel, int asciSayisi) {
        hazirlanacakSiparisler = new Vector<Siparis>();

        for (int i = 0; i< asciSayisi; i++ ) {
            AsciThread asciThread = new AsciThread( i, asciPanel, this ); //aşçılar için thread oluşturur
            asciThread.start();
        }

    }

    public void yeniSiparisEkle(Siparis o) { //sipariş oluşturur: garson, aşçı,masa tutar=order
        hazirlanacakSiparisler.add(o);
    }
    public static Vector<Siparis> hazirlanacakSiparisleriGetir(){
        return hazirlanacakSiparisler;
    }

}
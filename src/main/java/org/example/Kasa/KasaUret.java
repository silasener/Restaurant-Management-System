package org.example.Kasa;

import org.example.AsciPanel;
import org.example.Koordinasyon;
import org.example.Siparis;
import org.example.asci.AsciThread;

import javax.swing.*;
import java.util.Vector;

public class KasaUret {
    private static Vector<Siparis> odenecekSiparisler;

    public KasaUret(JPanel panelKasa, Koordinasyon koordinasyon) {
        odenecekSiparisler = new Vector<Siparis>();
           KasaThread kasaThread=new KasaThread(panelKasa,koordinasyon,this);
            kasaThread.start();
    }

    public void odenecekSiparisEkle(Siparis o) {
        odenecekSiparisler.add(o);
    }

    public static Vector<Siparis> odenecekSiparisleriGetir() {
        return odenecekSiparisler;
    }
}

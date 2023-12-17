package org.example.musteri;

import org.example.Koordinasyon;
import org.example.Problem1;
import org.example.RestoranYonetimSistemi;

import java.util.Vector;

public class MusteriUret extends Thread {
    public static Vector<MusteriThread> musteriThreadVectorleri = new Vector<MusteriThread>();
    public static int musteriGrupSirasi = 0;
    int normalMusteriNumarasi = 0;
    int oncelikliMusteriNumarasi = 0;
    int musteriNumarasi = 0;
    private Koordinasyon koordinasyonThread;

    public MusteriUret(Koordinasyon koordinasyonThread) {
        this.koordinasyonThread = koordinasyonThread;
        this.start();
    }

    public static Vector<MusteriThread> getMusteriThreadVectorleri() {
        return musteriThreadVectorleri;
    }

    public void musteriGrubuCagir() {
        normalMusteriNumarasi = Problem1.musteriGrubuList.get(musteriGrupSirasi).getNormalMusteriSayisi();
        oncelikliMusteriNumarasi = Problem1.musteriGrubuList.get(musteriGrupSirasi).getOncelikliMusteriSayisi();
        System.out.println("\nAdım no: " + (musteriGrupSirasi + 1));
        for (int i = 0; i < oncelikliMusteriNumarasi; i++) {
            System.out.println("öncelikli müşteri numaraları: " + musteriNumarasi);
            RestoranYonetimSistemi.bekleyenMusteriEkle(musteriNumarasi);
            MusteriThread musteriThread = new MusteriThread(musteriNumarasi, koordinasyonThread, true);
            musteriThreadVectorleri.add(musteriThread);
            RestoranYonetimSistemi.oncelikliMusterilerBitirildi = false;
            if ((i + 1) < Problem1.musteriGrubuList.get(musteriGrupSirasi).getOncelikliMusteriSayisi()) {
                musteriNumarasi++;
            }
        }
        musteriNumarasi++;
        for (int i = 0; i < normalMusteriNumarasi; i++) {
            RestoranYonetimSistemi.bekleyenMusteriEkle(musteriNumarasi);
            MusteriThread musteriThread = new MusteriThread(musteriNumarasi, koordinasyonThread, false);
            musteriThreadVectorleri.add(musteriThread);
            if ((i + 1) < Problem1.musteriGrubuList.get(musteriGrupSirasi).getNormalMusteriSayisi()) {
                musteriNumarasi++;
            }
        }
        musteriNumarasi++;
    }


}

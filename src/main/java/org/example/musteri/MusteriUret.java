package org.example.musteri;

import com.sun.tools.javac.Main;
import org.example.Koordinasyon;
import org.example.Problem1;
import org.example.RestoranYonetimSistemi;

import java.util.List;
import java.util.Vector;

public class MusteriUret extends Thread{
    public static Vector<MusteriThread> musteriThreadVectorleri = new Vector<MusteriThread>();
    private Koordinasyon koordinasyonThread;

    public static Vector<MusteriThread> getMusteriThreadVectorleri() {
        return musteriThreadVectorleri;
    }

    public static int musteriGrupSirasi=0;
    int normalMusteriNumarasi = 0;
    int oncelikliMusteriNumarasi = 0;
    int musteriNumarasi=0;

    public MusteriUret(Koordinasyon koordinasyonThread) {
        this.koordinasyonThread = koordinasyonThread;;
        this.start();
    }

    public void musteriGrubuCagir() {
        normalMusteriNumarasi=Problem1.musteriGrubuList.get(musteriGrupSirasi).getNormalMusteriSayisi();
        oncelikliMusteriNumarasi=Problem1.musteriGrubuList.get(musteriGrupSirasi).getOncelikliMusteriSayisi();
        System.out.println("\nAdım no: "+musteriGrupSirasi);
        for ( int i=0; i < oncelikliMusteriNumarasi; i++) {
            System.out.println("öncelikli müşteri numaraları: "+musteriNumarasi);
            RestoranYonetimSistemi.bekleyenMusteriEkle(musteriNumarasi);
            MusteriThread musteriThread = new MusteriThread(musteriNumarasi, koordinasyonThread,true);
            musteriThreadVectorleri.add(musteriThread);
            RestoranYonetimSistemi.oncelikliMusterilerBitirildi=false;
            if((i+1)<Problem1.musteriGrubuList.get(musteriGrupSirasi).getOncelikliMusteriSayisi()){
                musteriNumarasi++;
            }
        }
        musteriNumarasi++;
        for (int i=0 ; i < normalMusteriNumarasi; i++) {
            RestoranYonetimSistemi.bekleyenMusteriEkle(musteriNumarasi);
            MusteriThread musteriThread = new MusteriThread(musteriNumarasi, koordinasyonThread,false);
            musteriThreadVectorleri.add(musteriThread);
            if((i+1)<Problem1.musteriGrubuList.get(musteriGrupSirasi).getNormalMusteriSayisi()){
                musteriNumarasi++;
            }
        }
        musteriNumarasi++;

        if(Problem1.musteriGrubuList.size()>musteriGrupSirasi){
            musteriGrupSirasi++;
        }
    }




}

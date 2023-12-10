package org.example.musteri;

import org.example.Koordinasyon;

import java.util.Vector;

public class MusteriUret extends Thread{
    private Vector<MusteriThread> musteriThreadVectorleri = new Vector<MusteriThread>();
    private Koordinasyon koordinasyonThread;

    public MusteriUret(Koordinasyon koordinasyonThread) {
        this.koordinasyonThread = koordinasyonThread;;
        this.start();
    }

    public void run() {
        try {
            int musteriNumarasi = 0;
            while (true) {
                MusteriThread musteriThread = new MusteriThread(musteriNumarasi++, koordinasyonThread);
                musteriThreadVectorleri.add(musteriThread);
                Thread.sleep(1000 * (int)(Math.random() * 5)); // customers come in between 0 and 5 seconds apart
            }
        } catch (InterruptedException ie) {
            System.out.println("CustomerFactory.run(): InterruptedException: " + ie.getMessage());
            for (MusteriThread musteriThreadi : musteriThreadVectorleri) {
                musteriThreadi.interrupt();
            }
        }
    }
}

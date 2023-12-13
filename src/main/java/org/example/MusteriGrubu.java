package org.example;

public class MusteriGrubu {
    private int adimNo;
    private int oncelikliMusteriSayisi;
    private int normalMusteriSayisi;

    public MusteriGrubu(int adimNo) {
        this.adimNo = adimNo;
    }

    public int getAdimNo() {
        return adimNo;
    }

    public int getOncelikliMusteriSayisi() {
        return oncelikliMusteriSayisi;
    }

    public void setOncelikliMusteriSayisi(int oncelikliMusteriSayisi) {
        this.oncelikliMusteriSayisi = oncelikliMusteriSayisi;
    }

    public int getNormalMusteriSayisi() {
        return normalMusteriSayisi;
    }

    public void setNormalMusteriSayisi(int normalMusteriSayisi) {
        this.normalMusteriSayisi = normalMusteriSayisi;
    }
}

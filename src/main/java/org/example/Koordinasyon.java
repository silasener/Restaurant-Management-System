package org.example;

import org.example.Kasa.KasaThread;
import org.example.garson.GarsonThread;
import org.example.musteri.MusteriThread;

import java.util.Random;


public class Koordinasyon {
	private Masalar masalar;

	public Koordinasyon(int masaNumaralari) {
		this.masalar = new Masalar(masaNumaralari);
	} // masaları oluşturur




	public Masa musteriYerlestir(MusteriThread musteriThread) {
		Masa masa = null;
		try {
			RestoranYonetimSistemi.mesajEkle("Müşteri:" + musteriThread.getMusteriNumarasi()+" beklemede ve uygun masa bekliyor");
			/* Here, customer is about to wait for a table... add him to waiting label*/
			RestoranYonetimSistemi.bekleyenMusteriEkle(musteriThread.getMusteriNumarasi());
			masa = masalar.getTable();

			GarsonThread garson = RestoranYonetimSistemi.getGarsonUret().getGarson();
			garson.setMasa(masa);
			masa.masayaOturtveIlgilen(musteriThread, garson);

			// changes
			Random rand = new Random();
			int randomOrder = rand.nextInt(2);
			Siparis order = new Siparis( randomOrder, musteriThread.getMusteriNumarasi(), masa, garson); //order içinde garson, masa , aşçı tutularak kullanıma hazır hle getirilir
			RestoranYonetimSistemi.asciUret.yeniSiparisEkle(order);  //aşçının siparişi oluşur
			garson.setSiparis(order);
			musteriThread.setSiparis(order);

			/* Here, customer is seated */
			RestoranYonetimSistemi.hizmetVerilenMusteriEkle(musteriThread.getMusteriNumarasi());
			RestoranYonetimSistemi.garsonMesajiEkle("Müşteri " + musteriThread.getMusteriNumarasi() + " oturduğu masa: " + masa.getMasaNumarasi(), garson.getGarsonunNumarasi());

			RestoranYonetimSistemi.garsonMesajiEkle("Garson " +garson.getGarsonunNumarasi() + " aldığı sipariş tutarı: " + order.getSiparisTutari() +" ve aldığı masa "+ order.getMasa().getMasaNumarasi(),garson.getGarsonunNumarasi());

			RestoranYonetimSistemi.mesajEkle("Müşteri: " + musteriThread.getMusteriNumarasi() + " oturduğu masa: " + masa.getMasaNumarasi() + " ve garsonu: " + garson.getGarsonunNumarasi());


		} catch (InterruptedException ie) {
			System.out.println("HostessThread.musteriYerlestir():InterruptedException: " + ie.getMessage());
		}
		return masa;
	}


	public void musteriAyriliyor(MusteriThread musteriThread) {
		RestoranYonetimSistemi.musteriAyrilacakLabelGuncelle(musteriThread.getMusteriNumarasi());
		RestoranYonetimSistemi.garsonMesajiEkle("Müşteri: " + musteriThread.getMusteriNumarasi() + " yemek yedi , ödeme yapıp ayrılacak", musteriThread.getMasa().getGarsonThread().getGarsonunNumarasi());
		RestoranYonetimSistemi.mesajEkle("Müşteri " + musteriThread.getMusteriNumarasi() + " yemek yedi ve ayrılıyor.");
		musteriThread.getMasa().getGarsonThread().returnMasa(musteriThread.getMasa());
		KasaThread kasaThread = new KasaThread(musteriThread);
		kasaThread.start();
		masalar.returnMasa(musteriThread.getMasa());
	}

}

package org.example;

import org.example.garson.GarsonThread;
import org.example.musteri.MusteriThread;

import java.util.Random;


public class Koordinasyon {
	private Masalar masalar;

	public Koordinasyon(int masaNumaralari) {
		this.masalar = new Masalar(masaNumaralari);
	} // masaları oluşturur




	public Masa seatCustomer(MusteriThread musteriThread) {
		Masa masa = null;
		try {
			RestoranYonetimSistemi.mesajEkle("Koordinasyon , müşteriyi oturtmaya çalışıyor" + musteriThread.getMusteriNumarasi());
			/* Here, customer is about to wait for a table... add him to waiting label*/
			RestoranYonetimSistemi.bekleyenMusteriEkle(musteriThread.getMusteriNumarasi());
			masa = masalar.getTable();

			GarsonThread garson = RestoranYonetimSistemi.getGarsonUret().getGarson();
			garson.setMasa(masa);
			masa.masayaOturtveIlgilen(musteriThread, garson);

			// changes
			Random rand = new Random();
			int randomOrder = rand.nextInt(2);
			Order order = new Order( randomOrder, musteriThread.getMusteriNumarasi(), masa, garson);
			RestoranYonetimSistemi.cookFactory.appendNewOrder(order);
			garson.setSiparis(order);
			musteriThread.setSiparis(order);

			/* Here, customer is seated */
			RestoranYonetimSistemi.hizmetVerilenMusteriEkle(musteriThread.getMusteriNumarasi());
			RestoranYonetimSistemi.garsonMesajiEkle("Müşteri " + musteriThread.getMusteriNumarasi() + " oturduğu masa: " + masa.getMasaNumarasi(), garson.getGarsonunNumarasi());

			RestoranYonetimSistemi.garsonMesajiEkle("Garson " +garson.getGarsonunNumarasi() + " aldığı sipariş:   " + order.getOrderText() +" ve aldığı masa "+ order.getMasa().getMasaNumarasi(),garson.getGarsonunNumarasi());

			RestoranYonetimSistemi.mesajEkle("Koordinasyonun oturttuğu müşteri: " + musteriThread.getMusteriNumarasi() + "ve masası: " + masa.getMasaNumarasi() + " ve garsonu: " + garson.getGarsonunNumarasi());


		} catch (InterruptedException ie) {
			System.out.println("HostessThread.seatCustomer():InterruptedException: " + ie.getMessage());
		}
		return masa;
	}


	public void musteriAyriliyor(MusteriThread musteriThread) {
		RestoranYonetimSistemi.musteriAyrilacakLabelGuncelle(musteriThread.getMusteriNumarasi());
		RestoranYonetimSistemi.garsonMesajiEkle("Müşteri: " + musteriThread.getMusteriNumarasi() + " yemek yedi ayrılıyor, ve garsonu:", musteriThread.getMasa().getGarsonThread().getGarsonunNumarasi());
		RestoranYonetimSistemi.mesajEkle("Müşteri " + musteriThread.getMusteriNumarasi() + " yemek yedi ve ayrılıyor.");
		musteriThread.getMasa().getGarsonThread().returnMasa(musteriThread.getMasa());
		masalar.returnMasa(musteriThread.getMasa());
	}

}

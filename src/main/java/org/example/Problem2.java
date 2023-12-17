package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Problem2 extends JFrame {

    private JTextArea textArea;

    public Problem2() {
        setTitle("Problem 2 Maksimum Müşteri Sayısına Hizmet Verme Stratejisi ");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        JLabel labelSimulasyonZamani = new JLabel("Simülasyon Süresi (saniye):");
        JTextField textFieldSimulasyonZamani = new JTextField();
        JLabel labelMusteriGelmeAraligi = new JLabel("Müşteri Gelme Aralığı (saniye):");
        JTextField textFieldMusteriGelmeAraligi = new JTextField();
        JLabel labelMusteriSayisi = new JLabel("Her Turda Gelecek Müşteri Sayısı:");
        JTextField textFieldMusteriSayisi = new JTextField();

        JButton buttonHesapla = new JButton("Hesapla");

        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        panel.add(labelSimulasyonZamani);
        panel.add(textFieldSimulasyonZamani);
        panel.add(labelMusteriGelmeAraligi);
        panel.add(textFieldMusteriGelmeAraligi);
        panel.add(labelMusteriSayisi);
        panel.add(textFieldMusteriSayisi);
        panel.add(new JLabel());
        panel.add(buttonHesapla);
        panel.add(new JLabel());
        panel.add(scrollPane);

        buttonHesapla.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int simulasyonZamani = Integer.parseInt(textFieldSimulasyonZamani.getText());
                int musteriGelmeAraligi = Integer.parseInt(textFieldMusteriGelmeAraligi.getText());
                int musteriSayisi = Integer.parseInt(textFieldMusteriSayisi.getText());

                int toplamTur = (int) Math.floor((double) simulasyonZamani / musteriGelmeAraligi); // tur sayısı aşağı yuvarlanır

                int masadanAyrilmaSuresi = 9; // Müşteri masadan ayrıldıktan sonra masanın tekrar uygun hale gelmesine kadar geçen süre

                int katSayi = (masadanAyrilmaSuresi / musteriGelmeAraligi) + 1;
                // masa ve garsonlar için boş olma zamanını hesaplar , müşteri grubuna göre üretilmesi gereken sayıyı hesaplamada kullanılır

                int asciSayisi = 0;
                if ((musteriGelmeAraligi - 3) >= 1) {
                    asciSayisi = (int) Math.ceil(musteriSayisi / 2.0); // her aşçı aynı anda 2 sipariş yapabilir
                    // gruptaki kisi sayısı / 2 yapılır ; her müşteri için aynı anda sipariş hazırlanabilmesi için
                } else if ((musteriGelmeAraligi - 3) < 1) {
                    asciSayisi = musteriSayisi;
                    // müşteri gelme aralığı , aşçının sipariş yapma süresinden kısaysa ( 2sn de bir gelmesi gibi )  her müşteri için aynı anda sipariş hazırlanabilmesi için
                }

                int masaSayisi = katSayi * musteriSayisi;
                int garsonSayisi = katSayi * musteriSayisi;
                int agirlananMusteriSayisi = toplamTur * musteriSayisi;
                int kazanc = agirlananMusteriSayisi - (masaSayisi + garsonSayisi + asciSayisi);

                String resultMessage = "Ağırlanan müşteri sayısı: " + agirlananMusteriSayisi +
                        "\nToplam masa sayısı: " + masaSayisi +
                        "\nToplam garson sayısı: " + garsonSayisi +
                        "\nToplam aşçı sayısı: " + asciSayisi +
                        "\nKazanç: " + kazanc;

                textArea.setText(resultMessage);
            }
        });


        add(panel);

        setVisible(true);
    }


}

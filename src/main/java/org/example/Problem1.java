package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Problem1 extends JFrame {
    public static List<MusteriGrubu> musteriGrubuList = new ArrayList<>();
    private static JButton kaydetButton;
    private int adimSayisi;
    private int i;
    private int j;


    public Problem1() {
        setTitle("Problem 1 Müşteri Senaryosu Belirleme Ekranı");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        JPanel anaPanel = new JPanel(new BorderLayout());
        anaPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel ustPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        kaydetButton = new JButton("Kaydet");

        // Adım sayısı girişi için etiket, alan ve onayla butonu
        JLabel adimLabel = new JLabel("Adım Sayısı:");
        JTextField adimField = new JTextField(5);
        JButton onaylaButton = new JButton("Onayla");

        ustPanel.add(adimLabel);
        ustPanel.add(adimField);
        ustPanel.add(onaylaButton);

        // Ortadaki panel oluştur
        JPanel merkezPanel = new JPanel(new GridLayout(0, 4, 10, 10));

        JLabel oncelikliLabel = new JLabel("  Öncelikli Müşteri Sayısı");
        JLabel normalLabel = new JLabel("    Normal Müşteri Sayısı");

        onaylaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    adimSayisi = Integer.parseInt(adimField.getText());

                    merkezPanel.removeAll();

                    merkezPanel.add(new JLabel());
                    merkezPanel.add(oncelikliLabel);
                    merkezPanel.add(new JLabel());
                    merkezPanel.add(normalLabel);


                    for (i = 1; i <= adimSayisi; i++) {
                        MusteriGrubu gruplar = new MusteriGrubu(i);
                        musteriGrubuList.add(gruplar);
                        JComboBox<Integer> oncelikliMusteriComboBox = new JComboBox<>();
                        oncelikliMusteriComboBox.setToolTipText(String.valueOf(i));
                        JComboBox<Integer> normalMusteriComboBox = new JComboBox<>();
                        normalMusteriComboBox.setToolTipText(String.valueOf(i));

                        for (j = 0; j <= 10; j++) {
                            oncelikliMusteriComboBox.addItem(j);
                        }

                        oncelikliMusteriComboBox.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                int secilenOncelikliMusteriSayisi = (Integer) oncelikliMusteriComboBox.getSelectedItem();
                                int secilebilecekMaxNormalMusteriSayisi = 10 - secilenOncelikliMusteriSayisi;

                                normalMusteriComboBox.removeAllItems();
                                for (int k = 0; k <= secilebilecekMaxNormalMusteriSayisi; k++) {
                                    normalMusteriComboBox.addItem(k);
                                }

                                for (MusteriGrubu grup : musteriGrubuList) {
                                    if (grup.getAdimNo() == Integer.parseInt(normalMusteriComboBox.getToolTipText())) {
                                        grup.setOncelikliMusteriSayisi((Integer) oncelikliMusteriComboBox.getSelectedItem());
                                        grup.setNormalMusteriSayisi((Integer) normalMusteriComboBox.getSelectedItem());
                                    }
                                }
                            }
                        });

                        normalMusteriComboBox.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                for (MusteriGrubu grup : musteriGrubuList) {
                                    if (grup.getAdimNo() == Integer.parseInt(normalMusteriComboBox.getToolTipText())) {
                                        grup.setOncelikliMusteriSayisi((Integer) oncelikliMusteriComboBox.getSelectedItem());
                                        grup.setNormalMusteriSayisi((Integer) normalMusteriComboBox.getSelectedItem());
                                    }
                                }
                            }
                        });

                        merkezPanel.add(new JLabel("Adım " + i + ":"));
                        merkezPanel.add(oncelikliMusteriComboBox);
                        merkezPanel.add(new JLabel());
                        merkezPanel.add(normalMusteriComboBox);
                    }


                    JPanel savePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                    savePanel.add(kaydetButton);

                    anaPanel.add(ustPanel, BorderLayout.NORTH);
                    anaPanel.add(merkezPanel, BorderLayout.CENTER);
                    anaPanel.add(savePanel, BorderLayout.SOUTH);

                    revalidate();
                    repaint();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Geçerli bir sayı giriniz.");
                }
            }
        });


        kaydetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (MusteriGrubu grup : musteriGrubuList) {
                    System.out.println("\nAdım :" + grup.getAdimNo() + "\nÖncelikli musteri sayisi:" + grup.getOncelikliMusteriSayisi() + "\nNormal musteri sayisi:" + grup.getNormalMusteriSayisi());
                }
                RestoranYonetimSistemi restoranYonetimSistemi = new RestoranYonetimSistemi();
                setVisible(false);
            }
        });

        // Ana panele üst ve orta panelleri ekle
        anaPanel.add(ustPanel, BorderLayout.NORTH);
        anaPanel.add(merkezPanel, BorderLayout.CENTER);

        // Ana paneli pencereye ekle
        add(anaPanel);

        // Pencereyi göster
        setVisible(true);
    }

    public static List<MusteriGrubu> getMusteriGrubuList() {
        return musteriGrubuList;
    }

    public static void setMusteriGrubuList(List<MusteriGrubu> musteriGrubuList) {
        Problem1.musteriGrubuList = musteriGrubuList;
    }
}
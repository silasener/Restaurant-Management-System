package org.example;
import org.example.Kasa.KasaThread;
import org.example.asci.AsciUret;
import org.example.garson.GarsonUret;
import org.example.musteri.MusteriThread;
import org.example.musteri.MusteriUret;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.swing.*;


public class RestoranYonetimSistemi  extends JFrame {

    private JComboBox<Integer> asciSayisiCombobox, garsonSayisiCombobox, masaSayisiCombobox;
    private static JTextArea metin;
    private static boolean[] masaDurumu;
    private static JTextArea[] garsonlar;
    private static  RestaurantListLabel bekleyenMusterilerLabel;
    private static  RestaurantListLabel odemeYapipAyrilanMusterilerLabel;
    private static  RestaurantListLabel hizmetVerilenMusterilerLabel;
    private static RestaurantListLabel beklemeSuresiDolanMusterilerLabel;
    private JPanel garsonPanel;
    private static GarsonUret garsonUret;
    private MusteriUret musteriUret;
    public static AsciUret asciUret;
    private AsciPanel asciPanel;
    private static JLabel bosMasaLabel;
    private static JLabel doluMasaLabel;
    private static JFrame kasaLog;
    private static  JTextArea kasaLogTextArea;
    private JLabel musteriSayisiLabel;
    public static JTextField musteriSayisiField;
    private JLabel toplamOdemeLabel;
    public static JTextField toplamOdemeField;
    private  JButton grupCagir;
    public static boolean oncelikliMusterilerBitirildi = false;



    public RestoranYonetimSistemi(){
        super("Restoran Yönetim Sistemi");
        JPanel panel=new JPanel();
        panel.setLayout(new GridBagLayout());

        Integer [] sayisalDegerler= {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
        asciSayisiCombobox=new JComboBox<Integer>(sayisalDegerler);
        garsonSayisiCombobox=new JComboBox<Integer>(sayisalDegerler);
        masaSayisiCombobox=new JComboBox<Integer>(sayisalDegerler);

        JLabel garsonSayisiLabel = new JLabel("Garson Sayısı:");
        comboboxEkle(panel,garsonSayisiLabel, garsonSayisiCombobox, 0, 0);
        JLabel masaSayisiLabel = new JLabel("Masa Sayısı:");
        comboboxEkle(panel, masaSayisiLabel, masaSayisiCombobox, 2, 0);
        JLabel asciSayisiLabel = new JLabel("Aşçı Sayısı:");
        comboboxEkle(panel, asciSayisiLabel, asciSayisiCombobox, 4, 0);
        JButton baslat = new JButton("BAŞLAT");
        componentEkle(panel, baslat, 6, 0, GridBagConstraints.CENTER, 2, 1);
        grupCagir = new JButton("grup çağır");
        componentEkle(panel, grupCagir, 8, 0, GridBagConstraints.CENTER, 2, 1);
        grupCagir.setEnabled(false);

        bosMasaLabel = new JLabel("Boş Masalar: ");
        componentEkle(panel,bosMasaLabel,0,3,GridBagConstraints.CENTER,2,1);
        doluMasaLabel = new JLabel("Dolu Masalar: ");
        componentEkle(panel,doluMasaLabel,5,3,GridBagConstraints.CENTER,2,1);
        ReentrantLock threadKontrolu = new ReentrantLock();
        bekleyenMusterilerLabel = new RestaurantListLabel("Bekleyen Müşteriler: ", threadKontrolu);
        componentEkle(panel,bekleyenMusterilerLabel,0,4,GridBagConstraints.CENTER,2,1);
        odemeYapipAyrilanMusterilerLabel = new RestaurantListLabel("Ödeme Yapıp Ayrılan Müşteriler: ", threadKontrolu);
        componentEkle(panel, odemeYapipAyrilanMusterilerLabel,5,6,GridBagConstraints.CENTER,2,1);
        hizmetVerilenMusterilerLabel  = new RestaurantListLabel("Hizmet Verilen Müşteriler: ", threadKontrolu);
        componentEkle(panel,hizmetVerilenMusterilerLabel,5,4,GridBagConstraints.CENTER,2,1);
        beklemeSuresiDolanMusterilerLabel=new RestaurantListLabel("Bekleme Süresi Dolup Ayrılan Müşteriler: ",threadKontrolu);
        componentEkle(panel,beklemeSuresiDolanMusterilerLabel,0,6,GridBagConstraints.CENTER,2,1);

        grupCagir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RestoranYonetimSistemi.oncelikliMusterilerBitirildi=false;
                if(Problem1.musteriGrubuList.size()>MusteriUret.musteriGrupSirasi){
                    MusteriUret.musteriGrupSirasi++;
                }
                musteriUret.musteriGrubuCagir();
                if((Problem1.musteriGrubuList.size()-1)==MusteriUret.musteriGrupSirasi){
                    grupCagir.setEnabled(false);
                }
            }
        });


       baslat .addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                if (baslat.getText().equals("BAŞLAT")) {
                    grupCagir.setEnabled(true);

                    baslat.setText("DURDUR");
                    StringBuilder bosMasalarinNumaralari = new StringBuilder("Boş Masalar: ");
                    for (int i = 1; i < getMasaNumaralari(); i++) {
                        bosMasalarinNumaralari.append(i).append(" ,");
                    }
                    bosMasalarinNumaralari.append(getMasaNumaralari()); //son masanın numarası
                    bosMasaLabel.setText(bosMasalarinNumaralari.toString());

                    masaDurumu= new boolean [getMasaNumaralari()];
                    garsonlar= new JTextArea[getGarsonNumaralari()];
                    garsonPanel= new JPanel();
                    garsonPanel.setLayout(new GridLayout(1, getGarsonNumaralari()));

                    for (int i=0; i < getGarsonNumaralari(); i++) {
                        garsonlar[i] = new JTextArea("", 10, 10);
                        JScrollPane jsp = new JScrollPane(garsonlar[i]);
                        garsonPanel.add(jsp);
                    }

                    asciPanel =new AsciPanel();

                    add(garsonPanel, BorderLayout.SOUTH);

                    Koordinasyon koordine = new Koordinasyon(getMasaNumaralari());
                    garsonUret = new GarsonUret(koordine, getGarsonNumaralari()); //garson
                    musteriUret= new MusteriUret(koordine); //müşteri
                    musteriUret.musteriGrubuCagir();
                    asciUret = new AsciUret(asciPanel,getAsciNumaralari());  //aşçı

                    kasaLog=new JFrame();
                    kasaLog.setTitle("KASA");
                    kasaLog.setSize(400, 300);
                    kasaLog.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                    kasaLogTextArea = new JTextArea("", 20, 40);
                    kasaLogTextArea.setLineWrap(false);
                    kasaLogTextArea.setFont(new Font("Arial", Font.PLAIN, 12));

                    musteriSayisiLabel = new JLabel("Ödeme Yapan Müşteri Sayısı:");
                    musteriSayisiField = new JTextField(10);
                    musteriSayisiField.setEditable(false);
                    musteriSayisiField.setText(String.valueOf(KasaThread.getOdemeYapanToplamMusteriSayisi()));

                    toplamOdemeLabel = new JLabel("Toplam Ödeme Miktarı:");
                    toplamOdemeField = new JTextField(10);
                    toplamOdemeField.setEditable(false);
                    toplamOdemeField.setText(String.valueOf(KasaThread.getSiparisTutari()));

                    JScrollPane kasaLogScrollPane = new JScrollPane(kasaLogTextArea);

                    kasaLog.setLayout(new BorderLayout());

                    JPanel panelKasa = new JPanel();
                    panelKasa.setLayout(new GridLayout(3, 2));
                    panelKasa.add(musteriSayisiLabel);
                    panelKasa.add(musteriSayisiField);
                    panelKasa.add(toplamOdemeLabel);
                    panelKasa.add(toplamOdemeField);

                    kasaLog.add(panelKasa, BorderLayout.NORTH);
                    kasaLog.add(kasaLogScrollPane, BorderLayout.CENTER);

                    kasaLog.setVisible(true);


                }else if (baslat.getText().equals("DURDUR")) {
                    musteriUret.interrupt();
                    baslat.setText("BAŞLAT");
                }
            }
       });


        metin= new JTextArea("", 20, 40); //bir sayfada 20 satır , 40 sütun(karakter) görüntülemeyi sağlar
        metin.setLineWrap(false); //yeni satıra geçip geçmemeyi belirler
        metin.setFont(new Font("Arial",Font.PLAIN, 12));
        JScrollPane kaydirilabilirMetin = new JScrollPane(metin); // metin'in kaydırılabilir bir panel içinde görüntülenmesi sağlanır

        add(panel, BorderLayout.NORTH);
        add(kaydirilabilirMetin, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 500);
        setVisible(true);
    }



    private void comboboxEkle(JPanel panel, JLabel label, JComboBox<Integer> jcb, int x, int y) {
        componentEkle(panel, label,x, y, GridBagConstraints.EAST, 1, 1);
        componentEkle(panel, jcb, x + 1,y, GridBagConstraints.WEST, 1, 1);
    }

    private void componentEkle(JPanel panel, JComponent jc, int x, int y, int hizza, int gridwidth, int gridheight) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.ipadx = 3;
        gbc.ipady = 3;
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.anchor = hizza;
        gbc.gridwidth = gridwidth;
        gbc.gridheight = gridheight;
        gbc.insets = new Insets(5, 5, 0, 0);
        panel.add(jc, gbc);
    }


    public int getMasaNumaralari() {return masaSayisiCombobox.getItemAt(masaSayisiCombobox.getSelectedIndex());}

    public int getGarsonNumaralari() {return garsonSayisiCombobox.getItemAt(garsonSayisiCombobox.getSelectedIndex());}

    public int getAsciNumaralari() {return asciSayisiCombobox.getItemAt(asciSayisiCombobox.getSelectedIndex());}


    public static void mesajEkle(String msg) {
        String text = metin.getText();
        if (text == null || text.length() == 0) {
            metin.setText(msg);
        }
        else {
            metin.setText(metin.getText() + "\n" + msg);
        }
    }

    public static void kasaMesajEkle(String msg) {
        String text = kasaLogTextArea.getText();
        if (text == null || text.length() == 0) {
            kasaLogTextArea.setText(msg);
        }
        else {
            kasaLogTextArea.setText(kasaLogTextArea.getText() + "\n" + msg);
        }
    }


    public static void bekleyenMusteriEkle(int musteriNumarasi){bekleyenMusterilerLabel.add(musteriNumarasi);}

    public static GarsonUret getGarsonUret() {return garsonUret;}

    public static void hizmetVerilenMusteriEkle(int musteriNumarasi){
        bekleyenMusterilerLabel.remove(musteriNumarasi);
        hizmetVerilenMusterilerLabel.add(musteriNumarasi);
    }

    public static void beklemeSuresiDolanMusteriEkle(int musteriNumarasi){
        bekleyenMusterilerLabel.remove(musteriNumarasi);
        beklemeSuresiDolanMusterilerLabel.add(musteriNumarasi);
    }

    public static void garsonMesajiEkle(String mesaj, int garsonNumarasi) {
        String text = garsonlar[garsonNumarasi].getText();
        if (text == null || text.length() == 0) {
            garsonlar[garsonNumarasi].setText(mesaj);
        }
        else {
            garsonlar[garsonNumarasi].setText(garsonlar[garsonNumarasi].getText() + "\n" + mesaj);
        }
    }

    public static void musteriAyrilacakLabelGuncelle(int musteriNumarasi){
        hizmetVerilenMusterilerLabel.remove(musteriNumarasi);
        odemeYapipAyrilanMusterilerLabel.add(musteriNumarasi);
    }

    public static void masaDurumlariniGuncelle(int masaNumarasi) {
        masaDurumu[masaNumarasi] = !masaDurumu[masaNumarasi];
        StringBuilder bosMasaBuilder = new StringBuilder("Boş Masalar: ");
        StringBuilder doluMasaBuilder = new StringBuilder("Dolu Masalar: ");

        for (int i = 0; i < masaDurumu.length; i++) {
            if (!masaDurumu[i]) {bosMasaBuilder.append(i + ", ");
            }else{doluMasaBuilder.append(i + ", ");}
        }

        if (bosMasaBuilder.toString().contains(",")) {
            bosMasaBuilder.replace(bosMasaBuilder.lastIndexOf(","), bosMasaBuilder.lastIndexOf(",") + 1, "");
        }
        if (doluMasaBuilder.toString().contains(",")) {
            doluMasaBuilder.replace(doluMasaBuilder.lastIndexOf(","), doluMasaBuilder.lastIndexOf(",") + 1, "");
        }
        bosMasaLabel.setText(bosMasaBuilder.toString());
        doluMasaLabel.setText(doluMasaBuilder.toString());
    }

    public static List<MusteriThread> bekleyenMusteriler() {
        List<Integer> musteriNumaralari = new ArrayList<>();

        String bekleyenMusterilerText = bekleyenMusterilerLabel.getText();

        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(bekleyenMusterilerText);

        while (matcher.find()) {
            int numara = Integer.parseInt(matcher.group());
            musteriNumaralari.add(numara);
        }
        List<MusteriThread> bekleyenMusterilerList=MusteriUret.musteriThreadVectorleri.stream().filter(musteriThread -> musteriNumaralari.contains(musteriThread.getMusteriNumarasi())).collect(Collectors.toList());
        return  bekleyenMusterilerList;
    }



}
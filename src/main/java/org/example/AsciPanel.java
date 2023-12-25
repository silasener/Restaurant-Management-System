package org.example;

import javax.swing.*;
import java.awt.*;


public class AsciPanel extends JPanel {
    private static JTextArea asciPanelMesajlari;

    AsciPanel() {
        JFrame asciFrame = new JFrame();
        asciFrame.setTitle("AŞÇI");
        asciFrame.setSize(400, 500);
        asciPanelMesajlari = new JTextArea("", 20, 40);
        asciPanelMesajlari.setLineWrap(false);
        asciPanelMesajlari.setFont(new Font("Arial", Font.PLAIN, 12));
        JScrollPane asciPanelJSP = new JScrollPane(asciPanelMesajlari);
        asciFrame.add(asciPanelJSP);
        asciFrame.setVisible(true);
    }

    public void asciMesajiEkle(String mesaj) {
        asciPanelMesajlari.append(mesaj + "\n");
    }
}

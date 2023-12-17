package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GirisEkrani extends JFrame {
    private JButton button1;
    private JButton button2;

    public GirisEkrani() {
        setTitle("-PROBLEMLER-");
        setSize(1500, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel leftPanel = new JPanel(new BorderLayout());
        ImageIcon imageIcon1 = new ImageIcon("/Users/silasener/Desktop/foto1.png");
        Image image1 = imageIcon1.getImage().getScaledInstance(500, 700, Image.SCALE_SMOOTH);
        ImageIcon scaledImageIcon1 = new ImageIcon(image1);
        JLabel label1 = new JLabel(scaledImageIcon1);
        leftPanel.add(label1, BorderLayout.CENTER);
        mainPanel.add(leftPanel, BorderLayout.WEST);

        JPanel rightPanel = new JPanel(new BorderLayout());
        ImageIcon imageIcon2 = new ImageIcon("/Users/silasener/Desktop/foto2.png");
        Image image2 = imageIcon2.getImage().getScaledInstance(500, 700, Image.SCALE_SMOOTH);
        ImageIcon scaledImageIcon2 = new ImageIcon(image2);
        JLabel label2 = new JLabel(scaledImageIcon2);
        rightPanel.add(label2, BorderLayout.CENTER);
        mainPanel.add(rightPanel, BorderLayout.EAST);

        button1 = new JButton("Problem 1");
        button1.setBounds(600, 250, 200, 100);
        button2 = new JButton("Problem 2");
        button2.setBounds(600, 400, 200, 100);

        add(button1, BorderLayout.CENTER);
        add(button2, BorderLayout.CENTER);
        add(mainPanel);
        setVisible(true);


        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Problem1 problem1 = new Problem1();
            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Problem2 problem2 = new Problem2();
            }
        });
    }


    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException
                 | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        new GirisEkrani();
    }
}

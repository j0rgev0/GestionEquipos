package org.example;


import com.formdev.flatlaf.FlatDarkLaf;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.Window;

import javax.swing.*;

public class SplashScreen extends JDialog {

    private JProgressBar progressBar;

    public SplashScreen() {

        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
            UIManager.put("Button.arc", 30);

        } catch (Exception e) {
            e.printStackTrace();
        }


        setSize(400,250);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setUndecorated(true);
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        JLabel logoJLabel = new JLabel(new ImageIcon("img/ball.gif"), JLabel.CENTER);
        logoJLabel.setForeground(new Color(0,255,0));

        JLabel messageLabel = new JLabel("cargando recursos, por favor espere ",JLabel.CENTER);
        messageLabel.setFont(new Font("Arial",Font.PLAIN,14));
        messageLabel.setForeground(new Color(0,255,0));

        progressBar = new JProgressBar(0,100);
        progressBar.setStringPainted(true);
        progressBar.setForeground(new Color(0,255,0));


        mainPanel.add(logoJLabel,BorderLayout.CENTER);
        mainPanel.add(messageLabel,BorderLayout.SOUTH);


        add(mainPanel,BorderLayout.CENTER);
        add(progressBar, BorderLayout.SOUTH);

    }

    public void updateProgress(int value) {
        progressBar.setValue(value);

    }

}
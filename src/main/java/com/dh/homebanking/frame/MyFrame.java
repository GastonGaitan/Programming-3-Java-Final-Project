package com.dh.homebanking.frame;

import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame {
    public MyFrame(){
        this.setTitle("Homebanking Application");
        this.setSize(1100, 650);
        this.setResizable(false);
        this.setVisible(true);
        ImageIcon image = new ImageIcon("src\\main\\java\\com\\dh\\homebanking\\logoimg\\logo.png");
        this.setIconImage(image.getImage());
        System.out.println("Directorio de trabajo actual: " + System.getProperty("user.dir"));
        this.getContentPane().setBackground(new Color(48, 51,73));

        JLabel label = new JLabel();
        label.setText("Bro, do you code?");
    }
}

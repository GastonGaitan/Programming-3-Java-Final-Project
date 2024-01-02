package com.dh.homebanking.defaults;

import javax.swing.*;
import java.awt.*;

public class Defaults {
    public  static ImageIcon getResizeImage(String path, int width, int height){
        return new ImageIcon(
                new ImageIcon(path).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH)
        );
    }
}



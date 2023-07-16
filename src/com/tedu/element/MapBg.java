package com.tedu.element;

import com.tedu.show.GameJFrame;

import javax.swing.*;
import java.awt.*;

public class MapBg extends ElementObj {
    @Override
    public void showElement(Graphics g) {
        float imgRt = (float)GameJFrame.contentWidth/GameJFrame.contentHeight;
        g.drawImage(this.getIcon().getImage(),
                0, 0, GameJFrame.contentWidth,GameJFrame.contentHeight,
                this.getX(), 0,
                this.getX() + (int)(this.getIcon().getIconHeight()*imgRt),
                this.getIcon().getIconHeight(),
                null);
    }

    @Override
    public ElementObj createElement(String str) {
        this.setX(0);
        this.setIcon(new ImageIcon(str));
        return this;
    }
}

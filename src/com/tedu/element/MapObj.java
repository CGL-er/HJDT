package com.tedu.element;

import com.tedu.manager.GameElement;

import javax.swing.*;
import java.awt.*;

public class MapObj extends ElementObj{
    @Override
    public void showElement(Graphics g){
        g.drawImage(this.getIcon().getImage(),
                this.getX(), this.getY(),
                this.getH(), this.getW(), null);
    }
    @Override
    public void bePk(GameElement tar, ElementObj b){
        if(this.getHp()==-100)
            return;
        if (tar==GameElement.PLAYFIRE)
            setLive(false);
    }
    @Override
    public ElementObj createElement(String str){
        String []arr = str.split(",");
        ImageIcon icon = null;
        switch (arr[0]){
            case "GRASS":
                icon = new ImageIcon("image/wall/grass.png");
                break;
            case "BRICK":
                icon = new ImageIcon("image/wall/brick.png");
                break;
            case "RIVER":
                icon = new ImageIcon("image/wall/ricer.png");
                this.setHp(-100);
                break;
            case "IRON":
                icon = new ImageIcon("image/wall/iron.png");
                this.setHp(-100);
                break;
        }
        this.setX(Integer.parseInt(arr[1]));
        this.setY(Integer.parseInt(arr[2]));
        this.setW(icon.getIconWidth());
        this.setH(icon.getIconHeight());
        this.setIcon(icon);
        return this;
    }
}

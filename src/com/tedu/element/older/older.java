package com.tedu.element.older;

import com.tedu.element.ElementObj;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public class older extends ElementObj {

    static ArrayList<ImageIcon> icons ;
    private int currentIndex = 0;
    private int iconMaxIndex = 0;
    private Queue<olderStatus> queue= olderStatus.getQueue();
    static Map<olderStatus,ArrayList<ImageIcon>> iconsMap;
    private olderStatus status = olderStatus.LABA;
    static {
        iconsMap = new HashMap<>();
        icons = new ArrayList<>();
        icons.add(new ImageIcon("image/oder/oder0.png"));
        icons.add(new ImageIcon("image/oder/oder1.png"));
        icons.add(new ImageIcon("image/oder/oder2.png"));
        icons.add(new ImageIcon("image/oder/oder3.png"));
        icons.add(new ImageIcon("image/oder/oder4.png"));
        icons.add(new ImageIcon("image/oder/oder5.png"));
        icons.add(new ImageIcon("image/oder/oder6.png"));
        icons.add(new ImageIcon("image/oder/oder7.png"));
        icons.add(new ImageIcon("image/oder/oder8.png"));
        icons.add(new ImageIcon("image/oder/oder9.png"));
        iconsMap.put(olderStatus.LABA,icons);
        icons = new ArrayList<>();
        icons.add(new ImageIcon("image/oder/oder10.png"));
        icons.add(new ImageIcon("image/oder/oder11.png"));
        icons.add(new ImageIcon("image/oder/oder12.png"));
        icons.add(new ImageIcon("image/oder/oder13.png"));
        icons.add(new ImageIcon("image/oder/oder14.png"));
        iconsMap.put(olderStatus.JIETUO,icons);
        icons = new ArrayList<>();
        icons.add(new ImageIcon("image/oder/oder15.png"));
        icons.add(new ImageIcon("image/oder/oder16.png"));
        icons.add(new ImageIcon("image/oder/oder17.png"));
        icons.add(new ImageIcon("image/oder/oder18.png"));
        icons.add(new ImageIcon("image/oder/oder15.png"));
        icons.add(new ImageIcon("image/oder/oder16.png"));
        icons.add(new ImageIcon("image/oder/oder17.png"));
        icons.add(new ImageIcon("image/oder/oder18.png"));
        icons.add(new ImageIcon("image/oder/oder15.png"));
        icons.add(new ImageIcon("image/oder/oder16.png"));
        icons.add(new ImageIcon("image/oder/oder17.png"));
        icons.add(new ImageIcon("image/oder/oder18.png"));
        iconsMap.put(olderStatus.JUMP,icons);
        icons = new ArrayList<>();
        icons.add(new ImageIcon("image/oder/oder19.png"));
        icons.add(new ImageIcon("image/oder/oder20.png"));
        icons.add(new ImageIcon("image/oder/oder21.png"));
        icons.add(new ImageIcon("image/oder/oder22.png"));
        icons.add(new ImageIcon("image/oder/oder23.png"));
        icons.add(new ImageIcon("image/oder/oder19.png"));
        icons.add(new ImageIcon("image/oder/oder20.png"));
        icons.add(new ImageIcon("image/oder/oder21.png"));
        icons.add(new ImageIcon("image/oder/oder22.png"));
        icons.add(new ImageIcon("image/oder/oder23.png"));
        icons.add(new ImageIcon("image/oder/oder19.png"));
        icons.add(new ImageIcon("image/oder/oder20.png"));
        icons.add(new ImageIcon("image/oder/oder21.png"));
        icons.add(new ImageIcon("image/oder/oder22.png"));
        icons.add(new ImageIcon("image/oder/oder23.png"));
        iconsMap.put(olderStatus.RUN1,icons);
        icons = new ArrayList<>();
        icons.add(new ImageIcon("image/oder/oder24.png"));
        icons.add(new ImageIcon("image/oder/oder25.png"));
        icons.add(new ImageIcon("image/oder/oder26.png"));
        icons.add(new ImageIcon("image/oder/oder27.png"));
        icons.add(new ImageIcon("image/oder/oder28.png"));
        icons.add(new ImageIcon("image/oder/oder29.png"));
        icons.add(new ImageIcon("image/oder/oder30.png"));
        icons.add(new ImageIcon("image/oder/oder27.png"));
        icons.add(new ImageIcon("image/oder/oder28.png"));
        icons.add(new ImageIcon("image/oder/oder29.png"));
        icons.add(new ImageIcon("image/oder/oder30.png"));
        icons.add(new ImageIcon("image/oder/oder27.png"));
        icons.add(new ImageIcon("image/oder/oder28.png"));
        icons.add(new ImageIcon("image/oder/oder29.png"));
        icons.add(new ImageIcon("image/oder/oder30.png"));
        iconsMap.put(olderStatus.UP,icons);
        icons = new ArrayList<>();
        icons.add(new ImageIcon("image/oder/oder31.png"));
        icons.add(new ImageIcon("image/oder/oder32.png"));
        icons.add(new ImageIcon("image/oder/oder33.png"));
        icons.add(new ImageIcon("image/oder/oder34.png"));
        icons.add(new ImageIcon("image/oder/oder35.png"));
        icons.add(new ImageIcon("image/oder/oder36.png"));
        icons.add(new ImageIcon("image/oder/oder37.png"));
        icons.add(new ImageIcon("image/oder/oder35.png"));
        icons.add(new ImageIcon("image/oder/oder36.png"));
        icons.add(new ImageIcon("image/oder/oder37.png"));
        icons.add(new ImageIcon("image/oder/oder35.png"));
        icons.add(new ImageIcon("image/oder/oder36.png"));
        icons.add(new ImageIcon("image/oder/oder37.png"));
        icons.add(new ImageIcon("image/oder/oder31.png"));
        icons.add(new ImageIcon("image/oder/oder32.png"));
        icons.add(new ImageIcon("image/oder/oder33.png"));
        icons.add(new ImageIcon("image/oder/oder34.png"));
        icons.add(new ImageIcon("image/oder/oder35.png"));
        icons.add(new ImageIcon("image/oder/oder36.png"));
        icons.add(new ImageIcon("image/oder/oder37.png"));
        icons.add(new ImageIcon("image/oder/oder35.png"));
        icons.add(new ImageIcon("image/oder/oder36.png"));
        icons.add(new ImageIcon("image/oder/oder37.png"));
        icons.add(new ImageIcon("image/oder/oder35.png"));
        icons.add(new ImageIcon("image/oder/oder36.png"));
        icons.add(new ImageIcon("image/oder/oder37.png"));
        iconsMap.put(olderStatus.RUN2,icons);
    }
    private int time = 0;
    private int index = 0;
    @Override
    public ElementObj createElement(String str) {
        this.setIcon(icons.get(0));
        this.setY(280);
        this.setX(600);
        this.setH(icons.get(0).getIconHeight());
        this.setW(icons.get(0).getIconWidth());
        this.status = queue.poll();
        this.iconMaxIndex = iconsMap.get(status).size();
        this.currentIndex = 0;
        return this;
    }

    @Override
    protected void updateImage(long gameTime) {
        if(time >= 8){
            time = 0;
            ImageIcon icon =  this.getNextIcon();
            if(icon != null){
                this.setIcon(icon);
                if(this.status == olderStatus.RUN1 || this.status == olderStatus.RUN2){
                    this.setX(this.getX() - 10);
                }
            }
        }else{
            time++;
        }
    }
    private ImageIcon getNextIcon(){
        if(currentIndex >= iconMaxIndex){
            changeStatus();
        }
        if(this.isLive()) return iconsMap.get(status).get(currentIndex++);
        else return null;
    }
    private void changeStatus(){
        if(queue.isEmpty()){
            this.setLive(false);
            return;
        }
        this.status = queue.poll();
        this.iconMaxIndex = iconsMap.get(status).size();
        this.currentIndex = 0;
    }
    @Override
    public void showElement(Graphics g) {
        g.drawImage(this.getIcon().getImage(),this.getX(),this.getY(),this.getW(),this.getH(),null);
    }

}

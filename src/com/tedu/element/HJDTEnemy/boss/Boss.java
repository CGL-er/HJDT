package com.tedu.element.HJDTEnemy.boss;

import com.tedu.element.ElementObj;
import com.tedu.element.HJDTEnemy.soldier.DeadType;
import jdk.internal.org.objectweb.asm.tree.InsnList;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Boss extends ElementObj {
    private Long myLiveTime = 0L;
    private int curIconIndex = 0;
    private static ArrayList<ImageIcon> iconList = new ArrayList<>();
    static {
        String url = "image/die/";
        DeadMap = new HashMap<>();
        List<ImageIcon> list = new ArrayList<>();
        list.add(new ImageIcon(url+"0 (1).png"));
        list.add(new ImageIcon(url+"0 (2).png"));
        list.add(new ImageIcon(url+"0 (3).png"));
        list.add(new ImageIcon(url+"0 (4).png"));
        list.add(new ImageIcon(url+"0 (5).png"));
        DeadMap.put(DeadType.TYPE0,list);
        list = new ArrayList<>();
        list.add(new ImageIcon(url+"1 (1).png"));
        list.add(new ImageIcon(url+"1 (2).png"));
        list.add(new ImageIcon(url+"1 (3).png"));
        list.add(new ImageIcon(url+"1 (4).png"));
        list.add(new ImageIcon(url+"1 (5).png"));
        DeadMap.put(DeadType.TYPE1,list);
        list = new ArrayList<>();
        list.add(new ImageIcon(url+"2 (1).png"));
        list.add(new ImageIcon(url+"2 (2).png"));
        list.add(new ImageIcon(url+"2 (3).png"));
        list.add(new ImageIcon(url+"2 (4).png"));
        list.add(new ImageIcon(url+"2 (5).png"));
        DeadMap.put(DeadType.TYPE2,list);
    }
    @Override
    protected void updateImage(long gameTime) {
        if(gameTime-myLiveTime>100){
            myLiveTime = gameTime;
            this.curIconIndex++;
            if(this.curIconIndex>=this.iconList.size()){
                this.curIconIndex = 0;
            }
            this.setIcon(this.iconList.get(this.curIconIndex));
        }
    }

    @Override
    public void showElement(Graphics g) {
        g.drawImage(this.getIcon().getImage(),this.getX(),this.getY(),this.getW(),this.getH(),null);
    }
}

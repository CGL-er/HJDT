package com.tedu.element.HJDTEnemy.soldier;

import com.tedu.element.ElementObj;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class DeadEnemy extends ElementObj {
    private static Map<DeadType, List<ImageIcon>> DeadMap;
    private Long myLiveTime = 0L;
    public boolean direction;
    private int curIconIndex = -1;
    private DeadType deadType;
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
    List<ImageIcon> iconList;
    @Override
    public ElementObj createElement(String type) {

        String[] split = type.split(",");
        this.direction = Boolean.parseBoolean(split[0]);
        this.setX(Integer.parseInt(split[1]));
        this.setY(Integer.parseInt(split[2]));
        this.deadType = DeadType.values()[Integer.parseInt(split[3])];

        this.iconList = DeadMap.get(deadType);

        this.setW(iconList.get(0).getIconWidth());
        this.setH(iconList.get(0).getIconHeight());
        this.setIcon(iconList.get(0));
//        System.out.println("我死了");
        return this;
    }

    @Override
    public String toString() {
        return "DeadEnemy{" +
                "myLiveTime=" + myLiveTime +
                ", direction=" + direction +
                ", curIconIndex=" + curIconIndex +
                ", deadType=" + deadType +
                ", iconList=" + iconList +
                "W" + this.getW() +
                "H" + this.getH() +
                "icon" + this.getIcon() +
                '}';
    }

    @Override
    protected void updateImage(long gametime) {
        super.updateImage(gametime);
        if(this.getIcon() == null){
            this.setIcon(iconList.get(0));
            this.setW(this.getIcon().getIconWidth());
            this.setH(this.getIcon().getIconHeight());
        }
//        System.out.println(this.getIcon());
        if(gametime-myLiveTime>15){
//            System.out.println(iconList.size());
            myLiveTime = gametime;
            if(curIconIndex + 1<iconList.size()){
                this.setIcon(iconList.get(++curIconIndex));
                this.setW(this.getIcon().getIconWidth());
                this.setH(this.getIcon().getIconHeight());
                }
            else{
                this.setLive(false);
            }
        }
    }

    public static ImageIcon flipImage(ImageIcon image) {
        //    左右翻转图片
        Image img = image.getImage();
        int width = img.getWidth(null);
        int height = img.getHeight(null);
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bufferedImage.createGraphics();
        g.drawImage(img, 0, 0, width, height, width, 0, 0, height, null);
        g.dispose();
        return new ImageIcon(bufferedImage);
    }

    @Override
    public void showElement(Graphics g) {
        g.drawImage(this.getIcon().getImage(),this.getX(),this.getY(),this.getW(),this.getH(),null);
    }

}

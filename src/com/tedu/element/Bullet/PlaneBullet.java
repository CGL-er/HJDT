package com.tedu.element.Bullet;

import com.tedu.element.ElementObj;
import com.tedu.manager.GameElement;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PlaneBullet extends ElementObj{
    private int speed = 10;
    private int index = 0;
    private int time = 0;
    private int deadTime = 0;
    private boolean isDead = false;
    private boolean Boomed = false;
    private static ArrayList<ImageIcon> imageIcons = new ArrayList<>();
    private static ArrayList<ImageIcon> deadIcons = new ArrayList<>();
    ImageIcon deadIcon = new ImageIcon("image/boom/boom.png");
    static {
        imageIcons.add(new ImageIcon("image/bullet/plane_bomb0.png"));
        imageIcons.add(new ImageIcon("image/bullet/plane_bomb1.png"));
        imageIcons.add(new ImageIcon("image/bullet/plane_bomb2.png"));
        imageIcons.add(new ImageIcon("image/bullet/plane_bomb3.png"));

        deadIcons.add(new ImageIcon("image/boom/bomb_bang0.png"));
        deadIcons.add(new ImageIcon("image/boom/bomb_bang1.png"));
        deadIcons.add(new ImageIcon("image/boom/bomb_bang2.png"));
        deadIcons.add(new ImageIcon("image/boom/bomb_bang3.png"));
        deadIcons.add(new ImageIcon("image/boom/bomb_bang4.png"));
        deadIcons.add(new ImageIcon("image/boom/bomb_bang5.png"));
        deadIcons.add(new ImageIcon("image/boom/bomb_bang6.png"));
        deadIcons.add(new ImageIcon("image/boom/bomb_bang7.png"));
        deadIcons.add(new ImageIcon("image/boom/bomb_bang8.png"));
        deadIcons.add(new ImageIcon("image/boom/bomb_bang9.png"));
        deadIcons.add(new ImageIcon("image/boom/bomb_bang10.png"));
        deadIcons.add(new ImageIcon("image/boom/bomb_bang11.png"));
        deadIcons.add(new ImageIcon("image/boom/bomb_bang12.png"));
        deadIcons.add(new ImageIcon("image/boom/bomb_bang13.png"));
        deadIcons.add(new ImageIcon("image/boom/bomb_bang14.png"));
        deadIcons.add(new ImageIcon("image/boom/bomb_bang15.png"));
        deadIcons.add(new ImageIcon("image/boom/bomb_bang16.png"));
    }

    @Override
    public void bePk(GameElement tar, ElementObj b) {
        if(Boomed && !isDead){
            this.setAttack(0);
            dead();
        }
    }
    private void dead(){
        this.isDead = true;
        this.setIcon(deadIcons.get(0));
        this.setX(this.getX()-(this.getW()-deadIcon.getIconWidth())/2);
        this.setY(this.getY()-(this.getH()-deadIcon.getIconHeight())-30);
        this.setH(this.getIcon().getIconHeight());
        this.setW(this.getIcon().getIconWidth());
    }
    @Override
    public ElementObj createElement(String str) {
        String str1[] = str.split(",");
        for(int i = 0;i<str1.length;i++){
            String str2[] = str1[i].split(":");
            switch (str2[0]){
                case "x":
                    this.setX(Integer.parseInt(str2[1]));
                    break;
                case "y":
                    this.setY(Integer.parseInt(str2[1]));
                    break;
            }
        }
        this.setIcon(new ImageIcon("image/bullet/plane_bomb0.png"));
//        System.out.println(this.getIcon().getIconWidth());
        this.setW(this.getIcon().getIconWidth());
        this.setH(this.getIcon().getIconHeight());
        this.setAttack(0);
        return this;
    }

    @Override
    protected void updateImage(long gameTime) {
        if(this.getY()>300 && !this.isDead && !Boomed){
            this.setAttack(10);
            Boomed = true;
        }
        if(this.isDead){
            this.setAttack(0);
            deadTime++;
            this.setIcon(deadIcons.get(deadTime/3));
            if(deadTime >= 16*3+1){
                die();
            }
        }
    }

    @Override
    public void move(long gametime) {
        if(this.getY() > 350){
            dead();
        }
        if(this.isDead){
            return;
        }
        if(this.time >= 4){
            this.time = 0;
            this.index++;
            if(this.index >= imageIcons.size()){
                this.index = 0;
            }
            this.setY(this.getY()+10);
            this.setIcon(imageIcons.get(index));
        }else {
            this.time++;
        }
    }

    @Override
    public void die() {
        this.setLive(false);

    }

    @Override
    public void showElement(Graphics g) {
        g.drawImage(this.getIcon().getImage(),this.getX(),this.getY(),this.getW(),this.getH(),null);
    }
}

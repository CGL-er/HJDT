package com.tedu.element;

import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoad;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

public class fireBang extends ElementObj{
    private Direction fx;
    private long curTime;
    private int curIndex = 0;
    private int imgLen;
    private Action state;
    private int x;
    private int attack = 1;

    public int getAttack() {
        return attack;
    }

    @Override
    public ElementObj createElement(String str){
        String[] split = str.split(",");
        boolean isEnemy = false;
        for (String str1:split){
            String[] split2=str1.split(":");
            switch (split2[0]){
                case "x":
                    this.setX(Integer.parseInt(split2[1]));
                    x = this.getX();
                    break;
                case "y":
                    this.setY(Integer.parseInt(split2[1]));
                    break;
                case "fx":
                    this.fx = Direction.valueOf(split2[1]);
                    break;
                case "enemy":
                    isEnemy = true;
                    this.attack=0;
                    break;
            }
        }
        if(isEnemy){
            this.state = Action.enemy_bang;
        }else {
            if(fx == Direction.left){
                this.state = Action.showFire_bang_left;
            }
            else {
                this.state = Action.showFire_bang_right;
            }
        }
        setIcon(GameLoad.imgMaps.get(state).get(0));
        imgLen = GameLoad.imgMaps.get(state).size();
        this.setW(this.getIcon().getIconWidth());
        this.setH(this.getIcon().getIconHeight());

        java.util.Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                die();
            }
        };
        // 使用schedule方法指定任务的延迟时间
        timer.schedule(task, 480);
        return this;
    }
    @Override
    public void die(){
        setLive(false);
    }
    @Override
    protected void updateImage(long gameTime){
        if(gameTime-curTime>6){
//            System.out.println(1);
            this.setIcon(GameLoad.imgMaps.get(state).get((++curIndex)%imgLen));
            if(fx == Direction.left){
                this.setIcon(flipImage(this.getIcon()));
                this.setX(x-this.getW());
            }
            curTime = gameTime;
        }
    }
    private ImageIcon flipImage(ImageIcon image) {
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
    public void showElement(Graphics g){
        g.drawImage(getIcon().getImage(), getX(), getY(), getW(), getH(), null);
    }
}

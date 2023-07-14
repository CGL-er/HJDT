package com.tedu.element;

import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoad;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class fireBang extends ElementObj{
    private Direction fx;
    private long curTime;
    private int curIndex = 0;
    private int imgLen;
    private Action state;
    @Override
    public ElementObj createElement(String str){
        String[] split = str.split(",");
        for (String str1:split){
            String[] split2=str1.split(":");
            switch (split2[0]){
                case "x":
                    this.setX(Integer.parseInt(split2[1])-this.getW()/2);
                    break;
                case "y":
                    this.setY(Integer.parseInt(split2[1])-this.getH()/2);
                    break;
                case "fx":
                    this.fx = Direction.valueOf(split2[1]);
            }
        }
        if(fx == Direction.left){
            this.state = Action.showFire_bang_left;
        }
        else {
            this.state = Action.showFire_bang_right;
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
            System.out.println(1);
            this.setIcon(GameLoad.imgMaps.get(state).get((++curIndex)%imgLen));
            curTime = gameTime;
        }
    }
    @Override
    public void showElement(Graphics g){
        g.drawImage(getIcon().getImage(), getX(), getY(), getW(), getH(), null);

    }
}

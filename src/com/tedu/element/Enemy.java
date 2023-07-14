package com.tedu.element;

import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoad;
import com.tedu.show.GameJFrame;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Random;

public class Enemy extends ElementObj{

    private boolean left=false;
    private boolean up=false;
    private boolean right=false;
    private boolean down=false;
    //图片集合，使用map村粗，枚举类型配合移动（拓展）
    private int speed=5;
    private String sign;
    private Direction fx;

    @Override
    public void showElement(Graphics g){
//        System.out.println(this.getIcon());
//        System.out.println(this.getX());
//        System.out.println(this.getY());
//        System.out.println(this.getW());
//        System.out.println(this.getH());
        g.drawImage(this.getIcon().getImage(), this.getX(), this.getY(), this.getW(), this.getH(), null);
    }
    @Override
    public void bePk(GameElement tar){
//        System.out.println(this.getHp());

        if(tar==GameElement.PLAYFIRE){
            this.setHp(this.getHp()-new PlayFile().getAttack());
        }else {
            setAntiDirection();
        }
        if(this.getHp()<=0){
            if (isLive()){
                setLive(false);
                Panel.setScore(Panel.getScore()+1);
            }
        }
    }
    public void setAntiDirection() {
        switch (fx) {
            case left:
                this.right = false;
                this.left = false;
                this.down = false;
                this.up = false;

                this.right = true;
                this.fx = Direction.right;
                break;
            case up:
                this.right = false;
                this.left = false;
                this.down = false;
                this.up = false;
                this.down = true;
                this.fx = Direction.down;
                break;
            case right:
                this.right = false;
                this.left = false;
                this.down = false;
                this.up = false;
                this.left = true;
                this.fx = Direction.left;
                break;
            case down:
                this.right = false;
                this.left = false;
                this.down = false;
                this.up = false;
                this.up = true;
                this.fx = Direction.up;
                break;
        }
    }
    protected void updateImage(){
//        this.setIcon(GameLoad.imgMaps.get(ImgState.valueOf(fx.toString())));
    }
    public void setRandDirection(){
        Direction[] directions = Direction.values();
        Random random = new Random();
        Direction direction = directions[random.nextInt(Direction.values().length)];
        if (direction==this.fx){
            setRandDirection();
            return;
        }else {
            switch (direction) {
                case left:
                    this.right = false;
                    this.left =false;
                    this.down=false;
                    this.up=false;

                    this.left = true;
                    this.fx = Direction.left;
                    break;
                case up:
                    this.right = false;
                    this.left =false;
                    this.down=false;
                    this.up=false;
                    this.up = true;
                    this.fx = Direction.up;
                    break;
                case right:
                    this.right = false;
                    this.left =false;
                    this.down=false;
                    this.up=false;
                    this.right = true;
                    this.fx = Direction.right;
                    break;
                case down:
                    this.right = false;
                    this.left =false;
                    this.down=false;
                    this.up=false;
                    this.down = true;
                    this.fx = Direction.down;
                    break;
            }
        }
    }

    private long lastTime;
    @Override
    public void move(long gameTime){
        if (speed==0)
            return;
        int curX = this.getX();
        int curY = this.getY();

        if (curY >= GameJFrame.contentHeight-this.getIcon().getIconHeight() ||
                curX>= GameJFrame.contentWidth-this.getIcon().getIconWidth() ||
                curY<=0 ||
                curX<=0){
            setAntiDirection();
        }


        if(this.left && curX>0)
            this.setX(curX-speed);
        if(this.up && curY>0)
            this.setY(curY-speed);
        if(this.right && curX< GameJFrame.contentWidth-this.getIcon().getIconWidth())
            this.setX(curX+speed);
        if(this.down && curY < GameJFrame.contentHeight-this.getIcon().getIconHeight())
            this.setY(curY+speed);

        // 撞墙则不走
        ElementManager em=ElementManager.getManager();
        List<ElementObj> maps =  em.getElementsByKey(GameElement.MAPS);
        for(ElementObj i:maps){
            if(i.pk(this)){
                setAntiDirection();
                return;
            }
        }
        List<ElementObj> enemys =  em.getElementsByKey(GameElement.ENEMY);
        for(ElementObj i:enemys){
            Enemy t = (Enemy)i;
            if (t.sign.equals(this.sign))
                continue;
            if(i.pk(this)){
                setAntiDirection();
                return;
            }
        }
        if(gameTime-lastTime>20){
            lastTime = gameTime;
            setRandDirection();
        }
    }
    @Override
    public ElementObj createElement(String str){
        String[] split = str.split(",");
        this.setX(new Integer(split[0]));
        this.setY(new Integer(split[1]));
//        ImageIcon icon = GameLoad.imgMaps.get(ImgState.valueOf(split[2]));
        this.sign = split[2].split("_")[1];
        if(split[3].equals("0")){
            this.speed = 0;
            System.out.println("sepp");
        }
//        this.setW(icon.getIconWidth());
//        this.setH(icon.getIconHeight());
        this.setW(20);
        this.setH(20);
        this.setHp(1);
//        this.setIcon(icon);
        this.setRandDirection();
        return this;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}

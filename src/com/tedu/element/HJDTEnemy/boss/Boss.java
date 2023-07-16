package com.tedu.element.HJDTEnemy.boss;

import com.tedu.controller.GameThread;
import com.tedu.element.Action;
import com.tedu.element.ElementObj;
import com.tedu.element.HJDTEnemy.soldier.DeadType;
import com.tedu.element.HJDTEnemy.soldier.HJDTEnemy;
import com.tedu.element.older.older;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoad;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Boss extends ElementObj {
    private ElementManager em = ElementManager.getManager();
    private Long myLiveTime = 0L;
    private int curIconIndex = 0;
    private boolean direction = true;
    private int noticeDistance = 600; // 警觉范围
    private boolean status = false; // 状态，移动攻击true，原地攻击false
    private int attackDistance = 10; // 攻击范围
    private int attackTime = 10; // 共计图片更换时间
    private int kind;

    private int absX;


    /**
     * 玩家方向，false左，true右
     */
    private boolean playDirtion = true; // 玩家方向，false在左，true在右
    public int getPlayDistance(){
        int x = em.getElementsByKey(GameElement.PLAY).get(0).getX();
        int w = em.getElementsByKey(GameElement.PLAY).get(0).getW();

        if(this.getX() + (int)(this.getW() * 0.4)< x + w && this.getX() + this.getW() * 0.6 > x){
            return 0;
        }
        int t1 = (int) Math.abs(x - (this.getX() + this.getW() *0.6));
        int t2 = Math.abs(x + w - this.getX() - (int)(this.getW() * 0.4));
        this.playDirtion = t1 <= t2;
        return Math.min(t1,t2);
    }

    private List<ImageIcon> iconList;
    @Override
    public ElementObj createElement(String str) {
        this.kind = Integer.parseInt(str.split(",")[0]);
        this.iconList = GameLoad.imgMaps.get(Action.valueOf("boss"+kind));
        this.setX(Integer.parseInt(str.split(",")[1]));
        if(kind==1){
            setY(240);
            this.attackTime = 8;
        }else {
            this.setY(225);
            this.attackTime = 4;
        }
        this.setW(iconList.get(8).getIconWidth());
        this.setH(iconList.get(8).getIconHeight());
        this.setMaxHp(500);
        this.setHp(500);
        this.setLive(true);
        this.setAttackStatus(true);
        this.setAttack(10);
//        GameThread.feiji = true;
        return this;
    }
//    @Override
//    public Rectangle getRectangle(){
//        return new Rectangle(getX()+100,getY()+120,getW()-150,getH()-120);
//    }
    @Override
    protected void updateImage(long gameTime) {
        updateDirection();
        updateStatus();
        if(gameTime-myLiveTime>attackTime){
            int attackL;
            int attackR;
            if(kind==2){
                attackL = 7; attackR = 15;
            }else{
                attackL =3; attackR = 10;
            }
            myLiveTime = gameTime;
            this.curIconIndex++;
            if(this.curIconIndex == attackL && !this.getAttackStatus()) this.setAttackStatus(true);
            if((this.curIconIndex <attackL || this.curIconIndex > attackR) && this.getAttackStatus()) this.setAttackStatus(false);
            if(kind == 1 && curIconIndex == 10){
                newSoldierRand();
            }
            if(this.curIconIndex>=this.iconList.size()){
                this.curIconIndex = 0;
            }
            if(!direction){
                this.setIcon(this.iconList.get(this.curIconIndex));
            }else {
                this.setIcon(flipImage(this.iconList.get(this.curIconIndex)));
            }
        }
    }
    private void newSoldierRand(){
        Random random = new Random();
        int i = random.nextInt(3);
        HJDTEnemy enemy = (HJDTEnemy) new HJDTEnemy().createElement("800,"+i+",2");
        enemy.setNoticeDistance(1200);
        em.addElement(enemy, GameElement.ENEMY);
    }
    @Override
    public void bePk(GameElement tar, ElementObj b) {
        if(tar == GameElement.PLAYFIRE || tar == GameElement.DIEFIRE){
//            System.out.println("boss被火球打到");
            this.setHp(this.getHp()-b.getAttack());
        }
        if(this.getHp()<=0){
            this.setLive(false);
            GameThread.feiji = false;
            List<ElementObj> list = em.getElementsByKey(GameElement.PLANE);
            if(list.size() >= 1){
                list.get(0).setLive(false);
            }
            em.addElement(new older().createElement(""), GameElement.ODER);
        }
    }

    @Override
    protected void move(long gameTime) {
        if(kind==2){
            if(getAttackStatus() && this.status){
                if(this.direction){
                    this.setX(this.getX()-5);
                }else {
                    this.setX(this.getX()+5);
                }
            }
        }else {
            if(this.direction){
                this.setX(this.getX()-1);
            }else {
                this.setX(this.getX()+1);
            }
        }
    }

    private void updateStatus(){
        int distance = getPlayDistance();
        if(distance > noticeDistance){
            this.status = false;
            return;
        }
        if(distance > attackDistance){
            this.status = true;
            return;
        }
        this.status = false;
    }
    private void updateDirection() {
        int distance = getPlayDistance();
        if(distance > noticeDistance){
            return;
        }
        if(distance == 0){
            return;
        }
        this.direction = !playDirtion;
    }

    private ImageIcon flipImage(ImageIcon image) {
        //    左右翻转图片
        Image img = image.getImage();
        int width = img.getWidth(null);
        int height = img.getHeight(null);
//        System.out.println(image);
        BufferedImage bufferedImage = new BufferedImage(image.getIconWidth(), image.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bufferedImage.createGraphics();
        g.drawImage(img, 0, 0, width, height, width, 0, 0, height, null);
        g.dispose();
        return new ImageIcon(bufferedImage);
    }
    @Override
    public void showElement(Graphics g) {
        if(this.getIcon()==null){
            return;
        }
        g.drawImage(this.getIcon().getImage(),this.getX(),this.getY(),this.getW(),this.getH(),null);
//        g.drawRect(this.getX(),this.getY(),this.getW(),this.getH());
        // 绘制血条
        g.setColor(Color.RED);
        g.fillRect(this.getX(),this.getY()-10,this.getW(),5);
        g.setColor(Color.GREEN);
        g.fillRect(this.getX(),this.getY()-10,(int)(this.getW()*this.getHp()/this.getMaxHp()),5);

    }
}

package com.tedu.element.HJDTEnemy.boss;

import com.tedu.element.ElementObj;
import com.tedu.element.HJDTEnemy.soldier.DeadType;
import com.tedu.element.older.older;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Boss extends ElementObj {
    private static ElementManager em = ElementManager.getManager();
    private Long myLiveTime = 0L;
    private int curIconIndex = 0;
    private boolean direction = true;
    private int noticeDistance = 1000; // 警觉范围
    private boolean status = false; // 状态，移动攻击true，原地攻击false
    private int attackDistance = 10; // 攻击范围

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

    private static ArrayList<ImageIcon> iconList;
    static {
        String url = "image/Enemy/Boss/";
        iconList = new ArrayList<>();
        iconList.add(new ImageIcon(url+"1.png"));
        iconList.add(new ImageIcon(url+"2.png"));
        iconList.add(new ImageIcon(url+"3.png"));
        iconList.add(new ImageIcon(url+"4.png"));
        iconList.add(new ImageIcon(url+"5.png"));
        iconList.add(new ImageIcon(url+"6.png"));
        iconList.add(new ImageIcon(url+"7.png"));
        iconList.add(new ImageIcon(url+"8.png"));
        iconList.add(new ImageIcon(url+"9.png"));
        iconList.add(new ImageIcon(url+"10.png"));
        iconList.add(new ImageIcon(url+"11.png"));
        iconList.add(new ImageIcon(url+"12.png"));
        iconList.add(new ImageIcon(url+"13.png"));
        iconList.add(new ImageIcon(url+"14.png"));
        iconList.add(new ImageIcon(url+"15.png"));
        iconList.add(new ImageIcon(url+"16.png"));
        iconList.add(new ImageIcon(url+"17.png"));
        iconList.add(new ImageIcon(url+"18.png"));
        iconList.add(new ImageIcon(url+"19.png"));
        iconList.add(new ImageIcon(url+"20.png"));
        iconList.add(new ImageIcon(url+"21.png"));
        iconList.add(new ImageIcon(url+"22.png"));
    }

    @Override
    public ElementObj createElement(String str) {
        this.setX(Integer.parseInt(str));
        this.setY(225);
        this.setW(iconList.get(8).getIconWidth());
        this.setH(iconList.get(8).getIconHeight());
        this.setMaxHp(500);
        this.setHp(500);
        this.setLive(true);
        this.setAttackStatus(true);
        this.setAttack(10);
        return this;
    }

    @Override
    protected void updateImage(long gameTime) {
        updateDirection();
        updateStatus();
        if(gameTime-myLiveTime>4){
            myLiveTime = gameTime;
            this.curIconIndex++;
            if(this.curIconIndex == 7 && !this.getAttackStatus()) this.setAttackStatus(true);
            if(this.curIconIndex <7 && this.curIconIndex > 15 && this.getAttackStatus()) this.setAttackStatus(false);

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

    @Override
    public void bePk(GameElement tar, ElementObj b) {
        if(tar == GameElement.PLAYFIRE || tar == GameElement.DIEFIRE){
//            System.out.println("boss be pk");
//            System.out.println(tar);
            this.setHp(this.getHp()-b.getAttack());
        }
        if(this.getHp()<=0){
            this.setLive(false);
            em.addElement(new older().createElement(""), GameElement.ODER);
        }
    }

    @Override
    protected void move(long gameTime) {
        if(this.status && this.curIconIndex>= 7 && this.curIconIndex<= 13){
            if(this.direction){
                this.setX(this.getX()-5);
            }else {
                this.setX(this.getX()+5);
            }
        }
    }

    private void updateStatus(){
        int distance = getPlayDistance();
//        System.out.println(distance);
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
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
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

package com.tedu.element.HJDTEnemy.plane;

import com.tedu.element.Bullet.PlaneBullet;
import com.tedu.element.ElementObj;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.show.GameJFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class plane extends ElementObj {
    private static ElementManager em = ElementManager.getManager();
    private boolean direction;
    private long liveTime = 0L;
    private int shutSpeed = 2;
    @Override
    public ElementObj createElement(String str) {
        this.setIcon(new ImageIcon("image/Enemy/plane/plane0.png"));
        this.setW((int) (this.getIcon().getIconWidth() * 0.8));
        this.setH((int) (this.getIcon().getIconHeight() * 0.8));
        this.setX((int)(Math.random()*(GameJFrame.contentWidth-this.getW())));
        this.setY(10);
        this.direction = Math.random() > 0.5;
        if(!direction){
            this.setIcon(flipImage(this.getIcon()));
        }
        return this;
    }

    @Override
    protected void updateImage(long gameTime) {
    }

    @Override
    protected void move(long gameTime) {
        if(gameTime-liveTime>150){
//            System.out.println("发射子弹");
            liveTime = gameTime;
            em.addElement(new PlaneBullet().createElement("x:"+(this.getX()+this.getW()/2)+",y:"+(this.getY()+this.getH()/2)), GameElement.ENEMYFIRE);
        }
        if(this.getX()<=0){
            this.setX(0);
            direction = true;
            this.setIcon(flipImage(this.getIcon()));
        }else if(this.getX()>=GameJFrame.contentWidth-this.getW()){
            this.setX(GameJFrame.contentWidth-this.getW());
            direction = false;
            this.setIcon(flipImage(this.getIcon()));
        }
        if(direction){
            this.setX(this.getX()+2);
        }else{
            this.setX(this.getX()-2);
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
    public void showElement(Graphics g) {
        g.drawImage(this.getIcon().getImage(), this.getX(), this.getY(), this.getW(), this.getH(), null);
    }
}

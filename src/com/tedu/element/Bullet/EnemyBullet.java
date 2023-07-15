package com.tedu.element.Bullet;



import com.tedu.element.ElementObj;
import com.tedu.element.HJDTEnemy.soldier.EnemyType;
import com.tedu.show.GameJFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class EnemyBullet extends ElementObj {
    private int attack;
    private int speed = 10;
    private String fx;
    public static int bulletSize = 10;
    private EnemyType type;
    public EnemyBullet() {}

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    @Override
    public ElementObj createElement(String str) {
        String[] split = str.split(",");
        for(String str1:split){
            String[] split1 = str1.split(":");
            switch (split1[0]) {
                case "x":
                    this.setX(Integer.parseInt(split1[1]));
                    break;
                case "y":
                    this.setY(Integer.parseInt(split1[1]));
                    break;
                case "fx":
                    this.fx = split1[1];
                    break;
                case "type":
                    if(split1[1].equals("GUN")){
                        this.type = EnemyType.GUN;
                        this.setIcon(new ImageIcon("Image/bullet/bomb1.png"));
                        this.attack = 2;
                    }else if(split1[1].equals("ROCKET_GUN")){
                        this.type = EnemyType.ROCKET_GUN;
                        this.setIcon(new ImageIcon("Image/bullet/bullet30.png"));
                        this.attack = 3;
                    }
                    break;
            }
        }

        if(fx.equals("left")){
            if(this.getIcon() != null)
                this.setIcon(flipImage(this.getIcon()));
        }

        if(this.getIcon() != null){
            this.setW(this.getIcon().getIconWidth());
            this.setH(this.getIcon().getIconHeight());
        }


        this.setAttack(1);
        return this;
    }


    @Override
    public void move(long gametime){
        if (GameJFrame.OutBoard(this.getX(),this.getY())){
            this.setLive(false);
            return;
        }
        switch (this.fx){
            case "left":
                this.setX(this.getX()-speed);
                break;
            case "right":
                this.setX(this.getX()+speed);
                break;
        }
    }
    @Override
    public void die(){
        this.setLive(false);
    }
    @Override
    public void showElement(Graphics g) {
        g.drawImage(this.getIcon().getImage(),this.getX(),this.getY(),this.getW(),this.getH(),null);
    }

    public void setType(EnemyType type) {
        this.type = type;
    }

    public EnemyType getType() {
        return type;
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

}

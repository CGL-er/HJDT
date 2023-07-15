package com.tedu.element;

import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.show.GameJFrame;

import javax.swing.*;
import java.awt.*;

/**
 * @说明 玩家子弹类，本地的实体对象是由玩家对象调用和创建
 * @author cgl
 */
public class PlayFile extends ElementObj{
    private int attack = 1; //攻击力
    private int moveNum;

    private boolean isGun;
    private Direction fx;
    public PlayFile(){}

    // 对创建这个对象的过程进行封装，外界只需要传输必要的约定参数，返回值是对象实体
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
                case "upState":
                    String weapen = split2[1].split("_")[1];
                    String direction = split2[1].split("_")[2];
                    if(weapen.equals("handgun")){
                        isGun=true;
                        if(direction.equals("right"))
                            this.setIcon(new ImageIcon("image/bullet/bullet01.png"));
                        else
                            this.setIcon(new ImageIcon("image/bullet/bullet00.png"));
                        this.setAttackValue(2);
                    }
                    else{
                        isGun=false;
                        if(direction.equals("right"))
                            this.setIcon(new ImageIcon("image/bullet/bullet41.png"));
                        else
                            this.setIcon(new ImageIcon("image/bullet/bullet40.png"));
                        this.setAttackValue(5);
                    }

                    break;
                case "fx":
//                    System.out.println("----");
//                    System.out.println(split2[1]);
                    this.fx = Direction.valueOf(split2[1]);
            }
        }
        this.setW(this.getIcon().getIconWidth());
        this.setH(this.getIcon().getIconHeight());


        this.moveNum = 8;
//        this.setAttack(1);
        return this;
    }
    @Override
    public void showElement(Graphics g){
        g.drawImage(getIcon().getImage(), getX(), getY(), getW(), getH(), null);
    }
    @Override
    public void die(){
//        ElementManager em=ElementManager.getManager();
//        ImageIcon icon = new ImageIcon("image/tank/play2/player2_left.png");
//        ElementObj obj = new Play(this.getX(), this.getY(), 50, 50, icon);
//        em.addElement(obj, GameElement.DIE);
        if (isGun){
            setLive(false);
            return;
        }
        ElementObj bang = new fireBang().createElement(this.toString());
        ElementManager em=ElementManager.getManager();
        em.addElement(bang, GameElement.DIEFIRE);
        setLive(false);

    }
    @Override
    public void bePk(GameElement tar, ElementObj b){
        die();
    }
    @Override
    protected void move(long gameTime){
        if(this.getX()<0 || this.getX()> GameJFrame.contentWidth || this.getY()<0 || this.getY()>GameJFrame.contentHeight){
            this.setLive(false);
            return;
        }
        switch (this.fx){
            case up:
                this.setY(this.getY()-this.moveNum);
                break;
            case down:
                this.setY(this.getY()+this.moveNum);
                break;
            case left:
                this.setX(this.getX()-this.moveNum);
                break;
            case right:
                this.setX(this.getX()+this.moveNum);
                break;

        }
    }

    @Override
    public String toString() {
        return "x:"+getX()+",y:"+(getY()-50)+",isGun:"+this.isGun+",fx:"+this.fx.name();
    }

    public int getAttack() {
        return attack;
    }

    public void setAttackValue(int attackValue) {
        this.attack = attackValue;
    }

}

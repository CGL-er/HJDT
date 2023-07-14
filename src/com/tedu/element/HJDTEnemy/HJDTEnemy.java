package com.tedu.element.HJDTEnemy;

import com.tedu.element.Bullet.EnemyBullet;
import com.tedu.element.ElementObj;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoad;
import com.tedu.show.GameJFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.*;

/**
 * 敌人类
 * 实现敌人自动化移动，攻击，死亡等功能
 */
public class HJDTEnemy extends ElementObj {
    private static ElementManager em = ElementManager.getManager();
//    private static GameLevel gl = GameLevel.getGameLevel();
    private static Map<EnemyType, Map<EnemyStatus, List<ImageIcon>>> EnemyMap;
    private Long myLiveTime = 0L;
    private int curIconIndex = 0;
    private int movetime = 0; //移动间隔时间
    private int moveDistance = 0; //移动距离
    private int statusKeepTime = 0; //状态持续时间
    private final int Gunspeed = 10; //枪速
    private final int RocketSpeed = 10; //火箭速度
    private int shutSpeed = 0; //射击速度

    static {
        String url = "image/Enemy/";
        EnemyMap = new HashMap<EnemyType, Map<EnemyStatus, List<ImageIcon>>>();

        Map<EnemyStatus,List<ImageIcon>> map = new HashMap<>();
        List<ImageIcon> list = new ArrayList<>();
        list.add(new ImageIcon(url+"1/stand/"+"0.png"));
        list.add(new ImageIcon(url+"1/stand/"+"1.png"));
        map.put(EnemyStatus.STAND,list);
        list = new ArrayList<>();
        list.add(new ImageIcon(url+"1/run/"+"0.png"));
        list.add(new ImageIcon(url+"1/run/"+"1.png"));
        list.add(new ImageIcon(url+"1/run/"+"2.png"));
        list.add(new ImageIcon(url+"1/run/"+"3.png"));
        list.add(new ImageIcon(url+"1/run/"+"4.png"));
        map.put(EnemyStatus.RUN,list);
        list = new ArrayList<>();
        list.add(new ImageIcon(url+"1/attack/"+"0.png"));
        list.add(new ImageIcon(url+"1/attack/"+"1.png"));
        list.add(new ImageIcon(url+"1/attack/"+"2.png"));
        list.add(new ImageIcon(url+"1/attack/"+"3.png"));
        list.add(new ImageIcon(url+"1/attack/"+"4.png"));
        list.add(new ImageIcon(url+"1/attack/"+"5.png"));
        list.add(new ImageIcon(url+"1/attack/"+"6.png"));
        list.add(new ImageIcon(url+"1/attack/"+"7.png"));
        map.put(EnemyStatus.ATTACK,list);
        EnemyMap.put(EnemyType.KINEF,map);

        map = new HashMap<>();
        list = new ArrayList<>();
        list.add(new ImageIcon(url+"2/stand/"+"0.png"));
        list.add(new ImageIcon(url+"2/stand/"+"1.png"));
        map.put(EnemyStatus.STAND,list);
        list = new ArrayList<>();
        list.add(new ImageIcon(url+"2/run/"+"0.png"));
        list.add(new ImageIcon(url+"2/run/"+"1.png"));
        list.add(new ImageIcon(url+"2/run/"+"2.png"));
        list.add(new ImageIcon(url+"2/run/"+"3.png"));
        list.add(new ImageIcon(url+"2/run/"+"4.png"));
        map.put(EnemyStatus.RUN,list);
        list = new ArrayList<>();
        list.add(new ImageIcon(url+"2/attack/"+"0.png"));
        list.add(new ImageIcon(url+"2/attack/"+"1.png"));
        list.add(new ImageIcon(url+"2/attack/"+"2.png"));
        list.add(new ImageIcon(url+"2/attack/"+"3.png"));
        list.add(new ImageIcon(url+"2/attack/"+"4.png"));
        map.put(EnemyStatus.ATTACK,list);
        EnemyMap.put(EnemyType.GUN,map);

        map = new HashMap<>();
        list = new ArrayList<>();
        list.add(new ImageIcon(url+"3/stand/"+"0.png"));
        list.add(new ImageIcon(url+"3/stand/"+"1.png"));
        map.put(EnemyStatus.STAND,list);
        list = new ArrayList<>();
        list.add(new ImageIcon(url+"3/run/"+"0.png"));
        list.add(new ImageIcon(url+"3/run/"+"1.png"));
        list.add(new ImageIcon(url+"3/run/"+"2.png"));
        list.add(new ImageIcon(url+"3/run/"+"3.png"));
        list.add(new ImageIcon(url+"3/run/"+"4.png"));
        map.put(EnemyStatus.RUN,list);
        list = new ArrayList<>();
        list.add(new ImageIcon(url+"3/attack/"+"0.png"));
        list.add(new ImageIcon(url+"3/attack/"+"1.png"));
        list.add(new ImageIcon(url+"3/attack/"+"2.png"));
        list.add(new ImageIcon(url+"3/attack/"+"3.png"));
        list.add(new ImageIcon(url+"3/attack/"+"4.png"));
        map.put(EnemyStatus.ATTACK,list);
        EnemyMap.put(EnemyType.ROCKET_GUN,map);
    }

    private EnemyType type; // 敌人类型
    private int speed = 5; // 移动速度
    private boolean direction = true; // true 向右 false 向左
    private EnemyStatus status = EnemyStatus.RUN; // 状态

    /**
     * type: 传入 x,type,status
     * @param type
     * @return
     */
    @Override
    public ElementObj createElement(String type) {
//        Random ran = new Random();
        String[] split = type.split(",");
        this.setX(Integer.parseInt(split[0]));
        this.setType(EnemyType.values()[Integer.parseInt(split[1])]);
        this.setStatus(EnemyStatus.values()[Integer.parseInt(split[2])]);
        this.setY(300);

//        System.out.println("创建敌人："+this.getX()+","+this.getY()+","+this.getType()+","+this.getStatus());
        return this;
    }
    private void changeDirection(){
        this.direction = !this.direction;
        this.moveDistance = 0;
    }
    @Override
    protected void add(){
        if(this.getStatus() == EnemyStatus.ATTACK && this.curIconIndex == 1 && this.type != EnemyType.KINEF){
            int y = this.getY() + this.getH()/2;
            if((shutSpeed+1)%Gunspeed != 0 && this.type==EnemyType.GUN){
                shutSpeed++;
                return;
            }else if((shutSpeed+1)%RocketSpeed != 0 && this.type==EnemyType.ROCKET_GUN){
                shutSpeed++;
                return;
            }
            shutSpeed = 0;
            if(this.direction){
                em.addElement(new EnemyBullet().createElement("x:"+this.getX()+",y:"+y+",fx:right"+",type:"+type),GameElement.ENEMYFIRE);
//                GameLoad.getObj("ENEMYFIRE").createElement("x:"+this.getX()+",y:"+y+",fx:right"+",type:"+type);
            }else{
                em.addElement(new EnemyBullet().createElement("x:"+this.getX()+",y:"+y+",fx:left"+",type:"+type),GameElement.ENEMYFIRE);
//                GameLoad.getObj("ENEMYFIRE").createElement("x:"+this.getX()+",y:"+y+",fx:left"+",type:"+type);
            }
        }
    }

    @Override
    public String toString() {
        return "x:"+this.getX()+",y:"+this.getY()+",fx:"+this.direction;
    }

    @Override
    public void die() {
        Random random = new Random();
        this.setLive(false);
        String str = this.direction?"1":"0";
        str += ","+this.getX()+","+this.getY()+"," + random.nextInt(3); //随机死亡类型
        em.addElement(new DeadEnemy().createElement(str),GameElement.DIE);
    }

    @Override
    protected void updateImage(long gametime) {
        changeStatus();
        if(gametime - myLiveTime > 10){
            myLiveTime = gametime;
            List<ImageIcon> icons = EnemyMap.get(this.getType()).get(this.getStatus());
            curIconIndex = (curIconIndex + 1) % icons.size();
            ImageIcon icon = icons.get(curIconIndex);

            if(this.direction){
                this.setIcon(icon);
                if(this.type == EnemyType.KINEF && this.getStatus() == EnemyStatus.ATTACK){
                    this.setIcon(flipImage(icon));
                }
            }else{
                this.setIcon(flipImage(icon));
                if(this.type == EnemyType.KINEF  && this.getStatus() == EnemyStatus.ATTACK){
                    this.setIcon(icon);
                }
            }
            this.setH(this.getIcon().getIconHeight());
            this.setW(this.getIcon().getIconWidth());
        }
    }

    private void changeStatus() {
        if(statusKeepTime > 100){
            statusKeepTime = 0;
            Random ra = new Random();
            this.setStatus(EnemyStatus.values()[ra.nextInt(3)]);
        }else{
            statusKeepTime+=1;
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
    protected void move(long gametime) {
        if(this.status == EnemyStatus.RUN){
            movetime++;
            moveDistance += this.getSpeed();
            if(movetime % 5 == 0){
                movetime = 0;
                if(this.direction){
                    this.setX(this.getX() + this.getSpeed());
                }else{
                    this.setX(this.getX() - this.getSpeed());
                }
            }
            if(moveDistance > 20 && GameJFrame.OutBoard(this.getX(),this.getY())){
                changeDirection();
            }
        }
    }

    @Override
    public void showElement(Graphics g) {
//        System.out.println("hello");
        if(this.getIcon()!=null)
            g.drawImage(this.getIcon().getImage(),this.getX(),this.getY(),this.getW(),this.getH(),null);
    }
    public ElementManager getEm() {
        return em;
    }

    public EnemyType getType() {
        return type;
    }

    public void setType(EnemyType type) {
        this.type = type;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean isDirection() {
        return direction;
    }

    public void setDirection(boolean direction) {
        this.direction = direction;
    }

    public EnemyStatus getStatus() {
        return status;
    }

    public void setStatus(EnemyStatus status) {
        this.status = status;
    }

}

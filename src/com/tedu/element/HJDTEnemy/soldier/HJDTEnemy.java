package com.tedu.element.HJDTEnemy.soldier;

import com.tedu.element.Bullet.EnemyBullet;
import com.tedu.element.ElementObj;
import com.tedu.element.Panel;
import com.tedu.element.PlayFile;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
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
    private final int Gunspeed = 15; //枪速
    private final int RocketSpeed = 15; //火箭速度
    private int shutSpeed = 0; //射击速度
    private int biasX = 0; // x 偏移
    private int biasY = 0; // y 偏移
    private int noticeDistance = 400; // 警觉范围
    private int attackDistance = 200; // 攻击间隔时间
    private EnemyType type; // 敌人类型
    private int speed = 10; // 移动速度
    /**
     * true 向右 false 向左
     */
    private boolean direction = true;
    private EnemyStatus status = EnemyStatus.RUN; // 状态
    /**
     * 玩家方向，false左，true右
     */
    private boolean playDirtion = true; // 玩家方向，false在左，true在右

    public int getPlayDistance(){
        int x = em.getElementsByKey(GameElement.PLAY).get(0).getX();
        int w = em.getElementsByKey(GameElement.PLAY).get(0).getW();
        if(this.getX() < x + w && this.getX() + this.getW() > x){
            return 0;
        }

        int t1 = Math.abs(x - (this.getX() + this.getW()));
        int t2 = Math.abs(x + w - this.getX());
        this.playDirtion = t1 <= t2;
        return Math.min(t1,t2);
    }
    public boolean GetPlayDirtion() {
        int dis = this.getPlayDistance();
        if(dis < noticeDistance){
            if(dis > attackDistance){
                this.setStatus(EnemyStatus.RUN);
            }else {
                this.setStatus(EnemyStatus.ATTACK);
            }
            return playDirtion;
        }else {
            return this.direction;
        }
    }

    public void setPlayDirtion(boolean playDirtion) {
        this.playDirtion = playDirtion;
    }

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
        if(this.getType()==EnemyType.KINEF){
            this.setHp(3);
            this.setMaxHp(3);
            this.attackDistance = 0;
            this.setAttack(10);
        }else if(this.getType()==EnemyType.GUN){
            this.setMaxHp(2);
            this.setHp(2);
            this.attackDistance = 200;
        }
        else if(this.getType()==EnemyType.ROCKET_GUN){
            this.setMaxHp(2);
            this.setHp(2);
            this.attackDistance = 300;
        }
        this.setH(EnemyMap.get(this.getType()).get(this.getStatus()).get(0).getIconHeight());
        this.setW(EnemyMap.get(this.getType()).get(this.getStatus()).get(0).getIconWidth());
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
            }else if((((shutSpeed + 1) % RocketSpeed) != 0) && (this.type == EnemyType.ROCKET_GUN)){
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
    public void bePk(GameElement tar, ElementObj b){
        if(tar==GameElement.PLAYFIRE || tar==GameElement.DIEFIRE){
//            System.out.println("敌人被攻击"+tar);
            this.setHp(this.getHp()-b.getAttack());
        }
        if(this.getHp()<=0){
            if (isLive()){
                setLive(false);
                com.tedu.element.Panel.setScore(Panel.getScore()+1);
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
        if(this.getX() < 0){
            this.setX(0);
        }
        if(gametime - myLiveTime > 10){
            myLiveTime = gametime;
            List<ImageIcon> icons = EnemyMap.get(this.getType()).get(this.getStatus());
            curIconIndex++;
            if(this.curIconIndex == 4 && this.type == EnemyType.KINEF && !this.getAttackStatus()){
                this.setAttackStatus(true);
            }
            if(this.curIconIndex < 4 &&this.type == EnemyType.KINEF && this.getAttackStatus()) {
//                System.out.println("切换状态");
                this.setAttackStatus(false);
            }

            if(this.curIconIndex >= icons.size()){
                this.curIconIndex = 0;
            }

            ImageIcon icon = icons.get(curIconIndex);

//            if(this.getH() != 0 && this.getW() != 0){
//                this.biasX = this.getW() - this.getIcon().getIconWidth();
//                this.biasY = this.getH() - this.getIcon().getIconHeight();
//            }

            this.setX(this.getX() + biasX);
            this.setY(this.getY() + biasY);

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

        }
    }

    private void changeStatus() {
        this.direction = this.GetPlayDirtion();
        if(statusKeepTime > 500){
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
            if(GameJFrame.OutBoard(this.getX(),this.getY()) || GameJFrame.OutBoard(this.getX()+this.getW(),this.getY())){
//                System.out.println("出界了");
                this.changeDirection();
                moveDistance = 0;
            }
        }
    }

    @Override
    public void showElement(Graphics g) {
//        System.out.println("hello");

        if(this.getIcon()!=null){
            int bx = this.getW() - this.getIcon().getIconWidth();
            int by = this.getH() - this.getIcon().getIconHeight();
            g.drawImage(this.getIcon().getImage(),this.getX(),this.getY()+by,this.getIcon().getIconWidth(),this.getIcon().getIconHeight(),null);
            g.drawRect(this.getX(),this.getY(),this.getW(),this.getH());
            // 绘制血条
            g.setColor(Color.RED);
            g.fillRect(this.getX(),this.getY()-10,this.getW(),5);
            g.setColor(Color.GREEN);
            g.fillRect(this.getX(),this.getY()-10,(int)(this.getW()*this.getHp()/this.getMaxHp()),5);

        }
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

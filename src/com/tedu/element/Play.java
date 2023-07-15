package com.tedu.element;

import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoad;
import com.tedu.show.GameJFrame;
import com.tedu.show.GameMainJPanel;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.Timer;

public class Play extends ElementObj{
    /**
     * 移动属性：
     * 1. 单属性 配合 方向枚举类型使用；一次只能易用一个方向
     * 2. 双属性 上下 和 左右 配合boolean值使用
     *                      需要另一个变量来确认是否按下方向键
 *                      约定 0代表不东 1代表上 2点下
     * 3.4 属性 上下左右都可以 boolean配合使用 true 代表移动 false 不移动
     *                     同时按上和下 怎么办？ 后按的会重置先按的
     * @
     */
    private int speed=3;
    private int groundHeight=435;
    private int jumpSpeed = 5;
    private int jumpHeight = 150;

    private boolean right=false;
    private boolean left=false;

    private boolean directionRight = true;
    private boolean squat=false;
    private boolean useGun=true;
    private boolean useknife=false;
    private boolean jump=false;
    private boolean noFire = false;
    private int noFireTime = 240;

    private ImageIcon upIcon;
    private ImageIcon lowIcon;

    private int upLen;
    private int lowLen;
    private Action upState;
    private Action lowState;
    private boolean pkType=false;
    ElementManager em = ElementManager.getManager();

    public Play(int x, int y, int w, int h, ImageIcon icon) {
        super(x, y, w, h, icon);
    }

    public Play() {

    }

    @Override
    public void showElement(Graphics g){
//        System.out.println(this.getUpIcon());
//        System.out.println(this.getLowIcon());
        int uxbia = 0;
        int uybia = 0;
        int lxbia = 0;
        int lybia = 0;

        if(noFire && !directionRight){
            uxbia=getW()-this.getUpIcon().getIconWidth();
        }
        if(!squat && !jump){
            uybia=7;
            setH(150);
        }
        if(squat){
            uybia=20;
            setH(130);
        }
        if(!directionRight){
            lxbia=46;
            if(jump){
                uxbia=40;
            }
        }else
            lxbia=5;
        if(useknife){
            uybia=15;
        }
        g.drawImage(this.getUpIcon().getImage(),
                this.getX()+uxbia, this.getY()+uybia,
                this.getUpIcon().getIconWidth(), this.getUpIcon().getIconHeight(), null);
        g.drawImage(this.getLowIcon().getImage(),
                this.getX()+lxbia, this.getY()+this.getUpIcon().getIconHeight()+lybia,
                this.getLowIcon().getIconWidth(), this.getLowIcon().getIconHeight(), null);
        g.drawRect(this.getX(), this.getY(), this.getW(), this.getH());
    }
    @Override
    public void keyClick(boolean bl, int key){
//        System.out.println(key);
        if(bl){
            switch (key){
                case 37:
                    directionRight = false;
                    this.right=false;
                    this.left=true;
                    switchState();
                    break;
                case 38:
                    this.squat=false;
                    this.jump=true;
                    switchState();
                    break;
                case 39:
                    directionRight = true;
                    this.left=false;
                    this.right=true;
                    switchState();
                    break;
                case 40:
                    if(!jump)
                        this.squat=true;
                    switchState();
                    break;
                case 32:
                    if (!noFire && !useknife){
                        this.pkType=true;
                        switchState();
                    }
                    break;
                case 49:
                    this.useGun = true;
                    this.useknife = false;
                    switchState();
                    break;
                case 50:
                    this.useGun = false;
                    this.useknife = false;
                    switchState();
                    break;
                case 51:
                    useKnife();
                    switchState();
                    break;
            }
        }else {
            switch (key){
                case 37:
                    this.left=false;
                    break;
                case 38:
//                    this.up=false;
                    break;
                case 39:
                    this.right=false;
                    break;
                case 40:
                    this.squat=false;
                    break;
                case 32:
//                    this.pkType=false;
                    break;
            }
            switchState();
        }
    }
    private long jumpTime;
    private void switchState(){
        if(directionRight && useGun && pkType && !useknife)
            this.upState = Action.fire_handgun_right_upper;
        if(directionRight && useGun && !pkType && !useknife)
            this.upState = Action.unfire_handgun_right_upper;
        if(directionRight && !pkType && useknife)
            this.upState = Action.knife_right_upper;
        if(directionRight && useGun && jump)
            this.upState = Action.jump_handgun_right_upper;
        if(directionRight && !useGun && pkType && !useknife)
            this.upState = Action.fire_bang_right_upper;
        if(directionRight && !useGun && !pkType && !useknife)
            this.upState = Action.unfire_bang_right_upper;
        if(directionRight && !useGun && jump)
            this.upState = Action.jump_bang_right_upper;
        if(directionRight && !jump && right)
            this.lowState = Action.run_right_lower;

        if(directionRight && !jump && !right)
            this.lowState = Action.stand_right_lower;
        if(directionRight && jump)
            this.lowState = Action.jump_right_lower;
        if(directionRight && squat && right)
            this.lowState = Action.squat_right_lower;
        if(directionRight && squat && !right)
            this.lowState = Action.squatStand_right_lower;
        if(directionRight && pkType && !jump && useGun)
            this.upState = Action.fire_handgun_right_upper;
        if(directionRight && pkType && !jump && !useGun)
            this.upState = Action.fire_bang_right_upper;

        if(!directionRight && useGun && pkType)
            this.upState = Action.fire_handgun_left_upper;
        if(!directionRight && useGun && !pkType)
            this.upState = Action.unfire_handgun_left_upper;
        if(!directionRight && useGun && jump)
            this.upState = Action.jump_handgun_left_upper;
        if(!directionRight && !useGun && pkType && !useknife)
            this.upState = Action.fire_bang_left_upper;
        if(!directionRight && !useGun && !pkType && !useknife)
            this.upState = Action.unfire_bang_left_upper;
        if(!directionRight && !useGun && jump)
            this.upState = Action.jump_bang_left_upper;
        if(!directionRight && !pkType && useknife)
            this.upState = Action.knife_left_upper;

        if(!directionRight && !jump && left)
            this.lowState = Action.run_left_lower;
        if(!directionRight && !jump && !left)
            this.lowState = Action.stand_left_lower;
        if(!directionRight && jump)
            this.lowState = Action.jump_left_lower;
        if(!directionRight && squat && left)
            this.lowState = Action.squat_left_lower;
        if(!directionRight && squat && !left)
            this.lowState = Action.squatStand_left_lower;
        if(!directionRight && pkType && !jump && useGun && !useknife)
            this.upState = Action.fire_handgun_left_upper;
        if(!directionRight && pkType && !jump && !useGun && !useknife)
            this.upState = Action.fire_bang_left_upper;
//        System.out.println(upState);
//        System.out.println(lowState);
//        System.out.println(GameLoad.imgMaps.get(upState));
//        System.out.println(GameLoad.imgMaps.get(lowState));
        this.upLen = GameLoad.imgMaps.get(upState).size();
        this.lowLen = GameLoad.imgMaps.get(lowState).size();
    }
    @Override
    public void move(long gameTime){
        int curX = this.getX();
        int curY = this.getY();

        if(this.left && curX>GameJFrame.contentWidth*0.1)
            this.setX(curX-speed);
        if(this.left && curX<=GameJFrame.contentWidth*0.1){
            ElementObj tmap = em.getElementsByKey(GameElement.MAPBG).get(0);
            if (tmap.getX() > 0){
                tmap.setX(tmap.getX()-speed);
                for(ElementObj i:em.getElementsByKey(GameElement.ENEMY)){
                    i.setX(i.getX()+speed+3);
                }
            }
        }
        if(this.right && curX< GameJFrame.contentWidth*0.8)
            this.setX(curX+speed);
        if(this.right && curX>=GameJFrame.contentWidth*0.8){
            ElementObj tmap = em.getElementsByKey(GameElement.MAPBG).get(0);
            if (tmap.getX() < tmap.getIcon().getIconWidth()- GameJFrame.contentWidth){
                tmap.setX(tmap.getX()+speed);
                for(ElementObj i:em.getElementsByKey(GameElement.ENEMY)){
                    i.setX(i.getX()-speed-3);
                }
            }
        }
        if(this.jump){
            //可跳高度内
            if(curY > groundHeight-getH()-jumpHeight && curY < groundHeight-getH()){
                this.setY(curY-jumpSpeed);
            //站在地板的时候
            }else if(curY==groundHeight-getH()){
                if(jumpSpeed<0){
                    this.jump = false;
                    jumpSpeed = Math.abs(jumpSpeed);
                }else {
                    this.setY(curY-jumpSpeed);
                }
            //低过地板的时候
            }else if(curY>groundHeight-getH()){
                this.jump = false;
                jumpSpeed = Math.abs(jumpSpeed);
                this.setY(groundHeight-getH());
            //高过跳跃高度时
            }else {
                jumpSpeed = -jumpSpeed;
                this.setY(curY-jumpSpeed);
            }
        }
        switchState();
    }
    public void useKnife(){
        if(useknife){
            return;
        }
        curIndex = 0;
        Action lastState = upState;
        useknife=true;
        if(directionRight)
            upState = Action.knife_right_upper;
        else
            upState = Action.knife_left_upper;
        noFire = true;
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                noFire = false;
                pkType = false;
                upState = lastState;
                useknife=false;
                switchState();
            }
        };
        // 使用schedule方法指定任务的延迟时间
        timer.schedule(task, noFireTime);
        switchState();
    }
    @Override
    public void bePk(GameElement tar){
//        if (tar == GameElement.MAPS || tar == GameElement.ENEMY)

    }
    /**
     * @重写规则： 1.重写的方法访问的访问修饰符是否可以修改
     *              2.重写的方法传入参数类型序列，必须和父类的一样
     *              3.重写的方法方位修饰符只能比父类更宽泛
     *              4. 抛出的异常不能不能比父类更宽泛
     */
    @Override
    protected void add(){
        if(!this.pkType || noFire){ // 如果不是发射状态，返回
            return;
        }
        curIndex = -1;

        noFire = true;
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                noFire = false;
                pkType = false;
                switchState();
            }
        };
        // 使用schedule方法指定任务的延迟时间
        timer.schedule(task, noFireTime);
        // 以后的框架学习中会碰到，返回对象的实体毛病初始化数据
        ElementObj element = new PlayFile().createElement(this.toString());
        ElementManager.getManager().addElement(element, GameElement.PLAYFIRE);
        switchState();

    }
    private long curTime = 0;
    private int curIndex = 0;
    @Override
    protected void updateImage(long gameTime){
        if(gameTime-curTime>6){
//            System.out.println(GameLoad.imgMaps.get(upState));
            this.setUpIcon(GameLoad.imgMaps.get(upState).get((++curIndex)%upLen));
            this.setLowIcon(GameLoad.imgMaps.get(lowState).get((curIndex)%lowLen));
            curTime = gameTime;
        }
    }

    @Override
    public ElementObj createElement(String str) {
        String[] split = str.split(",");
        this.setW(85);
        this.setH(150);
        this.setX(new Integer(split[0]));
        this.setY(groundHeight-getH());
        this.upState = Action.valueOf(split[2]);
        this.lowState = Action.valueOf(split[3]);
        this.upLen = GameLoad.imgMaps.get(upState).size();
        this.lowLen = GameLoad.imgMaps.get(lowState).size();
        this.setUpIcon(GameLoad.imgMaps.get(upState).get(0));
        this.setLowIcon(GameLoad.imgMaps.get(lowState).get(0));
        this.setHp(100);
        this.setMaxHp(100);
        return this;
    }

    @Override
    public String toString(){
        int tx = this.getX();
        int ty = this.getY()+18;
        if(squat){
            ty=ty+20;
        }
        String direction;
        if(directionRight){
            tx+=85;
            direction = "right";
        }
        else
            direction = "left";
        return "x:"+tx+",y:"+ty+",upState:"+this.upState.name()+",lowState:"+this.lowState.name()+",fx:"+direction;
    }

    public ImageIcon getUpIcon() {
        return upIcon;
    }

    public void setUpIcon(ImageIcon upIcon) {
        this.upIcon = upIcon;
    }

    public ImageIcon getLowIcon() {
        return lowIcon;
    }

    public void setLowIcon(ImageIcon lowIcon) {
        this.lowIcon = lowIcon;
    }
}

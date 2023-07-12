package com.tedu.element;

import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoad;
import com.tedu.show.GameJFrame;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private boolean left=false;

    //图片集合，使用map村粗，枚举类型配合移动（拓展）

    private Action upState;
    private Action lowState;
    private boolean pkType=false;
    public Play(int x, int y, int w, int h, ImageIcon icon) {
        super(x, y, w, h, icon);
    }

    public Play() {

    }

    @Override
    public void showElement(Graphics g){
        g.drawImage(this.getIcon().getImage(),
                this.getX(), this.getY(),
                this.getH(), this.getW(), null);
    }
    @Override
    public void keyClick(boolean bl, int key){
        if(bl){
            switch (key){
                case 37:
                    this.right=false;
                    this.left=true;
//                    this.fx=Direction.left;
                    break;
                case 38:
                    this.down=false;
                    this.up=true;
//                    this.fx=Direction.up;
                    break;
                case 39:
                    this.left=false;
                    this.right=true;
//                    this.fx=Direction.right;
                    break;
                case 40:
                    this.up=false;
                    this.down = true;
//                    this.fx=Direction.down;
                    break;
                case 32:
                    this.pkType=true;
                    break;
            }
        }else {
            switch (key){
                case 37:
                    this.left=false;
                    break;
                case 38:
                    this.up=false;
                    break;
                case 39:
                    this.right=false;
                    break;
                case 40:
                    this.down=false;
                    break;
                case 32:
                    this.pkType=false;
                    break;
            }
        }
    }
    @Override
    public void move(long gameTime){
        int curX = this.getX();
        int curY = this.getY();

        if(this.left && curX>0)
            this.setX(curX-10);
        if(this.up && curY>0)
            this.setY(curY-10);
        if(this.right && curX< GameJFrame.contentWidth-this.getIcon().getIconWidth())
            this.setX(curX+10);
        if(this.down && curY < GameJFrame.contentHeight-this.getIcon().getIconHeight())
            this.setY(curY+10);
        // 撞墙则不走
        ElementManager em=ElementManager.getManager();
        List<ElementObj> maps =  em.getElementsByKey(GameElement.MAPS);
        for(ElementObj i:maps){
            if(i.pk(this)){
                this.setX(curX);
                this.setY(curY);
                return;
            }
        }
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
        if(!this.pkType){ // 如果不是发射状态，返回
            return;
        }
        // 以后的框架学习中会碰到，返回对象的实体毛病初始化数据
        ElementObj element = new PlayFile().createElement(this.toString());
        ElementManager.getManager().addElement(element, GameElement.PLAYFIRE);
    }
    @Override
    protected void updateImage(){
//        this.setIcon(GameLoad.imgMaps.get(ImgState.valueOf(fx.toString()));
    }

    @Override
    public ElementObj createElement(String str) {
        String[] split = str.split(",");
        this.setX(new Integer(split[0]));
        this.setY(new Integer(split[1]));
        List<ImageIcon> icon = GameLoad.imgMaps.get(ImgState.valueOf(split[2]));
        this.fx = Direction.valueOf(split[2].split("_")[0]);
//        this.setW(icon.getIconWidth());
//        this.setH(icon.getIconHeight());
        this.setW(20);
        this.setH(20);
//        this.setIcon(icon);
        return this;
    }

    @Override
    public String toString(){
        int tx = this.getX();
        int ty = this.getY();
        switch (this.fx){
            case up:
                tx += this.getW()/2;
                break;
            case down:
                ty += this.getH();
                tx += this.getW()/2;
                break;
            case left:
                ty += this.getH()/2;
                break;
            case right:
                tx += this.getW();
                ty += this.getH()/2;
                break;
        }
        return "x:"+tx+",y:"+ty+",fx:"+this.fx.name();
    }
}

package com.tedu.element;

import com.tedu.manager.GameElement;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @说明 所有元素的基类
 * @author cgl
 */
public abstract class ElementObj {
    private int x;
    private int y;
    private int w;
    private int h;
    private ImageIcon icon;
    private boolean live = true; //生存状态

//    还有各种必要的状态值
    private int hp;

    public ElementObj(){ // 此构造无作用，只是为了继承无作用而写

    }
    /**
     *
     * @param x 左上角x坐标
     * @param y 左上角y坐标
     * @param w 宽度
     * @param h 高度
     * @param icon 图片
     */
    public ElementObj(int x, int y, int w, int h, ImageIcon icon) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.icon = icon;
    }
    /**
     * @说明 抽象方法，显示元素
     * @param g 画布 用于绘画
     */
    public abstract void showElement(Graphics g);

    /**
     * @说明 使用父类定义接受键盘事件的方法
     *      只有使用实现键盘监听的子类，重写这个方法（约定）
     * @说明 方法2 使用接口的方式；使用接口的方式需要在监听类进行转换
     * @题外话 约定 配置 现在大部分java框架都是需要进行配置的
     *      约定由于配置
     * @param b1 点击的类型 true表示按下，false表示松开
     * @param key 代表键盘的code值
     */
    public void keyClick(boolean b1, int key){ // 这个方法不是强制必须重写的

    }
    public void bePk(GameElement tar){

    }
    public void die(){
    }
    public Rectangle getRectangle(){
        return new Rectangle(x,y,w,h);
    }
    public boolean pk(ElementObj obj){
        if(obj == null)
            return false;
        // 判断两个矩形是否相交
        return this.getRectangle().intersects(obj.getRectangle());
    }

    /**
     * @说明 移动方法；需要移动的子类则实现这个方法
     * 保护，不能直接调用，子类可以重写
     */
    protected void move(long gameTime){

    }
    /**
     * @设计模式 模板模式：在模板模式中定义 对象执行方法的先后顺序，由子类选择性重新方法
     *          1.移动 2，换装 3. 子弹发射
     */
    public final void model(long gameTime){
//        先换装
        updateImage(gameTime);
//        再移动

        move(gameTime);
//        再发射
        add();
    }
    protected void updateImage(long gameTime){}
    protected void add(){}
    public ElementObj createElement(String str){
        return null;
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    /**
     * 只要是VO类 就要为属性生成get和set方法
     */
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }
}

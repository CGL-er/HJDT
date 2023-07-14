package com.tedu.show;


import javax.swing.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * @说明 游戏窗体 主要实现功能：关闭，显示，最大最小化
 * @author cgl
 * @功能说明：需要嵌入面板，启动主线程等
 * @窗体说明 swing awt 窗体大小（记录用户上次使用软件的窗体样式
 *
 * @分析 1.面板绑定到窗体
 *      2.监听绑定
 *      3.游戏主线程启动
 *      4.显示窗体
 *
 */
public class GameJFrame extends JFrame {
    public static int GameX = 800;
    public static int GameY = 450;
    public static int contentWidth;
    public static int contentHeight;
    private JPanel jPanel = null; // 正在显示的面板
    private KeyListener keyListener = null; //键盘监听
    private MouseMotionListener mouseMotionListener = null; //鼠标监听
    private MouseListener mouseListener = null;
    private Thread thread = null; //游戏主线程

    public GameJFrame(){
        init();
    }

    public static boolean OutBoard(int x, int y) {
        if (x < 0 || x > GameX || y < 0 || y > GameY) {
            return true;
        }
        return false;
    }

    public void init(){
        this.setSize(GameX, GameY);
        this.setTitle("干翻腾讯");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 设置关闭且退出
        this.setLocationRelativeTo(null);
    }
    // 窗体布局：可以存档，读档。button
    public void addButton(){
//        this.setLayout(manager);  // 布局格式，可以添加控件
    }
//    启动方法
    public void start(){
        if(jPanel!=null){
            this.add(jPanel);
        }
        if(keyListener != null){
            this.addKeyListener(keyListener);
        }
        if(thread!=null){
            thread.start(); //启动线程
        }
//        界面的刷新
        this.setVisible(true);  //显示界面
        contentWidth = this.getContentPane().getWidth();
        contentHeight = this.getContentPane().getHeight();
        //如果jp是Runnable子类实体对象
        if(this.jPanel instanceof Runnable){
            new Thread((Runnable)this.jPanel).start();
        }
    }

    /**
     * set注入：等大家学习ssm 通过set方法注入配置文件中读取的数据，
     * 将配置文件中的数据复制为类的属性
     *构造注入：需要配合构造方法
     * spring 中ioc进行对象的自动生成，管理
     * **/
    public void setjPanel(JPanel jPanel) {
        this.jPanel = jPanel;
    }

    public void setKeyListener(KeyListener keyListener) {
        this.keyListener = keyListener;
    }

    public void setMouseMotionListener(MouseMotionListener mouseMotionListener) {
        this.mouseMotionListener = mouseMotionListener;
    }

    public void setMouseListener(MouseListener mouseListener) {
        this.mouseListener = mouseListener;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }
}

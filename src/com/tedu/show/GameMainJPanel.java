package com.tedu.show;

import com.tedu.element.ElementObj;
import com.tedu.element.Play;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;

import javax.swing.*;
import java.awt.Graphics;
import java.util.List;
import java.util.Map;

/**
 * @说明 游戏的主要面板
 * @author cgl
 * @功能说明 主要进行元素的显示，同时进行界面的刷新（多线程）
 *
 * @题外话 java开发实现思想：做继承或接口实现
 *
 * @多线程刷新 1.本类实现线程接口
 *           2. 本类中定义一个内部类来实现
 */
public class GameMainJPanel extends JPanel implements Runnable{
    private ElementManager em;

    public GameMainJPanel(){
        init();
        // 以下代码，以后会换地方重写（测试
    }
    public void init(){
        em = ElementManager.getManager();
    }

    /**
     * paint方法是进行绘画元素
     * 绘画时是有固定的顺序，先绘制的图片在底层，后绘画的图片会先覆盖先绘画的
     * 约定：本方法只执行一次，想实时刷新需要多线程
     */

    @Override  //用于绘画的
    public void paint(Graphics g){
        super.paint(g);

        Map<GameElement, List<ElementObj>> all = em.getGameElements();
        for(GameElement ge:GameElement.values() /* 隐藏方法，返回一个数组，数组顺序和定义数据同 */){
            List<ElementObj> list = all.get(ge);
            for(int i=0; i<list.size(); i++){
                ElementObj obj=list.get(i);
                if(obj!=null)
                    obj.showElement(g);
            }
        }
    }

    @Override
    public void run(){
        while (true){
            this.repaint();
            try {
                Thread.sleep(10);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}

package com.tedu.controller;

import com.tedu.element.*;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoad;
import com.tedu.show.GameMainJPanel;

import javax.swing.*;
import java.util.List;
import java.util.Map;

/**
 * @说明 游戏的主线程，用于控制游戏加载，游戏关卡，游戏运行时自动化
 *      游戏判定：游戏地铁切换 资源释放和重新读取。。。
 * @author cgl
 * @继承 使用继承的方式实现多线程（一般加你使用接口实现）
 */
public class GameThread extends Thread{
    private ElementManager em;

    private static boolean nextStage;

    private static int stage;

    public GameThread(){
        em=ElementManager.getManager();
    }


    @Override
    public void run(){ // 游戏的run方法，主线程
        stage=1;
        while (true){ // 拓展，将true变为变量用于控制结束
//        游戏开始前 读进度条，加载游戏资源（场景资源）
            gameLoad(stage);
//        游戏进行时，游戏过程中
            gameRun();
//        游戏场景结束 游戏资源回收（场景资源）
            gameOver();
            try {
                sleep(50);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 游戏的加载
     */
    private void gameOver() {
        if(stage==3)
            em.addElement(new Pass(stage, true), GameElement.PANEL);
        else{
            em.addElement(new Pass(stage, false), GameElement.PANEL);
        }
        try {
            sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if(stage==3){
            System.exit(0);
        }
        em.reinit();
    }
    private long gameTime=0L;
    /**
     * @说明 游戏进行时
     * @任务说明 游戏过程中需要做的事情：1. 自动化玩家移动，碰撞，死亡
     *                              2. 新元素增加（NPCdie，新道具出现）
     *                              3. 暂停等等。。。
     */
    private void gameRun() {
        while (!nextStage){ //预览拓展 true可以变为变量，控制关卡结束
            Map<GameElement, List<ElementObj>> all = em.getGameElements();
            ElementPK(GameElement.ENEMY, GameElement.PLAYFIRE);
            ElementPK(GameElement.PLAYFIRE, GameElement.MAPS);
            ElementPK(GameElement.PLAY, GameElement.MAPS);
            ElementPK(GameElement.PLAY, GameElement.ENEMY);
            moveAndUpdate(all, gameTime); // 游戏元素自动化方法
            gameTime++;
//            if(em.getElementsByKey(GameElement.ENEMY).size() == 0){
//                nextStage = true;
//                stage+=1;
//                System.out.println(stage);
//            }
            try {
                sleep(50);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        nextStage=false;
    }
    public void ElementPK(GameElement tarA, GameElement tarB){
        List<ElementObj> listA =  em.getElementsByKey(tarA);
        List<ElementObj> listB = em.getElementsByKey(tarB);
        for(int i = 0; i<listA.size(); i++){
            for(int j = 0; j<listB.size(); j++){
                ElementObj A = listA.get(i);
                ElementObj B = listB.get(j);
                if(A.pk(B)){
                    A.bePk(tarB);
                    B.bePk(tarA);
                }
            }
        }
    }
    public void moveAndUpdate(Map<GameElement, List<ElementObj>> all, long gameTime){
        for(GameElement ge:GameElement.values() /* 隐藏方法，返回一个数组，数组顺序和定义数据同 */){
            List<ElementObj> list = all.get(ge);
            for(int i=0; i<list.size(); i++){
                ElementObj obj=list.get(i);
                if(!obj.isLive()){
                    list.remove(i--); //list移除掉中间一个元素后，后面的元素会前移，继续后移导致漏掉一个元素
                    obj.die();
                    continue;
                }
                obj.model(gameTime);
            }
        }
    }

    /**
     * 游戏切换关卡
     */
    private void gameLoad(int stage) {
        Panel.setScore(0);
        GameLoad.loadObj();
        GameLoad.loadImg();
        GameLoad.MapLoad(stage);
//        GameLoad.loadPlay(stage);
        System.out.println(em.getElementsByKey(GameElement.ENEMY));
    }

}

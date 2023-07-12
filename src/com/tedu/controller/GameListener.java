package com.tedu.controller;


import com.tedu.element.ElementObj;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @说明 监听类，用于监听用户的操作 keyListener
 * @author renjj
 */
public class GameListener implements KeyListener {

    private ElementManager em = ElementManager.getManager();
    private Set<Integer> set=new HashSet<Integer>();

    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * 按下： 左37 上38 右39 下40
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int key=e.getKeyCode();
        if(set.contains(key)){
            return;
        }
        set.add(key);
        List<ElementObj> play = em.getElementsByKey(GameElement.PLAY);
        for (ElementObj obj:play){
            obj.keyClick(true, e.getKeyCode());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(!set.contains(e.getKeyCode())){
            return;
        }
        set.remove(e.getKeyCode());
        List<ElementObj> play = em.getElementsByKey(GameElement.PLAY);
        for (ElementObj obj:play){
            obj.keyClick(false, e.getKeyCode());
        }
    }
}

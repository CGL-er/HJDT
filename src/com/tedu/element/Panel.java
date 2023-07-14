package com.tedu.element;

import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;

import javax.swing.*;
import java.awt.*;

public class Panel extends ElementObj{
    private static int score;
    private ImageIcon blood0  = new ImageIcon("image/blood/blood0.png");
    private ImageIcon blood1  = new ImageIcon("image/blood/blood1.png");
    private ElementManager em = ElementManager.getManager();

    static {
        score=0;
    }
    public Panel(int t){
        score = t;
    }

    @Override
    public void showElement(Graphics g) {
        ElementObj play = em.getElementsByKey(GameElement.PLAY).get(0);
        float rate = (float) play.getHp()/(float) play.getMaxHp();
        g.drawImage(blood0.getImage(), 30, 5, blood0.getIconWidth(), blood0.getIconHeight(), null);
        g.drawImage(blood1.getImage(), 30, 5, (int)(blood1.getIconWidth()*rate), blood1.getIconHeight(), null);
        g.setColor(Color.black);
        g.setFont(new Font("宋体", Font.BOLD, 30));
        g.drawString("得分："+String.valueOf(score), 30, 60);
    }

    public static int getScore() {
        return score;
    }

    public static void setScore(int t) {
        score = t;
    }
}

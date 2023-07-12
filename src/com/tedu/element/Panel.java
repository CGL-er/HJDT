package com.tedu.element;

import java.awt.*;

public class Panel extends ElementObj{
    private static int score;

    static {
        score=0;
    }
    public Panel(int t){
        score = t;
    }

    @Override
    public void showElement(Graphics g) {
        g.setColor(Color.black);
        g.setFont(new Font("宋体", Font.BOLD, 30));
        g.drawString("得分："+String.valueOf(score), 30, 30);
    }

    public static int getScore() {
        return score;
    }

    public static void setScore(int t) {
        score = t;
    }
}

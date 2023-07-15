package com.tedu.element;

import com.tedu.manager.GameElement;
import com.tedu.show.GameJFrame;
import com.tedu.show.GameMainJPanel;

import java.awt.*;

public class Pass extends ElementObj{
    private int stage;
    private boolean finished;
    private boolean ctn = false;
    private boolean gameOver;
    public Pass(int t, boolean fin, boolean over){
        stage = t;
        finished=fin;
        gameOver=over;
    }

    @Override
    public void showElement(Graphics g) {
        FontMetrics fontMetrics = g.getFontMetrics();
        String message1 = "通关成功！"+"总计消灭敌人"+Panel.getScore();
        String message2 = "";
        String message3 = "按下空格继续......";
        if(gameOver){
            message1="您已阵亡，通关失败";
        }
        else if(finished){
            message2 = "已结束全部关卡";
        }else {
            message2 = "进入第" + String.valueOf(stage) + "关...";
        }

        int message1Width = fontMetrics.stringWidth(message1);
        int message2Width = fontMetrics.stringWidth(message2);
        int message3Width = fontMetrics.stringWidth(message3);

        g.setColor(Color.white);
        g.fillRect(0, 0, GameJFrame.contentWidth, GameJFrame.contentHeight);
        g.setColor(Color.black);
        g.setFont(new Font("宋体", Font.BOLD, 30));
        g.drawString(message1, GameJFrame.contentWidth/2-message1Width/2, GameJFrame.contentHeight/2-17);
        g.drawString(message2, GameJFrame.contentWidth/2-message2Width/2, GameJFrame.contentHeight/2+17);
        g.drawString(message3, GameJFrame.contentWidth/2-message3Width/2, GameJFrame.contentHeight/2+51);
    }


    public boolean isCtn() {
        return ctn;
    }
}

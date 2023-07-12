package com.tedu.element;

import com.tedu.manager.GameElement;
import com.tedu.show.GameJFrame;
import com.tedu.show.GameMainJPanel;

import java.awt.*;

public class Pass extends ElementObj{
    private int stage;
    private boolean finished;
    public Pass(int t, boolean fin){
        stage = t;
        finished=fin;
    }

    @Override
    public void showElement(Graphics g) {
        FontMetrics fontMetrics = g.getFontMetrics();
        String message1 = "通关成功！"+"总计消灭敌人"+Panel.getScore();
        String message2;
        if(finished){
            message2 = "已结束全部关卡";
        }else {
            message2 = "进入第" + String.valueOf(stage) + "关...";
        }

        int message1Width = fontMetrics.stringWidth(message1);
        int message2Width = fontMetrics.stringWidth(message2);

        g.setColor(Color.black);
        g.fillRect(0, 0, GameJFrame.contentWidth, GameJFrame.contentHeight);
        g.setColor(Color.white);
        g.setFont(new Font("宋体", Font.BOLD, 30));
        g.drawString(message1, GameJFrame.contentWidth/2-message1Width/2, GameJFrame.contentHeight/2-17);
        g.drawString(message2, GameJFrame.contentWidth/2-message2Width/2, GameJFrame.contentHeight/2+17);
    }


}

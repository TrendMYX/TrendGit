package chessComponent;

import controller.ClickController;
import model.ChessColor;
import model.ChessboardPoint;

import java.awt.*;

import static controller.GameController.clickcheat;

/**
 * 表示棋盘上非空棋子的格子，是所有非空棋子的父类
 */
public class ChessComponent extends SquareComponent{
    protected String name;// 棋子名字：例如 兵，卒，士等

    protected ChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor chessColor, ClickController clickController, int size) {
        super(chessboardPoint, location, chessColor, clickController, size);
    }
    @Override

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //绘制棋子填充色
        g.setColor(Color.ORANGE);
        g.fillOval(spacingLength, spacingLength, this.getWidth() - 2 * spacingLength, this.getHeight() - 2 * spacingLength);
       //绘制棋子边框
        g.setColor(Color.DARK_GRAY);
        g.drawOval(spacingLength, spacingLength, getWidth() - 2 * spacingLength, getHeight() - 2 * spacingLength);

        if (isReversal) {
            //绘制棋子文字
            g.setColor(this.getChessColor().getColor());
            g.setFont(CHESS_FONT);//此处是宋体，见SquareComponent
            g.drawString(this.name, this.getWidth() / 4, this.getHeight() * 2 / 3);

            //绘制棋子被选中时状态
            if (isSelected()) {
                g.setColor(Color.RED);//改变选中棋子边缘色
                Graphics2D g2 = (Graphics2D) g;
                g2.setStroke(new BasicStroke(4f));
                g2.drawOval(spacingLength, spacingLength, getWidth() - 2 * spacingLength, getHeight() - 2 * spacingLength);
            }
        }
        //==========================================================
        else if(!isReversal&&clickcheat==true){
            if (this.getChessColor()==ChessColor.BLACK) g.setColor(Color.GRAY);
            if (this.getChessColor()==ChessColor.RED) g.setColor(Color.pink);
            g.setFont(CHESS_FONT);//此处是宋体，见SquareComponent
            g.drawString(this.name, this.getWidth() / 4, this.getHeight() * 2 / 3);
        }
    }
}

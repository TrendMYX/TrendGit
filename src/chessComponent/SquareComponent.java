package chessComponent;

import controller.ClickController;
import controller.GameController;
import model.ChessColor;
import model.ChessboardPoint;
import model.Player;
import view.ChessGameFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;

/**
 * 这个类是一个抽象类，主要表示8*4棋盘上每个格子的棋子情况。
 * 有两个子类：
 * 1. EmptySlotComponent: 空棋子
 * 2. ChessComponent: 表示非空棋子
 */
public abstract class SquareComponent extends JComponent {

    private static final Color squareColor = new Color(250, 220, 190);//棋格颜色
    protected static int spacingLength;
    protected static final Font CHESS_FONT = new Font("宋体", Font.BOLD, 36);//改成宋体，不然显示不了

    /**
     * chessboardPoint: 表示8*4棋盘中，当前棋子在棋格对应的位置，如(0, 0), (1, 0)等等
     * chessColor: 表示这个棋子的颜色，有红色，黑色，无色三种
     * isReversal: 表示是否翻转
     * selected: 表示这个棋子是否被选中
     */
    private ChessboardPoint chessboardPoint;
    protected final ChessColor chessColor;
    protected boolean isReversal;
    private boolean selected;

    /**
     * handle click event
     */
    private final ClickController clickController;

    protected SquareComponent(ChessboardPoint chessboardPoint, Point location, ChessColor chessColor, ClickController clickController, int size) {
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
        setLocation(location);
        setSize(size, size);
        this.chessboardPoint = chessboardPoint;
        this.chessColor = chessColor;
        this.selected = false;
        this.clickController = clickController;
        this.isReversal = false;
    }

    public boolean isReversal() {
        return isReversal;
    }

    public void setReversal(boolean reversal) {
        isReversal = reversal;
    }

    public static void setSpacingLength(int spacingLength) {
        SquareComponent.spacingLength = spacingLength;
    }

    public ChessboardPoint getChessboardPoint() {
        return chessboardPoint;
    }

    public void setChessboardPoint(ChessboardPoint chessboardPoint) {
        this.chessboardPoint = chessboardPoint;
    }

    public ChessColor getChessColor() {
        return chessColor;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    /**
     * @param another 主要用于和另外一个棋子交换位置
     *                <br>
     *                调用时机是在移动棋子的时候，将操控的棋子和对应的空位置棋子(EmptySlotComponent)做交换
     */
    public void swapLocation(SquareComponent another) {
        ChessboardPoint chessboardPoint1 = getChessboardPoint(), chessboardPoint2 = another.getChessboardPoint();
        Point point1 = getLocation(), point2 = another.getLocation();
        setChessboardPoint(chessboardPoint2);
        setLocation(point2);
        another.setChessboardPoint(chessboardPoint1);
        another.setLocation(point1);
    }

    /**
     * @param e 响应鼠标监听事件
     *          <br>
     *          当接收到鼠标动作的时候，这个方法就会自动被调用，调用监听者的onClick方法，处理棋子的选中，移动等等行为。
     */
    @Override
    protected void processMouseEvent(MouseEvent e) {
        if(Player.CheckWin()) {

            if(ChessGameFrame.RestartAll==false)
                return ;
            else {
                ChessGameFrame.RestartAll=false;
            }
        }
        if(GameController.initGameFrame.getGameController().chessboard.getCurrentColor()!=Player.ClientColor)
            return ;
        super.processMouseEvent(e);
        if (e.getID() == MouseEvent.MOUSE_PRESSED) {
//            System.out.printf("Click [%d,%d]\n", chessboardPoint.getX(), chessboardPoint.getY());
            clickController.onClick(this);
        }
    }

    public boolean canMoveTo(SquareComponent[][] chessboard, ChessboardPoint destination) {
        SquareComponent destinationChess = chessboard[destination.getX()][destination.getY()];
//        return destinationChess.isReversal==false|| destinationChess instanceof EmptySlotComponent;//需要重写
        int thisx=this.chessboardPoint.getX(),thisy=this.chessboardPoint.getY();
        int desx=destination.getX(),desy=destination.getY();
        if(thisx!=desx&&thisy!=desy)
            return false;
        if(thisx==desx&&thisy==desy)
            return false;
        if(thisx==desx)
        {
            if(thisy<desy)
                if(desy-thisy!=1)
                    return false;
            if(thisy>desy)
                if(thisy-desy!=1)
                    return false;
        }
        if(thisy==desy)
        {
            if(thisx<desx)
                if(desx-thisx!=1)
                    return false;
            if(thisx>desx)
                if(thisx-desx!=1)
                    return false;
        }
        if(destinationChess instanceof EmptySlotComponent)
            return true;
        if(this instanceof GeneralChessComponent)
        {
            if((!(destinationChess instanceof SoldierChessComponent)))
                return true;
            return false;
        }
        if(this instanceof AdvisorChessComponent)
        {
            if(!(destinationChess instanceof GeneralChessComponent))
                return true;
            return false;
        }
        if(this instanceof MinisterChessComponent)
        {
            if(destinationChess instanceof GeneralChessComponent)
                return false;
            if(destinationChess instanceof AdvisorChessComponent)
                return false;
            return true;
        }
        if(this instanceof ChariotChessComponent)
        {
            if(destinationChess instanceof GeneralChessComponent)
                return false;
            if(destinationChess instanceof AdvisorChessComponent)
                return false;
            if(destinationChess instanceof MinisterChessComponent)
                return false;
            return true;
        }
        if(this instanceof HorseChessComponent)
        {
            if(destinationChess instanceof CannonChessComponent)
                return true;
            if(destinationChess instanceof SoldierChessComponent)
                return true;
            if(destinationChess instanceof HorseChessComponent)
                return true;
            return false;
        }
        if(this instanceof SoldierChessComponent)
        {
            if(destinationChess instanceof GeneralChessComponent)
                return true;
            if(destinationChess instanceof SoldierChessComponent)
                return true;
            return false;
        }
        return false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponents(g);
//        System.out.printf("repaint chess [%d,%d]\n", chessboardPoint.getX(), chessboardPoint.getY());
        g.setColor(squareColor);
        g.fillRect(1, 1, this.getWidth() - 2, this.getHeight() - 2);
    }
}

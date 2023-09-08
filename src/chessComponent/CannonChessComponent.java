package chessComponent;

import controller.ClickController;
import model.ChessColor;
import model.ChessboardPoint;

import java.awt.*;

/**
 * 表示大炮——————————————————此类比较难写,隔着打,不能随便移动
 */
public class CannonChessComponent extends ChessComponent {

    public CannonChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor chessColor, ClickController clickController, int size) {
        super(chessboardPoint, location, chessColor, clickController, size);
        if (this.getChessColor() == ChessColor.RED) {
            name = "炮";
        } else {
            name = "砲";
        }
    }

    @Override
    public boolean canMoveTo(SquareComponent[][] chessboard, ChessboardPoint destination) {
        int thisx=this.getChessboardPoint().getX();int thisy=this.getChessboardPoint().getY();
        int desx=destination.getX();int desy=destination.getY();
        if(thisx!=desx&&thisy!=desy)
            return false;
        if(thisx==desx&&thisy==desy)
            return false;
        if(chessboard[desx][desy] instanceof EmptySlotComponent)
            return false;
        if(chessboard[desx][desy].isReversal()==true&&chessboard[desx][desy].getChessColor()==this.getChessColor())
            return false;
        if(thisx==desx)
        {
            int cnt=0;
            for(int y=Math.min(thisy,desy)+1;y<Math.max(thisy,desy);y++)
            {
                if(!(chessboard[thisx][y] instanceof EmptySlotComponent))
                    cnt++;
            }
            if(cnt!=1)
                return false;
            else
                return true;
        }
        if(thisy==desy)
        {
            int cnt=0;
            for(int x=Math.min(thisx,desx)+1;x<Math.max(thisx,desx);x++)
            {
                if(!(chessboard[x][thisy] instanceof EmptySlotComponent))
                    cnt++;
            }
            if(cnt!=1)
                return false;
            else
                return true;
        }
        return true;
    }
}

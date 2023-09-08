package controller;


import chessComponent.CannonChessComponent;
import chessComponent.SquareComponent;
import chessComponent.EmptySlotComponent;
import model.ChessColor;
import model.Player;
import view.ChessGameFrame;
import view.Chessboard;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;

public class ClickController {
    private final Chessboard chessboard;
    private SquareComponent first=null;

    public ClickController(Chessboard chessboard) {
        this.chessboard = chessboard;
    }

    public void onClick(SquareComponent squareComponent) {
        //判断第一次点击
        if (first == null||first instanceof EmptySlotComponent) {/*!!!changed!!!*/
            if (handleFirst(squareComponent)) {
                squareComponent.setSelected(true);
                first = squareComponent;
                first.repaint();
            }
        } else {
            if (first == squareComponent) { // 再次点击取消选取
                squareComponent.setSelected(false);
                SquareComponent recordFirst = first;
                first = null;
                recordFirst.repaint();
            } else if (handleSecond(squareComponent)) {
                //repaint in swap chess method.
                chessboard.swapChessComponents(first, squareComponent);
                Player.gainScore(squareComponent);//改分数
                ChessGameFrame.BlackScores.setText("BLACK'S SCORE: "+Integer.toString(Player.getBlackscore()));
                ChessGameFrame.RedScores.setText("RED'S SCORE: "+Integer.toString(Player.getRedscore()));
                ChessGameFrame.eattenChess.setText(GameController.checkEaten());//改吃子
                chessboard.clickController.swapPlayer();
                try
                {
                    PrintStream SendToServer=new PrintStream(ChessGameFrame.clientThread.socket.getOutputStream());
                    String str=Player.HashGame(chessboard);
                    SendToServer.println(str);
                }catch (Exception ex) {ex.printStackTrace();}
                if(Player.CheckWin())
                {
                    if(Player.getBlackscore()>=60)
                        ChessGameFrame.getStatusLabel().setText("BLACK WINS!");
                    if(Player.getRedscore()>=60)
                        ChessGameFrame.getStatusLabel().setText("RED WINS!");
                }
                first.setSelected(false);
                first = null;
            }
        }
    }


    /**
     * @param squareComponent 目标选取的棋子
     * @return 目标选取的棋子是否与棋盘记录的当前行棋方颜色相同
     */

    private boolean handleFirst(SquareComponent squareComponent)  {
        if (!squareComponent.isReversal()&&(!(squareComponent instanceof EmptySlotComponent))) {
            squareComponent.setReversal(true);
            System.out.printf("Reverse[%d,%d]\n", squareComponent.getChessboardPoint().getX(), squareComponent.getChessboardPoint().getY());
            squareComponent.repaint();
            chessboard.clickController.swapPlayer();
            try
            {
                PrintStream SendToServer=new PrintStream(ChessGameFrame.clientThread.socket.getOutputStream());
                String str=Player.HashGame(chessboard);
                SendToServer.println(str);
            }catch (Exception ex) {ex.printStackTrace();}
            return false;
        }
        return squareComponent.getChessColor() == chessboard.getCurrentColor();
    }


    private boolean handleSecond(SquareComponent squareComponent) {
        if(!(first instanceof CannonChessComponent))
        {
            if (!squareComponent.isReversal())//没翻开且非空棋子不能走
                if (!(squareComponent instanceof EmptySlotComponent))
                    return false;
            return squareComponent.getChessColor() != chessboard.getCurrentColor() &&
                    first.canMoveTo(chessboard.getChessComponents(), squareComponent.getChessboardPoint());
        }
        if(first instanceof CannonChessComponent)
        {
            return first.canMoveTo(chessboard.getChessComponents(),squareComponent.getChessboardPoint());
        }
        //没翻开或空棋子，进入if
       return true;
    }

    public void swapPlayer() {//交换下棋顺序，当前是谁该走了？每次反转，调用chessboard的currentcolor
        chessboard.setCurrentColor(chessboard.getCurrentColor() == ChessColor.BLACK ? ChessColor.RED : ChessColor.BLACK);
        ChessGameFrame.getStatusLabel().setText(String.format("%s's TURN", chessboard.getCurrentColor().getName()));
    }
}

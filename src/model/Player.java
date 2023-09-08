package model;

import chessComponent.*;
import controller.GameController;
import view.ChessGameFrame;
import view.Chessboard;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
public class Player {//如果可能，应该写在服务器端
    private String name;
    public static ChessColor ClientColor=ChessColor.BLACK;
    private static int blackscore=0;
    private static int redscore=0;

    public static int getBlackscore() {
        return blackscore;
    }

    public static int getRedscore() {
        return redscore;
    }

    public static void setBlackscore(int blackscore) {
        Player.blackscore = blackscore;
    }

    public static void setRedscore(int redscore) {
        Player.redscore = redscore;
    }

    public static boolean CheckWin()
    {
        if(blackscore>=60)
            return true;
        if(redscore>=60)
            return true;
        return false;
    }

    public static void gainScore(SquareComponent sq)
    {
        if(sq instanceof GeneralChessComponent)
        {
            if(sq.getChessColor()==ChessColor.RED)
                blackscore+=30;
            if(sq.getChessColor()==ChessColor.BLACK)
                redscore+=30;
        }
        if(sq instanceof AdvisorChessComponent)
        {
            if(sq.getChessColor()==ChessColor.RED)
                blackscore+=10;
            if(sq.getChessColor()==ChessColor.BLACK)
                redscore+=10;
        }
        if(sq instanceof MinisterChessComponent||sq instanceof ChariotChessComponent||sq instanceof HorseChessComponent||sq instanceof CannonChessComponent)
        {
            if(sq.getChessColor()==ChessColor.RED)
                blackscore+=5;
            if(sq.getChessColor()==ChessColor.BLACK)
                redscore+=5;
        }
        if(sq instanceof SoldierChessComponent)
        {
            if(sq.getChessColor()==ChessColor.RED)
                blackscore+=1;
            if(sq.getChessColor()==ChessColor.BLACK)
                redscore+=1;
        }
    }

    public static String HashGame(Chessboard chessboard)
    {
        String ans="";
        SquareComponent[][] sq=chessboard.getChessComponents();
        for(int i=0;i<8;i++)
        {
            for(int j=0;j<4;j++)
            {
                SquareComponent chess=sq[i][j];
                if(chess instanceof GeneralChessComponent)
                    ans+="1";
                if(chess instanceof AdvisorChessComponent)
                    ans+="2";
                if(chess instanceof MinisterChessComponent)
                    ans+="3";
                if(chess instanceof ChariotChessComponent)
                    ans+="4";
                if(chess instanceof HorseChessComponent)
                    ans+="5";
                if(chess instanceof SoldierChessComponent)
                    ans+="6";
                if(chess instanceof CannonChessComponent)
                    ans+="7";
                if(chess instanceof EmptySlotComponent)
                    ans+="8";

                if(chess.getChessColor()==ChessColor.BLACK)
                    ans+="0";
                if(chess.getChessColor()==ChessColor.RED)
                    ans+="1";
                if(chess.getChessColor()==ChessColor.NONE)
                    ans+="2";
                if(chess.isReversal()==false)
                    ans+="0";
                if(chess.isReversal()==true)
                    ans+="1";
            }
        }
        if(chessboard.getCurrentColor()==ChessColor.BLACK)
            ans+="0";
        else if(chessboard.getCurrentColor()==ChessColor.RED)
            ans+="1";
        if(Player.blackscore<10)
            ans+="0";
        ans+=Integer.toString(Player.blackscore);
        if(Player.redscore<10)
            ans+="0";
        ans+=Integer.toString(Player.redscore);
        return ans;
    }

}

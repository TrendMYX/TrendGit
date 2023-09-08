package controller;

import chessComponent.SquareComponent;
import model.ChessColor;
import model.Player;
import view.ChessGameFrame;
import view.Chessboard;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * 这个类主要完成由窗体上组件触发的动作。
 * 例如点击button等
 * ChessGameFrame中组件调用本类的对象，在本类中的方法里完成逻辑运算，将运算的结果传递至chessboard中绘制
 */
public class GameController {
    public static Chessboard chessboard;

    public GameController(Chessboard chessboard) {
        this.chessboard = chessboard;
    }

    public List<String> loadGameFromFile(String path) {
        try {
            List<String> chessData = Files.readAllLines(Path.of(path));//将由path路径得到的文件中的按行录入到chessData(含空格)
            chessboard.loadGame(chessData);
            return chessData;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void generateFile(String path)//当前的一点缺陷：每次文件都会覆盖
    {
        File file=new File(path);
        if(!file.exists())
        {
            try {
                file.createNewFile();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        try {
            FileWriter write=new FileWriter(path,true);//会对文件进行覆盖，若不覆盖，加上true
            BufferedWriter bw=new BufferedWriter(write);
            bw.write(Player.HashGame(initGameFrame.getGameController().chessboard)+"\n");
            bw.close();write.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public static ChessGameFrame initGameFrame;//可能多用户时就不太对了
    public static ChessGameFrame pass(ChessGameFrame chessGameFrame)
    {
        return chessGameFrame;
    }

    public static void restartGame(){
//        if(Player.CheckWin()==true){//结束后重开
            chessboard.initAllChessOnBoard();
            ChessGameFrame.RestartAll=true;
            //###########################################################
            Player.setRedscore(0);Player.setBlackscore(0);
            ChessGameFrame.BlackScores.setText("BLACK'S SCORE: "+Integer.toString(Player.getBlackscore()));
            ChessGameFrame.RedScores.setText("RED'S SCORE: "+Integer.toString(Player.getRedscore()));
            ChessGameFrame.eattenChess.setText("");
            chessboard.repaint();
            if (chessboard.getCurrentColor() == ChessColor.RED){
                chessboard.clickController.swapPlayer();
            }//重置为黑棋先手
//            first.setSelected(true);
        /*}else {
            chessboard.initAllChessOnBoard();
            //#########################################################
            Player.setRedscore(0);Player.setBlackscore(0);
            ChessGameFrame.BlackScores.setText("BLACK'S SCORE: "+Integer.toString(Player.getBlackscore()));
            ChessGameFrame.RedScores.setText("RED'S SCORE: "+Integer.toString(Player.getRedscore()));
            ChessGameFrame.eattenChess.setText("");
            chessboard.repaint();
            if (chessboard.getCurrentColor() == ChessColor.RED){
                chessboard.clickController.swapPlayer();
            }//重置为黑棋先手*/
        }
//    }//重开方法

    //======================================================================检查被吃棋子的方法
    public static String checkEaten(){
        String ans=Player.HashGame(GameController.chessboard);
        String ret="";
        int[] cnt=new int[]{-1,1,2,2,2,2,5,2,-1,-1,-1,1,2,2,2,2,5,2,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
        for(int i=0;i<96; i=i+3)
        {
            //0-black 1-red
            int n=ans.charAt(i)-'0';
            n=10*(ans.charAt(i+1)-'0')+n;
            cnt[n]--;
        }
        for(int i=0;i<cnt.length;i++)
        {
            if(cnt[i]<=0)
                continue;
            switch (i)
            {
                case 1:
                    ret+=("黑色將*"+cnt[i]+" \n");
                    break;
                case 2:
                    ret+=("黑色士*"+cnt[i]+" \n");
//                    System.out.println("黑色士*"+cnt[i]);
                    break;
                case 3:
                    ret+=("黑色象*"+cnt[i]+" \n");
//                    System.out.println("黑色象*"+cnt[i]);
                    break;
                case 4:
                    ret+=("黑色車*"+cnt[i]+" \n");
//                    System.out.println("黑色車*"+cnt[i]);
                    break;
                case 5:
                    ret+=("黑色馬*"+cnt[i]+" \n");
//                    System.out.println("黑色馬*"+cnt[i]);
                    break;
                case 6:
                    ret+=("黑色卒*"+cnt[i]+" \n");
//                    System.out.println("黑色卒*"+cnt[i]);
                    break;
                case 7:
                    ret+=("黑色砲*"+cnt[i]+" \n");
//                    System.out.println("黑色砲*"+cnt[i]);
                    break;
                case 11:
                    ret+=("红色帥*"+cnt[i]+" \n");
//                    System.out.println("红色帥*"+cnt[i]);
                    break;
                case 12:
                    ret+=("红色仕*"+cnt[i]+" \n");
//                    System.out.println("红色仕*"+cnt[i]);
                    break;
                case 13:
                    ret+=("红色相*"+cnt[i]+" \n");
//                    System.out.println("红色相*"+cnt[i]);
                    break;
                case 14:
                    ret+=("红色俥*"+cnt[i]+" \n");
//                    System.out.println("红色俥*"+cnt[i]);
                    break;
                case 15:
                    ret+=("红色傌*"+cnt[i]+" \n");
//                    System.out.println("红色傌*"+cnt[i]);
                    break;
                case 16:
                    ret+=("红色兵*"+cnt[i]+" \n");
//                    System.out.println("红色兵*"+cnt[i]);
                    break;
                case 17:
                    ret+=("红色炮*"+cnt[i]+" \n");
//                    System.out.println("红色炮*"+cnt[i]);
                    break;
            }
        }
        System.out.println(ret);
        return ret;
    }
    //===================================切换作弊模式
    public static boolean clickcheat=false;
    public static void changecheat(){
            System.out.println("切换作弊模式");
            clickcheat=!clickcheat;
    }
}

package view;

import com.sun.tools.javac.Main;
import controller.GameController;
import model.ClientThread;
import model.Player;
import chessComponent.SquareComponent;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;

/**
 * 这个类表示游戏窗体，窗体上包含：
 * 1 Chessboard: 棋盘
 * 2 JLabel:  标签
 * 3 JButton： 按钮
 */
public class ChessGameFrame extends JFrame {
    public static boolean RestartAll=false;
    private final int WIDTH;
    private final int HEIGHT;
    public final int CHESSBOARD_SIZE;
    private GameController gameController;
    public static ClientThread clientThread;
    public static JLabel BlackScores;
    public static JLabel RedScores;
    private static JLabel statusLabel;  //我可以定义两个JLabel，一个表示black turn 一个red turn

    public static JLabel eattenChess;
    //我可以定义两个JLabel，一个表示black turn 一个red turn

    public GameController getGameController() {
        return gameController;
    }

    public ChessGameFrame(int width, int height) {
        setTitle("2022 CS109 Project(Client)------------You are Black"); //设置标题——估计是JFrame方法
        this.WIDTH = width;//赋值界面宽
        this.HEIGHT = height;//赋值界面高
        this.CHESSBOARD_SIZE = HEIGHT * 4 / 5;//赋值棋盘大小

        setSize(WIDTH, HEIGHT);//设置——估计是JFrame方法
        setLocationRelativeTo(null); // Center the window.
        getContentPane().setBackground(Color.WHITE);//设置界面背景颜色
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);

        addChessboard();//添加棋盘————————重点
        addLabel();//红黑轮回
        addBlackScores();
        addRedScores();
//        addHelloButton();//右边上边Hello按钮
        addLoadButton();//右边下边Load按钮，加载保存游戏
        addConnectButton();//连接服务器
        addSaveButton();//保存当前游戏
        //====================================zjh
        addRestartButton();//重新开始
        addCheatButton();//作弊模式
//        addShowEattenChessButton();//显示被吃棋子
        addDisplayButton();//回溯
    }



    /**
     * 在游戏窗体中添加棋盘
     */
    private void addChessboard() {
        Chessboard chessboard = new Chessboard(CHESSBOARD_SIZE / 2, CHESSBOARD_SIZE);
        gameController = new GameController(chessboard);
        chessboard.setLocation(HEIGHT /4, HEIGHT / 10);
        add(chessboard);
    }


    /**
     * 在游戏窗体中添加标签
     */
    //==============================================================================zjh

    private void addDisplayButton() {
        JButton button = new JButton("Display");
        button.setLocation(WIDTH * 3/5+20, HEIGHT / 10 );
        button.setSize(200, 80);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        button.setBackground(Color.LIGHT_GRAY);
        add(button);
        button.addActionListener((e) -> {
            System.out.println("Display");



        });
    }
    private void addRestartButton() {
        JButton button = new JButton("Restart");
        button.setLocation(WIDTH * 3/5+20, HEIGHT / 10 + 250);
        button.setSize(200, 80);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        button.setBackground(Color.LIGHT_GRAY);
        add(button);
        button.addActionListener((e) -> {
            System.out.println("Restart");
            GameController.restartGame();

            try
            {
                PrintStream SendToServer=new PrintStream(ChessGameFrame.clientThread.socket.getOutputStream());
                String str=Player.HashGame(GameController.chessboard);
                SendToServer.println(str);
                System.out.println("重启游戏发送给服务端的内容: "+str);
            }catch (Exception ex) {ex.printStackTrace();}

        });
    }

    private void addCheatButton(){
        JButton button = new JButton("Cheat");
        button.setLocation(WIDTH * 3/5+20, HEIGHT / 10 + 500);
        button.setSize(200, 80);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        button.setBackground(Color.LIGHT_GRAY);
        add(button);
        button.addActionListener((e) -> {
            System.out.println("Cheating");
            GameController.changecheat();
            for(int i=0;i<8;i++)
            {
                for(int j=0;j<4;j++)
                {
                    SquareComponent[][] sq=GameController.chessboard.getChessComponents();
                    sq[i][j].repaint();
                }
            }

        });
    }
    /*private void addShowEattenChessButton() {
        JButton button = new JButton("ShowEattenChess");
        button.setLocation(WIDTH * 3/5, HEIGHT / 10 + 540);
        button.setSize(180, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        button.setBackground(Color.LIGHT_GRAY);
        add(button);
        button.addActionListener((e) -> {
            System.out.println("ShowEattenChess");
            eattenChess.setText(GameController.checkEaten());
        });
    }*/
    //============================================================================
    private void addLabel() {
        statusLabel = new JLabel("BLACK's TURN");
        statusLabel.setLocation(WIDTH * 3/7-40, HEIGHT / 10 +250);//设置位置;
        statusLabel.setSize(200, 60);//设置大小
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 24));//设置字体
        add(statusLabel);//添加一个标签
        //remove(statusLabel);//remove可以把这个标签去掉
        eattenChess = new JLabel();
//        eattenChess.setText(GameController.checkEaten());
        eattenChess.setLocation(WIDTH /100, HEIGHT / 100);//设置位置;
        eattenChess.setSize(200000, 60);//设置大小
        eattenChess.setFont(new Font("宋体", Font.BOLD, 20));//设置字体
        add(eattenChess);//
    }

    //==========================================================================================
    private void addBlackScores(){
        BlackScores = new JLabel("BLACK'S SCORE: "+Player.getBlackscore());
        BlackScores.setLocation(WIDTH * 3/7-40, HEIGHT / 10+50);//设置位置;
        BlackScores.setSize(250, 60);//设置大小
        BlackScores.setFont(new Font("Rockwell", Font.BOLD, 24));//设置字体
        add(BlackScores);//添加一个标签
    }
    private void addRedScores(){
        RedScores = new JLabel("RED'S SCORE: "+Player.getRedscore());
        RedScores.setLocation(WIDTH * 3/7-40, HEIGHT / 10+450);//设置位置;
        RedScores.setSize(250, 60);//设置大小
        RedScores.setFont(new Font("Rockwell", Font.BOLD, 24));//设置字体
        add(RedScores);//添加一个标签
    }
//======================================================================================
    public static JLabel getStatusLabel() {
        return statusLabel;
    }

    /**
     * 在游戏窗体中增加一个按钮，如果按下的话就会显示Hello, world!
     */

    /*private void addHelloButton() {
        JButton button = new JButton("Score");//右边上面按钮的内容
        button.addActionListener((e) -> JOptionPane.showMessageDialog(this, String.format("Black's Score: "+ Player.getBlackscore()+"\n"+"Red's Score: "+Player.getRedscore())));
        button.setLocation(WIDTH * 3/5, HEIGHT / 10 + 60);//右边上面按钮的位置
        button.setSize(180, 60);//右边上面按钮的大小
        button.setFont(new Font("Rockwell", Font.BOLD, 20));//右边上面按钮的文字样式
        add(button);
    }*/


    private void addLoadButton() {//加载上一次保存的游戏，从文件导入
        JButton button = new JButton("Load");
        button.setLocation(WIDTH * 3/5+260, HEIGHT / 10 );
        button.setSize(200, 80);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        button.setBackground(Color.LIGHT_GRAY);
        add(button);

        button.addActionListener(e -> {
            System.out.println("Click load");
            String path = JOptionPane.showInputDialog(this, "Input Path here");
            System.out.println("game load files...");
            gameController.loadGameFromFile(path);
            try
            {
                PrintStream SendToServer=new PrintStream(ChessGameFrame.clientThread.socket.getOutputStream());
                String str=Player.HashGame(GameController.chessboard);
                SendToServer.println(str);
            }catch (Exception ex) {ex.printStackTrace();}

        });


    }
    private void addConnectButton() {//联接服务器
        JButton button = new JButton("Connect");
        button.setLocation(WIDTH * 3/5+260, HEIGHT / 10 +500);
        button.setSize(200, 80);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        button.setBackground(Color.LIGHT_GRAY);
        add(button);

        button.addActionListener(e -> {
            String path = JOptionPane.showInputDialog(this, "Input Server's IP address here");
            try {
                clientThread=new ClientThread();
                clientThread.socket=new Socket(path,2500);
            } catch (IOException ex) {throw new RuntimeException(ex);}
            clientThread.start();
        });
    }

    private void addSaveButton() {//联接服务器
        JButton button = new JButton("Save");
        button.setLocation(WIDTH * 3/5+260, HEIGHT / 10 +250);
        button.setSize(200, 80);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        button.setBackground(Color.LIGHT_GRAY);
        add(button);

        button.addActionListener(e -> {
            String path = JOptionPane.showInputDialog(this, "Input the path you want to save game(/or//or\\\\or\\and Upper or Lower case are all legal)");
            GameController.generateFile(path);
        });
    }


}

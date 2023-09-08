package view;


import chessComponent.*;
import controller.GameController;
import model.*;
import controller.ClickController;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * 这个类表示棋盘组建，其包含：
 * SquareComponent[][]: 4*8个方块格子组件
 */
public class Chessboard extends JComponent {


    private static final int ROW_SIZE = 8;
    private static final int COL_SIZE = 4;
    private static final int HALFCOL_SIZE = 4;

    private  SquareComponent[][] squareComponents = new SquareComponent[ROW_SIZE][COL_SIZE];//final?
    private  SquareComponent[][] squareComponents_deadchess = new SquareComponent[ROW_SIZE][HALFCOL_SIZE];//final?
    //todo: you can change the initial player
    private ChessColor currentColor = ChessColor.BLACK;

    //all chessComponents in this chessboard are shared only one model controller
    public final ClickController clickController = new ClickController(this);
    private final int CHESS_SIZE;


    public Chessboard(int width, int height) {
        setLayout(null); // Use absolute layout.
        setSize(width + 2, height);
        CHESS_SIZE = (height - 6) / 8;
        SquareComponent.setSpacingLength(CHESS_SIZE / 12);
        System.out.printf("chessboard [%d * %d], chess size = %d\n", width, height, CHESS_SIZE);

        initAllChessOnBoard();
    }

    public SquareComponent[][] getChessComponents() {
        return squareComponents;
    }

    public ChessColor getCurrentColor() {
        return currentColor;
    }

    public void setCurrentColor(ChessColor currentColor) {
        this.currentColor = currentColor;
    }

    /**
     * 将SquareComponent 放置在 ChessBoard上。里面包含移除原有的component及放置新的component
     * @param squareComponent
     */
    public void putChessOnBoard(SquareComponent squareComponent) {
        int row = squareComponent.getChessboardPoint().getX(), col = squareComponent.getChessboardPoint().getY();
        if (squareComponents[row][col] != null) {
            remove(squareComponents[row][col]);
        }
        add(squareComponents[row][col] = squareComponent);
    }

    /**
     * 交换chess1 chess2的位置
     * @param chess1
     * @param chess2
     */
    public void swapChessComponents(SquareComponent chess1, SquareComponent chess2) {
        // Note that chess1 has higher priority, 'destroys' chess2 if exists.
        if (!(chess2 instanceof EmptySlotComponent)) {
            remove(chess2);
            add(chess2 = new EmptySlotComponent(chess2.getChessboardPoint(), chess2.getLocation(), clickController, CHESS_SIZE));
        }
        chess1.swapLocation(chess2);
        int row1 = chess1.getChessboardPoint().getX(), col1 = chess1.getChessboardPoint().getY();
        squareComponents[row1][col1] = chess1;
        int row2 = chess2.getChessboardPoint().getX(), col2 = chess2.getChessboardPoint().getY();
        squareComponents[row2][col2] = chess2;

        //只重新绘制chess1 chess2，其他不变
        chess1.repaint();
        chess2.repaint();
    }


    //already done maybe!   Initialize chessboard for testing only.
    public void initAllChessOnBoard() {
        Random random = new Random();
        int[] arr = {1,2,2,3,3,4,4,5,5,6,6,6,6,6,7,7,11,12,12,13,13,14,14,15,15,16,16,16,16,16,17,17};
        Random r = new Random();
        for (int i = 0; i < arr.length; i++) {
            int randomIndex = r.nextInt(arr.length);
            int temp = arr[i];
            arr[i] = arr[randomIndex];
            arr[randomIndex] = temp;
        }
        for (int i = 0; i < squareComponents.length; i++) {
            for (int j = 0; j < squareComponents[i].length; j++) {
                SquareComponent squareComponent=null;
                int num=arr[(i)*4+j];
                if(num<10)
                {
                    ChessColor color=ChessColor.RED;//小于十是红色的
                    switch (num)
                    {
                        case 1:
                            squareComponent = new GeneralChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE);
                            break;
                        case 2:
                            squareComponent = new AdvisorChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE);
                            break;
                        case 3:
                            squareComponent = new MinisterChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE);
                            break;
                        case 4:
                            squareComponent = new ChariotChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE);
                            break;
                        case 5:
                            squareComponent = new HorseChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE);
                            break;
                        case 6:
                            squareComponent = new SoldierChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE);
                            break;
                        case 7:
                            squareComponent = new CannonChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE);
                            break;
                        default:
                            break;
                    }
                }
                else {
                    ChessColor color=ChessColor.BLACK;
                    switch (num%10)
                    {
                        case 1:
                            squareComponent = new GeneralChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE);
                            break;
                        case 2:
                            squareComponent = new AdvisorChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE);
                            break;
                        case 3:
                            squareComponent = new MinisterChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE);
                            break;
                        case 4:
                            squareComponent = new ChariotChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE);
                            break;
                        case 5:
                            squareComponent = new HorseChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE);
                            break;
                        case 6:
                            squareComponent = new SoldierChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE);
                            break;
                        case 7:
                            squareComponent = new CannonChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE);
                            break;
                        default:
                            break;
                    }
                }
                squareComponent.setVisible(true);
                putChessOnBoard(squareComponent);
            }
        }

    }

    /**
     * 绘制棋盘格子
     * @param g
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    /**
     * 将棋盘上行列坐标映射成Swing组件的Point
     * @param row 棋盘上的行
     * @param col 棋盘上的列
     * @return
     */
    private Point calculatePoint(int row, int col) {
        return new Point(col * CHESS_SIZE + 3, row * CHESS_SIZE + 3);
    }

    public void ChangeChessBoardStatus(String s)//由哈希字符串可得到目标状态
    {
        for(int num=0;num<32;num++)//棋盘状态
        {
            int i=num/4;int j=num-i*4;
            ChessColor color=null;
            if(s.charAt(num*3+1)=='0') color=ChessColor.BLACK;
            if(s.charAt(num*3+1)=='1') color=ChessColor.RED;
            SquareComponent chess=null;
            switch (s.charAt(num*3))
            {
                case '1':
                    chess=new GeneralChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE);
                    break;
                case '2':
                    chess=new AdvisorChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE);
                    break;
                case '3':
                    chess=new MinisterChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE);
                    break;
                case '4':
                    chess=new ChariotChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE);
                    break;
                case '5':
                    chess=new HorseChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE);
                    break;
                case '6':
                    chess=new SoldierChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE);
                    break;
                case '7':
                    chess=new CannonChessComponent(new ChessboardPoint(i, j), calculatePoint(i, j), color, clickController, CHESS_SIZE);
                    break;
                case '8':
                    chess=new EmptySlotComponent(new ChessboardPoint(i, j), calculatePoint(i, j),  clickController, CHESS_SIZE);
                    break;

            }
            if(chess instanceof ChessComponent)
            {
                putChessOnBoard(chess);
                if(s.charAt(num*3+2)=='0') chess.setReversal(false);
                if(s.charAt(num*3+2)=='1') chess.setReversal(true);
                chess.repaint();
            }
            if(chess instanceof EmptySlotComponent)
            {
                putChessOnBoard(chess);
                chess.repaint();
            }
        }
        if(s.charAt(96)=='0')
            setCurrentColor(ChessColor.BLACK);
        if(s.charAt(96)=='1')
            setCurrentColor(ChessColor.RED);

        int black_score=(s.charAt(97)-'0')*10+(s.charAt(98)-'0');
        int red_score=(s.charAt(99)-'0')*10+(s.charAt(100)-'0');
        Player.setBlackscore(black_score);
        Player.setRedscore(red_score);
        ChessGameFrame.BlackScores.setText("BLACK'S SCORE: "+Integer.toString(Player.getBlackscore()));
        ChessGameFrame.RedScores.setText("RED'S SCORE: "+Integer.toString(Player.getRedscore()));
        ChessGameFrame.eattenChess.setText(GameController.checkEaten());//改吃子
        if(Player.CheckWin()==true)
        {
            if(Player.getBlackscore()>=60)
                ChessGameFrame.getStatusLabel().setText("BLACK WINS!");
            if(Player.getRedscore()>=60)
                ChessGameFrame.getStatusLabel().setText(String.format("RED WINS!"));
        }
        else
            ChessGameFrame.getStatusLabel().setText(String.format("%s's TURN", getCurrentColor().getName()));
        ChessGameFrame.eattenChess.setText(GameController.checkEaten());
    }

    /**
     * 通过GameController调用该方法
     * @param chessData
     */
    public void loadGame(List<String> chessData) {//0~~size()-2是用来回溯的display()
//        chessData.forEach(System.out::println);//for test
        String s=chessData.get(chessData.size()-1);
        ChangeChessBoardStatus(s);
        System.out.println("Finish");
    }//输出List里的所有内容->此处要修改成加载保存的游戏吧

}

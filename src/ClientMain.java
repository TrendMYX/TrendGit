import controller.GameController;
import view.ChessGameFrame;

import javax.swing.*;

public class ClientMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ChessGameFrame mainFrame = new ChessGameFrame(1440, 720);//初始化游戏界面
            mainFrame.setVisible(true);//使游戏界面可见
            GameController.initGameFrame=GameController.pass(mainFrame);
        });
    }
}

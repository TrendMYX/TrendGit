package model;

import controller.GameController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientThread extends Thread{
    public String s="";
    public Socket socket;

    @Override
    public void run()  {
        super.run();
        System.out.println("Client: "+socket.getLocalAddress()+" Port= "+socket.getLocalPort());
        System.out.println("Server: "+socket.getInetAddress()+" Port= "+socket.getPort());
        try
        {
            BufferedReader GetByServer=new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while(!Player.CheckWin()||true)
            {
                String str=GetByServer.readLine();
                if(str!=null&&(!str.equals(s)))
                {
                    s=str;
                    GameController.initGameFrame.getGameController().chessboard.ChangeChessBoardStatus(s);
                    System.out.println("接收到来自服务器的新内容："+s.substring(0,5));
                }
            }
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }
}

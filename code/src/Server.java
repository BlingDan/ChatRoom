import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    public static ArrayList<MyChannel> list = new ArrayList<>();

    public static void main(String[] args) throws IOException{

        System.out.println("-->客户端启动<--");
        ServerSocket server = new ServerSocket(8888);

        //循环监听客户端是否有链接
        while(true){
            //监听
            Socket socket = server.accept();
            MyChannel channel = new MyChannel(socket);
            list.add(channel);
            new Thread(channel).start();
        }

    }
}

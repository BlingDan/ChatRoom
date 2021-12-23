import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class MyChannel implements  Runnable{

    private DataInputStream dis;
    private DataOutputStream dos;
    private boolean flag = true;

    public MyChannel(Socket socket) {
        try {
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            flag = false;
            try {
                dos.close();
                dis.close();
                socket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

    public String getMessage(){
        String str = "";
        try {
            str = dis.readUTF();
        } catch (IOException e) {
            flag = false;
            try {
                dis.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            Server.list.remove(this);
        }
        return str;
    }

    public void send(String str){
        try {
            dos.writeUTF(str);
            dos.flush();
        } catch (IOException e) {
            flag = false;
            try {
                dos.close();
                dis.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            Server.list.remove(this);
        }
    }

    public void sendOther(){
        ArrayList<MyChannel> l = Server.list;

            for(MyChannel each : l){
                //自己不发送给自己
                if(each != this && each != null){
                    each.send(getMessage());
                }
            }
        }


    @Override
    public void run() {
        while(flag){
            sendOther();
        }
    }
    }




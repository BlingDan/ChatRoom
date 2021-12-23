import org.omg.CosNaming.NamingContextPackage.NotFound;

import java.io.*;
import java.lang.reflect.Array;
import java.net.Socket;

public class Receive implements Runnable{

    private DataInputStream dis;
    private boolean flag = true;

    public Receive(Socket client) {
        try {
            this.dis = new DataInputStream(client.getInputStream());
        } catch (IOException e) {
            flag = false;
            try {
                dis.close();
                client.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

    private String getMessage(){
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
        }
        return str;
    }

    @Override
    public void run() {
        while(flag){
            System.out.println(this.getMessage());
            }
        }


    }


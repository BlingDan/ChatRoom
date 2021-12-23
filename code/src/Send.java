import javax.activation.FileDataSource;
import java.io.*;
import java.net.Socket;

public class Send implements Runnable{

    /**
     * br 键盘输入缓冲区
     * dos 输出缓冲区
     */
    private BufferedReader br;
    private DataOutputStream dos;
    private boolean flag = true;

    public Send(){
        this.br = new BufferedReader((new InputStreamReader(System.in)));
    }
    public Send(Socket client){
        this(); //调用无参构造方法
        try {
            this.dos = new DataOutputStream(client.getOutputStream());
        } catch (IOException e) {
            flag = false;
            try {
                client.close();
                br.close();
                dos.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * 获取从输入流缓冲区得到的字符串
     */
    private String getMessage(){
        String str = "";
        try {
            str = br.readLine();
        } catch (IOException e){
            flag = false;
            try {
                br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return str;
    }


    /**
     *将字符串或者txt文件传到输出缓冲区并强制输出
     */
    private void send(String str){
        try {
            //消息转发
            dos.writeUTF(str);
            dos.flush();
        } catch (IOException e) {
            flag = false;
            try {
                dos.close();
                br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
    }


    @Override
    public void run() {
        while(flag){
            this.send(getMessage());
        }
    }

}

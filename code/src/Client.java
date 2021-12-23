import java.io.IOException;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket client = new Socket("localhost", 8888);

        Send send = new Send(client);
        Receive receive = new Receive(client);

        new Thread(send).start();
        new Thread(receive).start();

    }

}

package em.socket;

import java.io.BufferedOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author Ron
 * @date 2020/10/8 下午 04:04
 */
public class SocketClient {
    private String address = "127.0.0.1";// 連線的ip
    private int port = 8765;// 連線的port

    public SocketClient() {
        Socket client = new Socket();
        InetSocketAddress isa = new InetSocketAddress(this.address, this.port);
        try {
            client.connect(isa, 10000);
            BufferedOutputStream out = new BufferedOutputStream(client
                    .getOutputStream());
            // 送出字串
            out.write("Send From Client \n".getBytes());
            out.flush();
            out.write("Send From Client2 ".getBytes());
            out.close();
            out = null;
            client.close();
            client = null;
        } catch (java.io.IOException e) {
            System.out.println("Socket連線有問題 !");
            System.out.println("IOException :" + e.toString());
        }
    }

    public static void main(String[] args) {
        new SocketClient();
    }
}

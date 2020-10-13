package em.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * @author Ron
 * @date 2020/10/8 下午 05:39
 */
public class SocketServerDemo {

    private static int serverport = 5050;
    private static ServerSocket serverSocket;

    // 用串列來儲存每個client
    private static ArrayList<Socket> players = new ArrayList<>();

    // 程式進入點
    public static void main(String[] args) {
        try {
            serverSocket = new ServerSocket(serverport);
            System.out.println("Server is start.");

            // 當Server運作中時
            while (!serverSocket.isClosed()) {
                // 顯示等待客戶端連接
                System.out.println("Wait new client connect");
                // 呼叫等待接受客戶端連接
                waitNewPlayer();
                serverSocket.close();
            }
        } catch (IOException e) {
            System.out.println("Server Socket ERROR");
        }

        System.out.println("END");

    }

    // 等待接受客戶端連接
    public static void waitNewPlayer() {
        try {
            Socket socket = serverSocket.accept();
            // 呼叫創造新的使用者
            createNewPlayer(socket);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

    // 創造新的使用者
    public static void createNewPlayer(final Socket socket) {
        // 以新的執行緒來執行
        Thread t = new Thread(() -> {
            try {
                // 增加新的使用者
                players.add(socket);

                // 取得網路串流
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                // 當Socket已連接時連續執行
                while (socket.isConnected()) {
                    // 取得網路串流的訊息
                    String msg = br.readLine();
                    // 輸出訊息
                    System.out.println(msg);
                    // 廣播訊息給其它的客戶端
                    // castMsg(msg);
                    // 創造網路輸出串流
                    BufferedWriter bw;
                    bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                    // 寫入訊息到串流
                    bw.write(msg + "\n");
                    // 立即發送
                    bw.flush();
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            // 移除客戶端
            players.remove(socket);
        });

        // 啟動執行緒
        t.start();
    }

    // 廣播訊息給其它的客戶端
    public static void castMsg(String Msg) {
        // 創造socket陣列
        Socket[] ps = new Socket[players.size()];
        // 將players轉換成陣列存入ps
        players.toArray(ps);

        // 走訪ps中的每一個元素
        for (Socket socket : ps) {
            try {
                // 創造網路輸出串流
                BufferedWriter bw;
                bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                // 寫入訊息到串流
                bw.write(Msg + "\n");
                // 立即發送
                bw.flush();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}

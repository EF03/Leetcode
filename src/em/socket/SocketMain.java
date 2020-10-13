package em.socket;

/**
 * @author Ron
 * @date 2020/10/8 下午 04:05
 */
public class SocketMain {
    public static void main(String[] args) {

//        if (args.length == 0)
//            System.out.println("請傳入參數server或client");
//        if (args[0].equals("server"))
        SocketServer socketServer = new SocketServer();
        socketServer.start();
//        else
        SocketClient client = new SocketClient();
        SocketClient client2 = new SocketClient();
        SocketClient client3 = new SocketClient();
    }
}

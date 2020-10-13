package em.socket;

import cn.hutool.core.lang.Console;
import cn.hutool.core.util.StrUtil;
import cn.hutool.socket.aio.*;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

/**
 * @author Ron
 * @date 2020/10/8 下午 03:40
 */
public class AioClientTest {
    public static void main(String[] args) {
        AioClient client = new AioClient(new InetSocketAddress("127.0.0.1", 8765), new SimpleIoAction() {

            @Override
            public void doAction(AioSession session, ByteBuffer data) {
                if (data.hasRemaining()) {
                    Console.log(StrUtil.utf8Str(data));
                    session.read();
                }
                Console.log("OK");
            }
        });
        // 客戶端先讀在寫
        client.write(ByteBuffer.wrap("Hello, here is AioClientTest!".getBytes()));
        client.write(ByteBuffer.wrap("Hello, here is AioClientTest!".getBytes()));
        client.read();
        client.close();

    }

}

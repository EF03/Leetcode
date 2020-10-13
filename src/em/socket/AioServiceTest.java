package em.socket;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.BufferUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.StaticLog;
import cn.hutool.socket.aio.AioServer;
import cn.hutool.socket.aio.AioSession;
import cn.hutool.socket.aio.SimpleIoAction;

import java.nio.ByteBuffer;

/**
 * @author Ron
 * @date 2020/10/8 下午 03:38
 */
public class AioServiceTest {
    public static void main(String[] args) {
        System.out.println("TT");
        AioServer aioServer = new AioServer(8765);
        aioServer.setIoAction(new SimpleIoAction() {

            @Override
            public void accept(AioSession session) {
                System.out.printf("【客户端】：%s 连接。", session.getRemoteAddress());
                StaticLog.debug("【客户端】：{} 连接。", session.getRemoteAddress());
                session.write(BufferUtil.createUtf8("=== Welcome to Hutool socket server. AioServiceTest ==="));
            }

            @Override
            public void doAction(AioSession session, ByteBuffer data) {
                Console.log(data);

                if (!data.hasRemaining()) {
                    StringBuilder response = StrUtil.builder()//
                            .append("HTTP/1.1 200 OK\r\n")//
                            .append("Date: ").append(DateUtil.formatHttpDate(DateUtil.date())).append("\r\n")//
                            .append("Content-Type: text/html; charset=UTF-8\r\n")//
                            .append("\r\n")
                            .append("Hello Hutool socket AioServiceTest 寫出");//
                    session.writeAndClose(BufferUtil.createUtf8(response));
                } else {
                    session.read();
                }
            }
        }).start(true);
    }
}

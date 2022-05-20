import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        InetSocketAddress socketAddress = new InetSocketAddress("127.0.0.1",
                23334);
        final SocketChannel socketChannel = SocketChannel.open();
        try (socketChannel; Scanner scanner = new Scanner(System.in)) {
            socketChannel.connect(socketAddress);
            final ByteBuffer inputBuffer = ByteBuffer.allocate(2 << 10);
            String str;
            while (true) {
                System.out.println("Введите строку");
                str = scanner.nextLine();
                if ("end".equals(str)) break;
                socketChannel.write(
                        ByteBuffer.wrap(
                                str.getBytes(StandardCharsets.UTF_8)));
                Thread.sleep(2000);
                int bytesCount = socketChannel.read(inputBuffer);
                System.out.println(new String(inputBuffer.array(), 0, bytesCount,
                        StandardCharsets.UTF_8).trim());
                inputBuffer.clear();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

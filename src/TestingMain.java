import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Thomas on 10/10/2017.
 */
public class TestingMain {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("192.168.56.1",5555);
            System.out.println("HELLO!");
            InputStream in = socket.getInputStream();
            Scanner scan = new Scanner(in);
            System.out.println(scan.nextLine());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

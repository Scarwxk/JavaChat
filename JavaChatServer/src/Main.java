import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    // 10 users
    public static List<IOCommandes> ioCommandes = new ArrayList<>();

    public static void main(String[] args) {
            // Try block to check for exceptions
            try (ServerSocket ss = new ServerSocket(5999)){

                // Creating an object of ServerSocket class
                // in the main() method  for socket connection

                while(true)
                {
                    Socket soc = ss.accept();
                    ChatThread chatThread = new ChatThread(soc);
                    Thread t = new Thread(chatThread);
                    t.start();
                    ioCommandes.add(chatThread.getIoCommandes());
                }

                // Lastly close the socket using standard close
                // method to release memory resources
            }

            // Catch block to handle the exceptions
            catch (Exception e) {
                // Display the exception on the console
                System.out.println(e);
            }

    }

}
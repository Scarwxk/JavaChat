import java.io.IOException;
import java.net.Socket;

public class Principale {
    public static boolean isEnd = false;

    public static void main(String[] args) {
        IOCommandes ioCommandes;
        Socket s;

        try {
            //s = new Socket("172.21.210.53", 5999);
            s = new Socket("127.0.0.1", 5999);
            ioCommandes = new IOCommandes(s);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ioCommandes.ecrireEcran("Connected to " + s.getInetAddress() + " at port : " + s.getPort());
        ioCommandes.ecrireEcran("Veuillez entrer votre nom d'utilisateur : ");

        new Thread(() -> {
            String message;

            do {
                ioCommandes.ecrireEcran(">> ");
                message = ioCommandes.lireEcran();
                ioCommandes.ecrireReseau(message);
            }while (!"quit".equals(message));
            isEnd = true;
            Thread.currentThread().interrupt();
        }).start();

        String message;
        do
        {
            message = ioCommandes.lireReseau();
            ioCommandes.ecrireEcran(message);
        }while (!isEnd);

        try {
            s.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
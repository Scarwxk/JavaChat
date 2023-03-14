import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatThread implements Runnable{
    private final IOCommandes ioCommandes;
    private final Socket s;

    public ChatThread(Socket s) {
        this.s = s;
        ioCommandes = new IOCommandes(s);
    }

    @Override
    public void run() {
        try {
            ioCommandes.ecrireEcran(s.getInetAddress() + " connected on port : " + s.getPort());
            ioCommandes.setUsername(ioCommandes.lireReseau());
            String message = null;
            do
            {
                try {
                    message = ioCommandes.lireReseau();
                    ioCommandes.ecrireEcran(s.getInetAddress() + " : " +  message);

                    for(IOCommandes io : Main.ioCommandes)
                    {
                        io.ecrireReseau(ioCommandes.getUsername() + " : " + message);
                    }

                    logMessage(message, ioCommandes.getSocket());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }while(!"quit".equals(message) && message != null);

            ioCommandes.ecrireEcran(s.getInetAddress() + " disconnected on port : " + s.getPort());
        }
        catch (Exception e) {
            System.err.println("La connexion avec le client a été interrompue de manière inattendue : " + e.getMessage());
            // Fermer la connexion avec le client
            try {
                s.close();
            } catch (IOException ex) {
                System.err.println("Erreur lors de la fermeture de la connexion avec le client : " + ex.getMessage());
            }

        }

    }

    private void logMessage(String message, Socket socket) throws IOException {
        InetAddress ipAddress = socket.getInetAddress();

        String ip = ipAddress.getHostAddress();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        String logMessage = ioCommandes.getUsername() + " " + ip + " " + formatter.format(date) + " " + message + "\n";
        FileWriter writer = new FileWriter("log.txt", true);
        writer.write(logMessage);
        writer.close();
    }

    public IOCommandes getIoCommandes() {
        return ioCommandes;
    }
}
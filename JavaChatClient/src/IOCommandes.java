import java.io.*;
import java.net.Socket;

public class IOCommandes {
    private final BufferedReader lectureEcran;
    private final BufferedReader lectureReseau;
    private final PrintStream ecritureEcran;
    private final PrintStream ecritureReseau;
    private final Socket socket;

    public IOCommandes(Socket socket) {
        this.lectureEcran = new BufferedReader(new InputStreamReader(System.in));
        this.ecritureEcran = new PrintStream(System.out);
        try {
            this.lectureReseau = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            this.ecritureReseau = new PrintStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.socket = socket;
    }

    public void ecrireEcran(String texte)
    {
        ecritureEcran.println(texte);
    }

    public String lireEcran()
    {
        try {
            return lectureEcran.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void ecrireReseau(String texte)
    {
        ecritureReseau.println(texte);
    }

    public String lireReseau()
    {
        try {
            return lectureReseau.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

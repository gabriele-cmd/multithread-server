import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.server.ExportException;

import it.itismeucci.ServerThread;

public class MultiServer {
    public void start(){
        try{
            ServerSocket serverSocket = new ServerSocket(6789);
            for(;;){
                System.out.println("1 Server in attesa...");
                Socket socket = serverSocket.accept(); //In ATTESA di comunicazione
                System.out.println("3 Server socket " + socket);
                ServerThread serverThread = new ServerThread(socket);//Apro il THREAD per il client
                serverThread.start();
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("Errore durante l'istanza dels erver !");
            System.exit(1);
        }
    }
}

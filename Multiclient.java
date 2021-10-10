import java.io.*;
import java.net.*;

import jdk.internal.org.jline.utils.InputStreamReader;

public class Multiclient {
    
    String nomeServer = "localhost";
    int portaServer = 6789;
    Socket mioSocket;

    BufferedReader tastiera; //input dell'utente da tastiera (buffer)
    String stringaUtente;    //string inserita dall'UTENTE
    String stringaRicevutaDalServer; //stringa RICEVUTA dal SERVER
    DataOutputStream outVersoServer; //stream di output che va VERSO il SERVER
    DataInputStream inDalServer; //stream di input in arrivo nel Client DAL SERVER

    public Socket connetti(){ //Metodo che apre un Socket che stabilisce la CONNESSIONE con il SERVER
            System.out.println("2 CLIENT partito in esecuzione...");
            try{
                //INPUT da tastiera
                tastiera = new BufferedReader(new InputStreamReader(System.in));
                //mioSocket == new Socket(InetAddress.getLocalHost(), 6789)
                mioSocket = new Socket(nomeServer, portaServer);
                //ASSOCIO due oggetti al socket per effetturare la scrittura e la lettura
                outVersoServer = new DataOutputStream(mioSocket.getOutputStream());
                inDalServer = new BufferedReader(new InputStreamReader(mioSocket.getInputStream()));
            }
            catch(UnknownHostException e){
                System.err.println("Host sconosciuto");
            }
            catch(Exception e){
                System.out.println(e.getMessage());
                System.out.println("Errore durante la connessione col server !");
                System.exit(1);
            }
            return mioSocket;
        }
        
    }

    public void comunica(){
        for(;;){ //ciclo infinito che termina con FINE
            try{
                System.out.println("4... utente, inserisci la stringa da trasmettere al server:");
                stringaUtente = tastiera.readLine();
                //spedisco la stringa al SERVER
                System.out.println("5... invio la stringa al server e attendo...");
                outVersoServer.writeBytes(stringaUtente + '\n');
                //leggo la RISPOSTA del server
                stringaRicevutaDalServer = inDalServer.readLine();
                System.out.println("7... risposta dal server " + '\n' + stringaRicevutaDalServer);
                if (stringaUtente.equals("FINE")){
                    System.out.println("8 CLIENT: termina elaborazione e chiude conessione");
                    mioSocket.close(); //CHIUDO LA CONNESSIONE
                    break; //TERMINE ciclo infinito
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
                System.out.println("Errore durante la connesione col server !");
                System.exit(1);
            }
    }
}

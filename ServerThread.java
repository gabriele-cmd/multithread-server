package it.itismeucci;

import java.net.*;
import java.io.*;

public class ServerThread extends Thread{
    ServerSocket server = null;
    Socket client = null; //socket che si connette al CLIENT
    String stringaRicevuta = null; //stringa ricevuta dal CLIENT
    String stringaModificata = null; //stringa mandata indietro dal SERVER
    BufferedReader inDalClient;
    DataOutputStream outVersoClient;

    public ServerThread(Socket socket){ //METODO COSTRUTTORE
        this.client = socket;
    }

    public void run(){  //Il metodo RUN del THREAD si limita solo a richiamare il metodo COMUNICA() che avvia la comunicazione con il CLIENT
        try{
            comunica();
        }catch(Exception e){
            e.printStackTrace(System.out);
        }
    }

    public void comunica() throws Exception{
        inDalClient = new BufferedReader(new InputStreamReader(client.getInputStream())); //Inizializzo il flusso DAL client
        outVersoClient = new DataOutputStream(client.getOutputStream());                  //Inizializzo il flusso VERSO il client

        for(;;){ //For INFINITO per mandare quanti messaggi ci pare al server (dal CLIENT)
            stringaRicevuta = inDalClient.readLine(); //con readLine() il Server LEGGE il messaggio IN ARRIVO DAL CLIENT

            //nel momento in cui il CLIENT invia un messaggio di FINE (oppure non si è proprio connesso), si termina il ciclo
            if(stringaRicevuta == null || stringaRicevuta.equals("FINE")){ 
                outVersoClient.writeBytes(stringaRicevuta + "CONFERMATA => server in chiusura...");
                System.out.println("Echo sul server in chiusura" + stringaRicevuta);
                break; //TERMINE CICLO E CHIUSURA DEL THREAD!
            } else if(stringaRicevuta.equals("STOP")){
                outVersoClient.writeBytes(stringaRicevuta + "CONFERMATA => server in chiusura...");
                System.out.println("Echo sul server in chiusura" + stringaRicevuta);
                server.close(); //CHIUSURA SERVER
                break; //TERMINE CICLO E CHIUSURA DEL THREAD!
            }
            else{
                outVersoClient.writeBytes(stringaRicevuta + "(ricevuta e ritrasmessa)" + '\n');
                System.out.println("6 Echo sul server: " + stringaRicevuta);
            }
        }
        outVersoClient.close();
        inDalClient.close();
        System.out.println("9 chiusura Socket" + client);
        client.close();
    }
}

package pacote1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Servidor {
        public static final int porta = 15001;
        private ServerSocket serverSocket;



        public void start() throws IOException{
            System.out.println("SERVIDOR ONLINE ");
            System.out.println("PORTA USADA: " + porta);
            serverSocket = new ServerSocket(porta);
            conexaoLoopCliente();
        }
        
        public void finalizar () throws IOException{
            serverSocket.close();
        }

        private void conexaoLoopCliente() throws IOException{
                while(true){
                Socket socketeCliente = serverSocket.accept();
                System.out.println("CLIENTE " + socketeCliente.getRemoteSocketAddress() + " ONLINE");
                BufferedReader in = new BufferedReader(new InputStreamReader(socketeCliente.getInputStream()));
                String msg = in.readLine();
                System.out.println("Cliente: " + socketeCliente.getRemoteSocketAddress() + ": " + msg);    
                }
        }

        public static void main (String[] args) throws Exception{
        
        
            try{    
            Servidor server = new Servidor();
            server.start();
       }catch(IOException e){
            System.out.println("SERVIDOR NÃO INICIADO :" + e.getMessage());
       }    



        }
    }

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Cliente {
    private static final String ip = "192.168.2.105";
    private Socket clientSocket;
    private final int porta = Servidor.porta; 
    private Scanner in = new Scanner(System.in);
    private PrintWriter out;


    public void start() throws UnknownHostException, IOException{
        clientSocket = new Socket(ip, porta); 
        clientSocket.getOutputStream();
        this.out = new PrintWriter(clientSocket.getOutputStream());
        System.out.println("=====================================");
        System.out.println("CONEXÃO FEITA COM O SERVERVIDOR NA PORTA: " + porta);
        System.out.println("=====================================");
        
        loopMensagem();
    }

    private void loopMensagem() throws IOException{
        String msg = null;
    do{
        System.out.println("DIGITE UMA MENSAGEM");
        System.out.println("PARA SAIR DIGITE SAIR");
        msg = in.nextLine();
        out.println(msg);
        out.flush();
        System.out.println("=====================================");
    }while(!msg.equalsIgnoreCase("sair"));
    }

    public static void main (String [] args) throws Exception{
      try{
        Cliente cliente = new Cliente();
      cliente.start();
    }catch(IOException e){
        System.out.println("ERRO AO INICIAR CLIENTE" + e.getMessage());
    }

    System.out.println("CLIENTE FINALIZADO");
}


}
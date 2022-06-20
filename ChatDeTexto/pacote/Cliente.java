import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.Calendar;
import java.util.Date;

public class Cliente implements Runnable {
    private static final String ip = "192.168.2.105";
    private final int porta = Servidor.porta; 
    private Scanner in = new Scanner(System.in);
    private String nome;
    ClienteSocket clientSocket;
    Date d = new Date();

    public void start() throws UnknownHostException, IOException{
        try{  
         setName();   
         clientSocket = new ClienteSocket(new Socket(ip, porta)); 
        System.out.println("=====================================");
        System.out.println("CONEXÃO FEITA COM O SERVERVIDOR NA PORTA: " + porta);
        System.out.println("=====================================");

        new Thread(this).start();
        loopMensagem();
        } finally{clientSocket.close();}
    }

    private void loopMensagem() throws IOException{
        String msg = null;
        System.out.println("DIGITE UMA MENSAGEM");
        System.out.println("PARA SAIR DIGITE SAIR");
    do{
        msg = in.nextLine();
        if(msg.equalsIgnoreCase("sair"))msg = "desconectou";

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date hora = Calendar.getInstance().getTime(); 
        String data = sdf.format(hora);
        clientSocket.sendMsg(nome + ": " + msg + " " + data );

        if(msg.equalsIgnoreCase("desconectou"))msg = "sair";

    }while(!msg.equalsIgnoreCase("sair"));
    }

    @Override
    public void run(){
        String msg;

        while((msg = clientSocket.getMsg()) != null){
        System.out.println(msg);
        }
    }

    private void setName(){
        System.out.println("DIGITE SEU NOME: ");
        nome = in.nextLine();
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
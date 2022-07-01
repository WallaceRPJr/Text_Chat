import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Scanner;



import java.util.Calendar;
import java.util.Date;

public class Cliente implements Runnable{
    private String ip;
    private int porta;
    private Scanner in = new Scanner(System.in);
    private String nome;
    ClienteSocket clientSocket;
    Date d = new Date();

    public Cliente(String nome, int porta, String ip){
        this.ip = ip;
        this.nome = nome;
        this.porta = porta;

    }


    public void start() throws UnknownHostException, IOException{
        try{
         clientSocket = new ClienteSocket(new Socket(ip, porta)); 
        System.out.println("=====================================");
        System.out.println("CONEXÃO FEITA COM O SERVERVIDOR NA PORTA: " + porta);
        System.out.println("=====================================");

       // new Thread(this).start();
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


}

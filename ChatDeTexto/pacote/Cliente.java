import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Cliente implements Runnable{
    private String ip;
    private String porta;
    private String nome;
    ClienteSocket clientSocket;
    Date d = new Date();

    
    public Cliente(String nome, String porta, String ip){
        this.ip = ip;
        this.nome = nome;
        this.porta = porta;
    }


    public void start() throws UnknownHostException, IOException{
        try{
         clientSocket = new ClienteSocket(new Socket(ip, Integer.parseInt(porta)), nome); 
        System.out.println("=====================================");
        System.out.println("CONEXÃO FEITA COM O SERVERVIDOR NA PORTA: " + porta);
        System.out.println("=====================================");
        clientSocket.sendMsg(nome);
        }catch (IOException tr){
           return;
        }
    }


    public String loopMensagem(String msg) throws IOException{
        String vc = "";

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date hora = Calendar.getInstance().getTime(); 
        String data = sdf.format(hora);
        clientSocket.sendMsg(nome + ": " + msg + " " + data );  

        vc = "Você: " + msg + " " + data;
        return vc;
        }


    public String recebeMsgServidor(){
        String msg;
        new Thread(
            msg = this.metodo()
        ).start();
        return msg;
    }


    public String metodo(){
        String msg = "null";
        while((msg = clientSocket.getMsg()) != "null"){
        return msg;
        }
        return msg;
    }


    public void close(){
        clientSocket.close();
    }

    @Override
    public void run(){
        String msg;
        while((msg = clientSocket.getMsg()) != null){
        System.out.println(msg);
        }
    }
}

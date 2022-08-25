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

    /**
     *  Classe Cliente: usada para fazer a conexão com o servidor e interagir com a classe ClienteSocket
     * @param nome
     * @param porta
     * @param ip
     */
    
    public Cliente(String nome, String porta, String ip){
        
        this.ip = ip;
        this.nome = nome;
        this.porta = porta;
    }

    /**
     *  Metodo start: Inicia uma conexão e envia o nome do usuario ao servidor
     * @throws UnknownHostException
     * @throws IOException
     */

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

    /**
     *  Metodo enviarMsgServidor : tem como parametro uma String, onde adiciona a essa String nome e a hora
     * para assim enviar para o servidor, e retorna uma String para atualizar o painel de mensagens do usuario 
     * @param msg
     * @return
     * @throws IOException
     */

    public String enviarMsgServidor(String msg) throws IOException{
        String vc = "";

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date hora = Calendar.getInstance().getTime(); 
        String data = sdf.format(hora);
        clientSocket.sendMsg(nome + ": " + msg + " " + data );  

        vc = "Você: " + msg + " " + data;
        return vc;
        }

    /**
     *  Metodo receberMsgServidor: Inicia uma thread usando o metodo(), para que possa ser feita outra atividade
     * ao mesmo tempo. Assim retorna uma mensagem, a qual é recebida pelo servidor.
     * @return
     */    

    public String recebeMsgServidor(){
        String msg;
        new Thread(
            msg = this.metodo()
        ).start();
        return msg;
    }

    /**
     * Metodo metodo: Usado para um thread, recebe uma mensagem do servidor e retorna a mesma.
     * @return
     */

    public String metodo(){
        String msg = "null";
        while((msg = clientSocket.getMsg()) != "null"){
        return msg;
        }
        return msg;
    }

    /**
     *  Metodo close: usado para fechar conexão
     */

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

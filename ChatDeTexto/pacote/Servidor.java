import java.io.IOException;
import java.net.ServerSocket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Servidor {
        public static final int porta = 15000;
        private ServerSocket serverSocket;
        private final List <ClienteSocket> clientes = new LinkedList<>();
        private final List <String> allMsg = new LinkedList<>();
        ClienteSocket aux;

    private void start() throws IOException{   
        System.out.println("SERVIDOR ONLINE ");
        System.out.println("PORTA USADA: " + porta);
        serverSocket = new ServerSocket(porta);
        conexaoLoopCliente();
        }
        
    private void conexaoLoopCliente() throws IOException{
        while(true){
        ClienteSocket socketeCliente = new ClienteSocket(serverSocket.accept(), null);
        String x = socketeCliente.getMsg();
        socketeCliente.setNome(x);
        clientes.add(socketeCliente);
        for(String msg : allMsg){
            sendMsgUser(socketeCliente, msg);
            }
            new Thread(() -> clienteMsgLoop(socketeCliente)).start();
            }
        }   


    private void clienteMsgLoop(ClienteSocket socketeCliente){
        String msg;
        try{
        while((msg = socketeCliente.getMsg()) != null){

            if (msg.contains("/user")){
                msg = msg.replace(socketeCliente.getNome(), "");
                    for (ClienteSocket receptor : clientes){
                        if (msg.contains(receptor.getNome())){         
                            msg = msg.replace("/user", " SUSSUROU: ");
                            sendMsgUser(receptor,socketeCliente.getNome() + msg); 
                            break;
                        }
                    }
            }else{

            if(msg.contains("DESCONECTOU")){
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                Date hora = Calendar.getInstance().getTime(); 
                String data = sdf.format(hora);

                sendMsgAll(socketeCliente,  socketeCliente.getNome() + ": Desconectou " + data);
                allMsg.add(socketeCliente.getNome() + ": Desconectou " + data);
            }else{
                if(!msg.contains("/")){
                System.out.println("Cliente: " + socketeCliente.getRemoteSocketAddress() + "Enviou: " + msg);
                allMsg.add(msg);
                sendMsgAll(socketeCliente, msg);
                }
                }
            }
}

        } finally {socketeCliente.close();}
    }


    private void sendMsgAll(ClienteSocket emissor, String msg){
        Iterator <ClienteSocket> iterator = clientes.iterator();
        while(iterator.hasNext()){
            ClienteSocket clienteSocket = iterator.next();
            if(!emissor.equals(clienteSocket)){
            if (!clienteSocket.sendMsg(msg)){
            iterator.remove();
            }}
        }}


    private void sendMsgUser(ClienteSocket emissor, String msg){
        Iterator <ClienteSocket> iterator = clientes.iterator();
        while(iterator.hasNext()){
            ClienteSocket clienteSocket = iterator.next();
            if(emissor.equals(clienteSocket)){
                clienteSocket.sendMsg(msg);
                }}
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
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


public class Servidor {
        public static final int porta = 15001;
        private ServerSocket serverSocket;
        private final List <ClienteSocket> clientes = new LinkedList<>();
        private final List <String> allMsg = new LinkedList<>();
        



        private void start() throws IOException{
            System.out.println("SERVIDOR ONLINE ");
            System.out.println("PORTA USADA: " + porta);
            serverSocket = new ServerSocket(porta);
            conexaoLoopCliente();
        }
        

        private void conexaoLoopCliente() throws IOException{
                while(true){
                ClienteSocket socketeCliente = new ClienteSocket(serverSocket.accept());
                clientes.add(socketeCliente);

                for(String msg : allMsg){
                    msgAll(socketeCliente, msg);
                }


                
                new Thread(() -> clienteMsgLoop(socketeCliente)).start();


                }
        }

        private void clienteMsgLoop(ClienteSocket socketeCliente){
            String msg;
            try{
            while((msg = socketeCliente.getMsg()) != null){
                if("sair".equalsIgnoreCase(msg)) return;

               
                System.out.println("Cliente: " + socketeCliente.getRemoteSocketAddress() + "Enviou: " + msg);
                allMsg.add(msg);
                msgTds(socketeCliente, msg);
            }
        } finally {socketeCliente.close();}
    }
        private void msgTds(ClienteSocket emissor, String msg){
            Iterator <ClienteSocket> iterator = clientes.iterator();
            while(iterator.hasNext()){
                ClienteSocket clienteSocket = iterator.next();
                if(!emissor.equals(clienteSocket)){
                if (!clienteSocket.sendMsg(msg)){
                iterator.remove();
                }
            }
        }

        }

        private void msgAll(ClienteSocket emissor, String msg){
            Iterator <ClienteSocket> iterator = clientes.iterator();
            while(iterator.hasNext()){
                ClienteSocket clienteSocket = iterator.next();
                if(emissor.equals(clienteSocket)){
                    clienteSocket.sendMsg(msg);
                }
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
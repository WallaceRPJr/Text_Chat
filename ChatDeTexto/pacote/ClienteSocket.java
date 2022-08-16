import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketAddress;

public class ClienteSocket {
    private  String login;
    private  Socket socket;
    private  BufferedReader in; // Obj de Entrada
    private  PrintWriter out;  // Obj de Saida

    /**
     * Objeto usado por ambos (Servidor e usuario) para o envio e recebimento das mensagens
     * @param socket
     * @param login
     * @throws IOException
     */

    public ClienteSocket(Socket socket, String login) throws IOException{
        this.login = login;
        this.socket = socket;
        System.out.println("CLIENTE " + socket.getRemoteSocketAddress() + " ONLINE");
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new PrintWriter(socket.getOutputStream(), true);
    }
    /**
     *  Metodo getRemoteSocketAddress: Retorna IP e PORTA do Objeto ClienteSocket
     * @return
     */
    public SocketAddress getRemoteSocketAddress(){
        return  socket.getRemoteSocketAddress(); 
    }

    /**
     *  Metodo getNome : Retorna a String login, usada também como nome.
     * @return
     */

    public String getNome(){
        return login;
    }

    public void setNome(String nome){
        this.login = nome;
    }

    /**
     *  Metodo getMsg: Retorna uma String, usado para receber mensagem.
     * @return
     */
     
    public String getMsg(){
        try {
            return in.readLine();
        } catch (IOException e) {
           return null;
        }
    }

    /**
     *   Metodo sendMsg: Envia a String passada como parametro, retorna boolean para chegar se a mensagem foi envia
     * @param msg
     * @return
     */

    public boolean sendMsg(String msg){
        out.println(msg);
        return !out.checkError();
    }

    /**
     *  Metodo close: Encera todos os tipos de conexões feitas por sockets
     */

    public void close(){
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            System.out.println("ERRO: " + e.getMessage());
        }
    }
}
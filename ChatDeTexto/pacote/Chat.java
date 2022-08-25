import java.awt.*;
import java.awt.Font;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;


import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;


/**  
 *  Clase que representa o Frame de Chat, onde o usuario recebe e envia as mensagens
 * 
*/


public class Chat extends JFrame  implements Action {
    
    private JButton enviaMsgButton;
    private JButton attListButton;

    private JEditorPane msgPane;
    private JEditorPane onlinePane;

    private JTextField enviaMsgField;
    
    private JScrollPane scrollPane;
    
    private JPanel panel;

    ArrayList<String> msgLista = new ArrayList<String>(); // Lista de todas as mensagens recebidas

    Cliente cliente;

    public Chat(Cliente cliente){
        
       this.cliente = cliente;
    }


    /**
     *      Metodo config: Usado para as configuações da pagina, tais como tamanho, posição, cor,
     * dos elementos Graficos, por exemplo: botão, campo de texto ou painel. 
     */

    public void config(){
        setSize(700, 650);
        setLayout(new BorderLayout());
        setTitle("Chat");
        setDefaultCloseOperation(Chat.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color (240,255,240));
        addWindowListener(new WindowListener(){
            @Override
            public void windowOpened(WindowEvent e){}

    /**
     *  
     * Metodo windowClosing: Usado quando o usuario fecha a janela. Envia "DESCONECTOU" para o servidor,
     * assim ele entende que o usuario saiu e envia para os demais.
     * @param WindowEvent 
     * */         

            @Override
            public void windowClosing(WindowEvent e) {   
                try {
                    cliente.enviarMsgServidor("DESCONECTOU");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            @Override
            public void windowClosed(WindowEvent e) {
                try {
                    cliente.enviarMsgServidor("DESCONECTOU");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }}

            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {}
            @Override
            public void windowDeactivated(WindowEvent e) {}
            
        });



        panel = new JPanel(new BorderLayout());



        attListButton = new JButton("Atualizar Lista");
        attListButton.setBounds(5,15,240,45);
        attListButton.setFont(new Font("Arial", Font.BOLD, 20));
        attListButton.setForeground(new Color (25, 25 , 25));
        attListButton.setBackground(new Color (70, 130 , 180));



        enviaMsgButton = new JButton(">");
        enviaMsgButton.setBounds(570,530,80,45);
        enviaMsgButton.setFont(new Font("Arial", Font.BOLD, 20));
        enviaMsgButton.setForeground(new Color (25, 25 , 25));
        enviaMsgButton.setBackground(new Color (128,128,128));
        


        msgPane = new JEditorPane();
        msgPane.setEditable(false);
        msgPane.setContentType("text/html");
        msgPane.setBackground(new Color (211,211,211));
        
        

        onlinePane = new JEditorPane();
        onlinePane.setBounds(5, 65, 240, 510);
        onlinePane.setEditable(false);



        enviaMsgField = new JTextField("");
        enviaMsgField.setBounds(250, 530,300, 45);
        enviaMsgField.setBackground(new Color(255,255,255));
        enviaMsgField.addKeyListener(new KeyListener(){
        
            /**
             * Metodo key: Usado quando o usuario preciona a tecla ENTER; assim pegando os caracteres de enviaMsgFild
             * e envia para o metodo send();.
             * @param KeyEvent
             */
        
            public void key(KeyEvent e){
            if(e.getKeyChar() == KeyEvent.VK_ENTER){
                    send();
                    enviaMsgField.setText("");
            }}

            @Override
            public void keyTyped(KeyEvent e){}
            @Override
            public void keyPressed(KeyEvent e){key(e);}
            @Override
            public void keyReleased(KeyEvent e) {}
        });



        scrollPane = new JScrollPane(msgPane);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }


    /**
     *  Metodo adiciona: Adiciona todos os elementos grafios a tela.
     */

    private void adiciona(){
        add(scrollPane,BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);
        panel.add(enviaMsgField, BorderLayout.CENTER);
        panel.add(enviaMsgButton, BorderLayout.EAST);

        enviaMsgButton.addActionListener(this);
    }
    
    /**
     * Metodo visivel: Deixa a tela visivel ao usuario.
     */

    private void visivel(){
        setVisible(true);
    }

    /**
     * Metodo msgLoop: Loop para receber todas mensagens diferentes de "null" e colocar passar para o metodo
     * addMsgLista.
     */

    private void msgLoop(){
        while(true){
        String msg = cliente.recebeMsgServidor();
        if (!msg.equalsIgnoreCase("null")){
            addMsgLista(msg + "<br>");
        }}}

    /**
     * Metodo start: Estancia os metodos: config(), adiciona(), visivel(), e manda uma mesagem para o usuario
     * assim que a tela fica visivel ao mesmo. Juntamente inicia a thread com o metodo msgLoop, para assim fazer
     * duas funções ao mesmo tempo 
     * */    

    public void start(){
        config();
        adiciona();
        visivel();

        addMsgLista("==================================================================="+ "<br>");
        addMsgLista("=== BEM VINDO"+ "<br>");
        addMsgLista("=== PARA ENVIAR UMA MENSAGEM PRIVADA"+ "<br>");
        addMsgLista("=== DIGITE: /user nomeDoUsuario"+ "<br>");
        addMsgLista("=== MAS CERTIFIQUE-SE DE QUE ELE ESTEJA CONECTECTADO"+ "<br>");
        addMsgLista("==================================================================="+ "<br>");

        new Thread(() -> msgLoop()).start();
    }

    /**
     * Metodo addMsgLista: adiciona a String recebida a msgLista, e atualiza o painel msgPane.
     * @param String
     */

    private void addMsgLista(String msg){
        msgLista.add(msg);
        String mensagem = " ";
        for(String str : msgLista){
            mensagem += str;
        }
        msgPane.setText(mensagem);
    }

    /**
     *  Metodo send: Pega os caracteres do campo de texto enviaMsgField envia ao servidor passa para o metodo 
     * addMsgLista.
     */


    public void send(){
        String vc;
        try {
            vc = cliente.enviarMsgServidor(enviaMsgField.getText());
            addMsgLista(vc + "<br>");
        } catch (IOException e) {
            e.printStackTrace();
        }}

    /**
     *  Metodo actionPerformed: seleciona a Ação ao clicar no botão.  
     * */

    @Override
    public void actionPerformed(ActionEvent e) {
            send();
            enviaMsgField.setText("");
    }

    @Override
    public Object getValue(String key) {return null;}
    @Override
    public void putValue(String key, Object value) {}

}

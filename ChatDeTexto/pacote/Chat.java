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
import javax.swing.border.Border;


public class Chat extends JFrame  implements Action {
    
    private JButton enviaMsgButton;
    private JButton attListButton;
    private JEditorPane msgPane;
    private JTextField enviaMsgField;
    private JEditorPane onlinePane;
    private JScrollPane scrollPane;
    private JPanel panel;

    ArrayList<String> msgLista = new ArrayList<String>();
    ArrayList<String> onlineLista = new ArrayList<String>();

    Cliente cliente;

    public Chat(Cliente cliente){
       this.cliente = cliente;
    }

    public void config(){
        setSize(700, 650);
        setLayout(new BorderLayout());
        setTitle("Chat");
        setDefaultCloseOperation(Chat.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color (224, 255 , 255));
        addWindowListener(new WindowListener(){
            @Override
            public void windowOpened(WindowEvent e){}
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    cliente.loopMensagem("DESCONECTOU");
                    cliente.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            @Override
            public void windowClosed(WindowEvent e) {}
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
        

        msgPane = new JEditorPane();
        
        msgPane.setEditable(false);
        msgPane.setContentType("text/html");
        msgPane.setBackground(new Color (176, 196 , 222));
        
        

        onlinePane = new JEditorPane();
        onlinePane.setBounds(5, 65, 240, 510);
        onlinePane.setEditable(false);

        enviaMsgField = new JTextField("");
        enviaMsgField.setBounds(250, 530,300, 45);
        enviaMsgField.addKeyListener(new KeyListener(){
        public void key(KeyEvent e){
            if(e.getKeyChar() == KeyEvent.VK_ENTER){
                    send();
                    enviaMsgField.setText("");
            }
            }

            @Override
            public void keyTyped(KeyEvent e){}
            @Override
            public void keyPressed(KeyEvent e){
                key(e);
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });

        scrollPane = new JScrollPane(msgPane);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }

    private void adiciona(){
        add(scrollPane,BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);
        panel.add(enviaMsgField, BorderLayout.CENTER);
        panel.add(enviaMsgButton, BorderLayout.EAST);

        enviaMsgButton.addActionListener(this);
        
    }
    

    private void visivel(){
        setVisible(true);
    }

    private void msgLoop(){
        while(true){
        String msg = cliente.recebeMsgServidor();
        if (!msg.equalsIgnoreCase("null")){
            addMsgLista(msg + "<br>");
        }
    }
    }


    public void start(){
        config();
        adiciona();
        visivel();

        new Thread(() -> msgLoop()).start();

    }

    private void addMsgLista(String msg){
        msgLista.add(msg);
        String mensagem = " ";
        for(String str : msgLista){
            mensagem += str;
        }
        msgPane.setText(mensagem);
    }

    public void send(){
        String vc;
        try {
            vc = cliente.loopMensagem(enviaMsgField.getText());
            addMsgLista(vc + "<br>");
        } catch (IOException e) {
            e.printStackTrace();
        }            
    }
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

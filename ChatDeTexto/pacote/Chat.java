import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;


import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class Chat extends JFrame  implements Action {
    
    private JButton enviaMsgButton;
    private JEditorPane msgPane;
    private JTextField enviaMsgField;
    private JEditorPane onlinePane;
    private JScrollPane scrollPane;

    ArrayList<String> msgLista = new ArrayList<String>();
    ArrayList<String> onlineLista = new ArrayList<String>();

    Cliente cliente;

    public Chat(Cliente cliente){
       this.cliente = cliente;
    }

    public void config(){
        setSize(700, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(new Color (224, 255 , 255));


        enviaMsgButton = new JButton(">");
        enviaMsgButton.setBounds(570,530,80,45);
        enviaMsgButton.setFont(new Font("Arial", Font.BOLD, 20));
        enviaMsgButton.setForeground(new Color (25, 25 , 25));
        enviaMsgButton.setBackground(new Color (181, 199 , 211));


        msgPane = new JEditorPane();
        msgPane.setBounds(250, 15, 400, 500);
        msgPane.setEditable(false);
        msgPane.setContentType("text/html");
        

        onlinePane = new JEditorPane();
        onlinePane.setBounds(5, 15, 240, 500);
        onlinePane.setEditable(false);

        enviaMsgField = new JTextField("");
        enviaMsgField.setBounds(250, 530,300, 45);
        enviaMsgField.addKeyListener(new KeyListener(){
        public void key(KeyEvent e){
            if(e.getKeyChar() == KeyEvent.VK_ENTER){
                    send();
            }
            }

            @Override
            public void keyTyped(KeyEvent e){}
            @Override
            public void keyPressed(KeyEvent e){}
            @Override
            public void keyReleased(KeyEvent e) {}
        });

        scrollPane = new JScrollPane(msgPane);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }

    private void adiciona(){
        add(enviaMsgButton);
        add(enviaMsgField);
        add(msgPane);
        add(onlinePane);

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

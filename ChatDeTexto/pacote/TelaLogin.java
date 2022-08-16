import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * Classe TelaLogin: Primeira tela do projeto, tela executada pelo usuario.
 */

public class TelaLogin extends JFrame implements Action {
    static TelaLogin teste;

    JButton botao = new JButton("Pronto");

    JTextField nomeField;
    JTextField portaField;
    JTextField ipField;

    String nome;
    String porta;
    String ip;

    JLabel nomeJLabel;
    JLabel ipJLabel;
    JLabel portaJLabel;

    /**
     * Metodo TelaLogin: usado para estanciar a classe com todas as configurações já setadas.
     */

    public TelaLogin(){
        config();
        adicionar();
        visivel();
    }

    /**
     * Metodo config: Configura todos os elementos graficos da janela.
     */

    private void config(){
        setSize(500, 450);
        setDefaultCloseOperation(TelaLogin.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(new Color (211,211,211));
        setTitle("Login");
        
        
        

        botao.setBounds(200,320,100,50);
        botao.setFont(new Font("Arial", Font.BOLD, 20));
        botao.setForeground(new Color (1, 1 , 1));
        botao.setBackground(new Color (128,128,128));



        nomeField = new JTextField("");
        nomeField.setBounds(140, 50, 200, 45);



        portaField = new JTextField("");
        portaField.setBounds(140, 150, 200, 45);



        ipField = new JTextField("");
        ipField.setBounds(140, 250, 200, 45);



        nomeJLabel = new JLabel("Nome");
        nomeJLabel.setBounds(140, 15, 100, 45);
        nomeJLabel.setFont(new Font("Arial", Font.BOLD, 20));



        portaJLabel = new JLabel("IP");
        portaJLabel.setBounds(140, 215, 100, 45);
        portaJLabel.setFont(new Font("Arial", Font.BOLD, 20));



        ipJLabel = new JLabel("Porta");
        ipJLabel.setBounds(140, 115, 100, 45);
        ipJLabel.setFont(new Font("Arial", Font.BOLD, 20));
    };

    /**
     *  Metodo adiciona: Adiciona todos os elemetos a janela, e a ação ao Botão
     */

    private void adicionar(){
        add(nomeField);
        add(portaField);
        add(ipField);
        add(botao);
        add(nomeJLabel);
        add(portaJLabel);
        add(ipJLabel);

        botao.addActionListener(this);
    };

    /**
     *  Metodo visivel: Deixa a tela visivel ao usuario.
     */

    private void visivel(){
        setVisible(true);
    };

    /**
     *  Metodo actionPerformed: usado quando o botão é selecionado, pegata todos os tres campus e inicia
     * uma conexão com o servidor, enviando assim para o mesmo que esta conectado.
     */

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
       nome = nomeField.getText();
       porta = portaField.getText();
       ip = ipField.getText();
       Cliente cliente = new Cliente(nome, porta, ip);
       cliente.start();

       cliente.enviarMsgServidor( ": Conectou");

       Chat test = new Chat(cliente);
       test.start();
       teste.dispose();
    } catch (IOException e1) {
        JOptionPane.showMessageDialog(null, "Servidor não iniciado ou dados faltando", "ERRO", JOptionPane.ERROR_MESSAGE);
    }}


    @Override
    public Object getValue(String key) {return null;}

    @Override
    public void putValue(String key, Object value) {}


    
    public static void main (String [] args){
        teste = new TelaLogin();
    }
}

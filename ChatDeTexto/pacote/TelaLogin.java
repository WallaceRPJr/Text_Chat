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

public class TelaLogin extends JFrame implements Action {
    JButton botao = new JButton("Pronto");
    JTextField nomeField;
    JTextField ipField;
    JTextField portaField;
    String nome;
    Integer ip;
    String porta;
    JLabel nomeJLabel;
    JLabel ipJLabel;
    JLabel portaJLabel;


    public TelaLogin(){
        config();
        adicionar();
        visivel();
    }

    private void config(){
        setSize(500, 450);
        setDefaultCloseOperation(TelaLogin.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);
        setBackground(new Color (195, 160 , 110));

        botao.setBounds(200,320,100,50);
        botao.setFont(new Font("Arial", Font.BOLD, 20));
        botao.setForeground(new Color (25, 25 , 25));
        botao.setBackground(new Color (181, 199 , 211));

        nomeField = new JTextField("");
        nomeField.setBounds(140, 50, 200, 45);

        ipField = new JTextField("");
        ipField.setBounds(140, 150, 200, 45);

        portaField = new JTextField("");
        portaField.setBounds(140, 250, 200, 45);

        nomeJLabel = new JLabel("Nome");
        nomeJLabel.setBounds(140, 15, 100, 45);
        nomeJLabel.setFont(new Font("Arial", Font.BOLD, 20));

        portaJLabel = new JLabel("Porta");
        portaJLabel.setBounds(140, 215, 100, 45);
        portaJLabel.setFont(new Font("Arial", Font.BOLD, 20));

        ipJLabel = new JLabel("IP");
        ipJLabel.setBounds(140, 115, 100, 45);
        ipJLabel.setFont(new Font("Arial", Font.BOLD, 20));

    };

    private void adicionar(){
        add(nomeField);
        add(ipField);
        add(portaField);
        add(botao);
        add(nomeJLabel);
        add(portaJLabel);
        add(ipJLabel);

        botao.addActionListener(this);


       
    };

    private void visivel(){
        setVisible(true);
    };





    


    
    public static void main (String [] args){
        TelaLogin teste = new TelaLogin();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
       
        if(nomeField.getText() == "" || ipField.getText() == "" || portaField.getText() == ""){
            JOptionPane.showMessageDialog(null, "DADOS FALTANDO", "ERRO",JOptionPane.ERROR_MESSAGE);
        }
       
       
        nome = nomeField.getText();
       ip = Integer.parseInt(ipField.getText());
       porta = portaField.getText();


       try {
       Cliente cliente = new Cliente(nome, ip, porta);

       
        cliente.start();
    } catch (IOException e1) {
        JOptionPane.showMessageDialog(null, "SERVIDOR PODE NÃO TER SIDO INICIADO", "ERRO",JOptionPane.ERROR_MESSAGE);
    }
        
    }

    @Override
    public Object getValue(String key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void putValue(String key, Object value) {
        // TODO Auto-generated method stub
        
    }
}

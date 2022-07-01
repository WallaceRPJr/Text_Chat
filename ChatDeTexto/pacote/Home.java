import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.plaf.ColorUIResource;

public class Home extends JFrame {

    private JLabel jl_titulo;
    private String infoConnection;
    private JButton jb_pegaConnection, jb_iniciaConversa; // pega os usuarios conectados com o servidor, e a outra para abrir uma janela de conversa
    private JList jlista; // lista dos usuários conectados
    private JScrollPane scr;

    public Home(String infoConnection) { // aqui será uma verificação se alguem estará usando o mesmo apelido ou a mesma porta.
        super("Chat - Home");
        this.infoConnection = infoConnection;
        iniciar();
        configurar();
        inserir();
        inserirAcoes();
        start();


    }
    private void iniciar(){
        jl_titulo = new JLabel("Conectado como : "+infoConnection.split(":")[0], SwingConstants.CENTER); //centraliza
        jb_pegaConnection = new JButton("Contatos Online");
        jb_iniciaConversa = new JButton("Iniciar conversa");
        jlista = new JList(); 
        scr = new JScrollPane(jlista); //muitos itens que não caibam na tela, então um rolamento é interessante


    }
    private void configurar(){
        this.setLayout(null);
        this.setMinimumSize(new Dimension(600, 480));
        this.setResizable(false); //não será redimensionavel
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //
        this.getContentPane().setBackground(new ColorUIResource(255, 255, 255));
        jl_titulo.setBounds(10, 10, 370, 40);
        jl_titulo.setBorder(BorderFactory.createLineBorder(new ColorUIResource(105, 105, 105)));

        jb_pegaConnection.setBounds(400, 10, 180, 40);
        jb_pegaConnection.setFocusable(false); //tira o foco do botão

        jb_iniciaConversa.setBounds(10, 400, 575, 40);
        jb_pegaConnection.setFocusable(false);

        jlista.setBorder(BorderFactory.createTitledBorder("Usuários online"));
        jlista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //nesse tipo não será selecionado mais de um usuário a um mesmo tempo. Uma coisa por vez

        scr.setBounds(10, 60, 575, 335);
        scr.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); //Aparecer a vertical somente quando necessário
        scr.setHorizontalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scr.setBorder(null); //se ativar, vai haver uma disputa com a borda do jlista
    }
    private void inserir(){
        this.add(jl_titulo);
        this.add(jb_iniciaConversa);
        this.add(jb_pegaConnection);
        this.add(jlista);


    }
    private void inserirAcoes(){

    }
    private void start(){
        this.pack();
        this.setVisible(true);

    }
    public static void main (String [] args){
        Home test = new Home("Lucas: 127.0.0.1 ");
    }

}

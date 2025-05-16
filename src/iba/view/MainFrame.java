package iba.view;

import javax.swing.*;

// Classe principal da interface gráfica, responsável por exibir o menu principal
public class MainFrame extends JFrame {

    // Construtor da janela principal
    public MainFrame() {
        setTitle("Sistema da Universidade de Teologia - IBA"); // Título da janela
        setSize(400, 350);  // Define o tamanho da janela
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Fecha o app ao clicar no X
        setLocationRelativeTo(null); // Centraliza a janela na tela
        initComponents(); // Inicializa os componentes da interface
    }

    // Inicializa e configura os botões do menu principal
    private void initComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Layout em coluna

        // Criação dos botões do menu principal
        JButton btnAlunos = new JButton("Gerenciar Alunos");
        JButton btnProfessores = new JButton("Gerenciar Professores");
        JButton btnCursos = new JButton("Gerenciar Cursos");
        JButton btnMaterias = new JButton("Gerenciar Matérias");
        JButton btnNotas = new JButton("Gerenciar Notas");

        // Define ações para os botões, alternando o painel exibido
        btnAlunos.addActionListener(e -> showPanel(new AlunoPanel(this)));
        btnProfessores.addActionListener(e -> showPanel(new ProfessorPanel(this)));
        btnCursos.addActionListener(e -> showPanel(new CursoPanel(this)));
        btnMaterias.addActionListener(e -> showPanel(new MateriaPanel(this)));
        btnNotas.addActionListener(e -> showPanel(new NotaPanel(this)));

        // Adiciona os botões ao painel
        panel.add(btnAlunos);
        panel.add(btnProfessores);
        panel.add(btnCursos);
        panel.add(btnMaterias);
        panel.add(btnNotas);

        // Define o painel como conteúdo da janela
        setContentPane(panel);
        revalidate(); // Atualiza a interface
    }

    // Método utilizado para trocar o painel exibido na janela
    public void showPanel(JPanel panel) {
        setContentPane(panel); // Define o novo painel
        revalidate(); // Atualiza o layout da janela
        repaint(); // Reforça a atualização visual
    }

    // Volta para o menu principal recriando os componentes
    public void voltarAoMenuPrincipal() {
        initComponents();
    }

    // Método main que inicia a aplicação
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true)); // Executa na thread de interface
    }
}

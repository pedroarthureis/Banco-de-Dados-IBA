package iba.view;

import iba.controller.AlunoController;
import iba.controller.CursoController;
import iba.controller.MateriaController;
import iba.model.Aluno;
import iba.model.Curso;
import iba.model.Materia;
import iba.model.Nota;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

// Painel de interface gráfica responsável pela visualização e manipulação dos dados dos alunos
public class AlunoPanel extends JPanel {
    private JTable tabela;                        // Tabela que exibe os alunos
    private DefaultTableModel modelo;             // Modelo da tabela (estrutura de dados)
    private MainFrame mainFrame;                  // Referência ao frame principal da aplicação

    // Construtor do painel de alunos
    public AlunoPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        // Define layout vertical para o painel
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Define colunas da tabela (inclui curso e notas)
        modelo = new DefaultTableModel(new Object[]{"Nome", "Registro", "Período", "Curso", "Notas"}, 0);
        tabela = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabela); // Scroll para tabela

        // Botões para ações do usuário
        JButton btnAdicionar = new JButton("Adicionar Aluno");
        JButton btnRemover = new JButton("Remover Selecionado");
        JButton btnVoltar = new JButton("Voltar ao Menu Principal");

        // Associa ações aos botões
        btnAdicionar.addActionListener(e -> adicionarAluno());
        btnRemover.addActionListener(e -> removerAluno());
        btnVoltar.addActionListener(e -> mainFrame.voltarAoMenuPrincipal());

        // Adiciona componentes ao painel
        add(scroll);
        add(btnAdicionar);
        add(btnRemover);
        add(btnVoltar);

        // Carrega os dados dos alunos na tabela ao iniciar
        carregarTabela();
    }

    // Carrega todos os alunos na tabela
    private void carregarTabela() {
        modelo.setRowCount(0); // Limpa a tabela

        for (Aluno aluno : AlunoController.getAlunos()) {
            // Monta uma string com as notas do aluno
            StringBuilder notasString = new StringBuilder();
            for (Nota nota : aluno.getNotas()) {
                if (notasString.length() > 0) {
                    notasString.append(", ");
                }
                notasString.append(nota.getMateria().getNome()).append(": ").append(nota.getValor());
            }

            // Pega o nome do curso ou mostra mensagem caso não tenha
            String nomeCurso = aluno.getCurso() != null ? aluno.getCurso().getNome() : "Curso não definido";

            // Adiciona linha à tabela
            modelo.addRow(new Object[]{aluno.getNome(), aluno.getRegistro(), aluno.getPeriodo(), nomeCurso, notasString.toString()});
        }
    }

    // Adiciona um novo aluno ao sistema
    private void adicionarAluno() {
        // Coleta dados básicos do aluno
        String nome = JOptionPane.showInputDialog("Nome do Aluno:");
        String registro = JOptionPane.showInputDialog("Número de Registro:");
        String periodo = JOptionPane.showInputDialog("Período:");

        if (nome != null && registro != null && periodo != null) {
            // Exibe lista de cursos disponíveis para seleção
            List<Curso> cursos = CursoController.getCursos();
            JComboBox<Curso> comboCursos = new JComboBox<>(cursos.toArray(new Curso[0]));
            int optionCurso = JOptionPane.showConfirmDialog(this, comboCursos, "Selecione o Curso", JOptionPane.OK_CANCEL_OPTION);

            if (optionCurso == JOptionPane.OK_OPTION) {
                Curso cursoSelecionado = (Curso) comboCursos.getSelectedItem();
                List<Materia> materias = cursoSelecionado != null ? cursoSelecionado.getMaterias() : new ArrayList<>();

                if (!materias.isEmpty()) {
                    // Coleta as notas para cada matéria do curso selecionado
                    List<Nota> notas = new ArrayList<>();
                    for (Materia materia : materias) {
                        String notaStr = JOptionPane.showInputDialog(this, "Informe a nota para " + materia.getNome() + ":");
                        try {
                            double nota = Double.parseDouble(notaStr); // Converte entrada para double
                            notas.add(new Nota(materia, nota));       // Adiciona nova nota à lista
                        } catch (NumberFormatException e) {
                            // Exibe mensagem de erro e cancela a adição do aluno
                            JOptionPane.showMessageDialog(this, "Nota inválida. A nota deve ser um número.");
                            return;
                        }
                    }

                    // Cria novo aluno e o adiciona ao sistema
                    Aluno aluno = new Aluno(nome, registro, cursoSelecionado, periodo, notas);
                    AlunoController.adicionarAluno(aluno);
                    carregarTabela(); // Atualiza a tabela
                } else {
                    // Caso o curso não tenha matérias
                    JOptionPane.showMessageDialog(this, "Curso não possui matérias associadas.");
                }
            }
        }
    }

    // Remove o aluno selecionado na tabela
    private void removerAluno() {
        int selected = tabela.getSelectedRow(); // Pega linha selecionada
        if (selected >= 0) {
            AlunoController.removerAluno(selected); // Remove do sistema
            carregarTabela(); // Atualiza a tabela
        }
    }
}

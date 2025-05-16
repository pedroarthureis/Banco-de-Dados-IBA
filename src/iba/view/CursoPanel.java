package iba.view;

import iba.controller.CursoController;
import iba.controller.MateriaController;
import iba.model.Curso;
import iba.model.Materia;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

// Painel da interface gráfica responsável por gerenciar cursos
public class CursoPanel extends JPanel {
    private JTable tabela;                        // Tabela para exibir os cursos
    private DefaultTableModel modelo;             // Modelo de dados da tabela
    private MainFrame mainFrame;                  // Referência à janela principal

    // Construtor do painel de cursos
    public CursoPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;

        // Define o layout vertical
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Define as colunas da tabela
        modelo = new DefaultTableModel(new Object[]{"Nome do Curso", "Matérias"}, 0);
        tabela = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabela); // Adiciona scroll à tabela

        // Cria os botões de ação
        JButton btnAdicionar = new JButton("Adicionar Curso");
        JButton btnRemover = new JButton("Remover Selecionado");
        JButton btnVoltar = new JButton("Voltar ao Menu Principal");

        // Associa eventos aos botões
        btnAdicionar.addActionListener(e -> adicionarCurso());
        btnRemover.addActionListener(e -> removerCurso());
        btnVoltar.addActionListener(e -> mainFrame.voltarAoMenuPrincipal());

        // Adiciona componentes ao painel
        add(scroll);
        add(btnAdicionar);
        add(btnRemover);
        add(btnVoltar);

        // Carrega os cursos existentes na tabela
        carregarTabela();
    }

    // Carrega os dados dos cursos na tabela
    private void carregarTabela() {
        modelo.setRowCount(0); // Limpa a tabela
        for (Curso c : CursoController.getCursos()) {
            StringBuilder materiasStr = new StringBuilder();
            for (Materia m : c.getMaterias()) {
                // Monta string com os nomes e códigos das matérias
                materiasStr.append(m.getNome()).append(" (").append(m.getCodigo()).append("), ");
            }
            // Adiciona linha à tabela com o nome do curso e suas matérias
            modelo.addRow(new Object[]{c.getNome(), materiasStr.toString()});
        }
    }

    // Permite ao usuário adicionar um novo curso
    private void adicionarCurso() {
        // Solicita o nome do novo curso
        String nome = JOptionPane.showInputDialog("Nome do Curso:");
        if (nome != null && !nome.isBlank()) {
            List<Materia> materias = new ArrayList<>();
            List<Materia> todasMaterias = MateriaController.obterMaterias(); // Obtém todas as matérias disponíveis
            String[] materiasArray = new String[todasMaterias.size()];

            // Preenche array com os nomes das matérias
            for (int i = 0; i < todasMaterias.size(); i++) {
                materiasArray[i] = todasMaterias.get(i).getNome();
            }

            // Exibe uma lista com múltipla seleção para escolher as matérias do curso
            JList<String> listaMaterias = new JList<>(materiasArray);
            listaMaterias.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            JScrollPane scrollPane = new JScrollPane(listaMaterias);
            int option = JOptionPane.showConfirmDialog(this, scrollPane, "Selecione as Matérias", JOptionPane.OK_CANCEL_OPTION);

            if (option == JOptionPane.OK_OPTION) {
                // Adiciona as matérias selecionadas ao curso
                for (int i : listaMaterias.getSelectedIndices()) {
                    materias.add(todasMaterias.get(i));
                }
                // Cria e adiciona o curso ao sistema
                Curso curso = new Curso(nome, materias);
                CursoController.adicionarCurso(curso);
                carregarTabela(); // Atualiza a tabela
            }
        }
    }

    // Remove o curso selecionado na tabela
    private void removerCurso() {
        int selected = tabela.getSelectedRow(); // Obtém índice da linha selecionada
        if (selected >= 0) {
            CursoController.removerCurso(selected); // Remove o curso pelo índice
            carregarTabela(); // Atualiza a tabela
        }
    }
}

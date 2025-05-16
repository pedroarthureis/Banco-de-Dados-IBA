package iba.view;

import iba.controller.AlunoController;
import iba.controller.CursoController;
import iba.controller.MateriaController;
import iba.model.Aluno;
import iba.model.Curso;
import iba.model.Materia;
import iba.model.Nota;

import javax.swing.*;
import java.awt.*;
import java.util.List;

// Painel responsável pelo gerenciamento de notas dos alunos
public class NotaPanel extends JPanel {
    private MainFrame mainFrame;

    public NotaPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout()); // Define o layout principal como BorderLayout

        // ComboBox para seleção de cursos
        JComboBox<Curso> comboCursos = new JComboBox<>();
        List<Curso> cursos = CursoController.getCursos(); // Obtém a lista de cursos disponíveis
        for (Curso curso : cursos) {
            comboCursos.addItem(curso); // Adiciona os cursos ao ComboBox
        }

        // ComboBox para seleção de matérias
        JComboBox<Materia> comboMaterias = new JComboBox<>();

        // Se houver cursos, carrega as matérias do primeiro curso inicialmente
        if (!cursos.isEmpty()) {
            List<Materia> materias = cursos.get(0).getMaterias();
            for (Materia materia : materias) {
                comboMaterias.addItem(materia); // Adiciona as matérias ao ComboBox
            }
        }

        // ComboBox para seleção de alunos
        JComboBox<Aluno> comboAlunos = new JComboBox<>();
        List<Aluno> alunos = AlunoController.getAlunos(); // Obtém a lista de alunos
        for (Aluno aluno : alunos) {
            comboAlunos.addItem(aluno); // Adiciona os alunos ao ComboBox
        }

        // Campo de texto para entrada da nota
        JTextField txtNota = new JTextField(10);

        // Botões para salvar a nota e voltar ao menu principal
        JButton btnSalvarNota = new JButton("Salvar Nota");
        JButton btnVoltar = new JButton("Voltar ao Menu Principal");

        // Ação executada ao clicar no botão "Salvar Nota"
        btnSalvarNota.addActionListener(e -> {
            Aluno alunoSelecionado = (Aluno) comboAlunos.getSelectedItem();
            Materia materiaSelecionada = (Materia) comboMaterias.getSelectedItem();
            String notaStr = txtNota.getText();

            // Verifica se todos os campos estão preenchidos
            if (alunoSelecionado == null || materiaSelecionada == null || notaStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos corretamente.");
                return;
            }

            try {
                double valorNota = Double.parseDouble(notaStr); // Converte a string para número
                // Verifica se o valor está no intervalo válido
                if (valorNota < 0.0 || valorNota > 10.0) {
                    JOptionPane.showMessageDialog(this, "A nota deve estar entre 0.0 e 10.0");
                    return;
                }

                // Cria e salva a nota para o aluno
                Nota nota = new Nota(materiaSelecionada, valorNota);
                AlunoController.atualizarNota(alunoSelecionado.getRegistro(), nota);

                JOptionPane.showMessageDialog(this, "Nota salva com sucesso!");
            } catch (NumberFormatException ex) {
                // Mostra mensagem se o valor não for numérico
                JOptionPane.showMessageDialog(this, "Digite um número válido para a nota.");
            }
        });

        // Ação executada ao clicar no botão "Voltar"
        btnVoltar.addActionListener(e -> mainFrame.voltarAoMenuPrincipal());

        // Atualiza a lista de matérias quando o curso selecionado for alterado
        comboCursos.addActionListener(e -> {
            Curso cursoSelecionado = (Curso) comboCursos.getSelectedItem();
            comboMaterias.removeAllItems(); // Limpa as matérias atuais
            if (cursoSelecionado != null) {
                List<Materia> materias = cursoSelecionado.getMaterias();
                for (Materia materia : materias) {
                    comboMaterias.addItem(materia); // Adiciona as novas matérias
                }
            }
        });

        // Painel interno com layout horizontal para disposição dos componentes
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(new JLabel("Curso:"));
        panel.add(comboCursos);
        panel.add(new JLabel("Aluno:"));
        panel.add(comboAlunos);
        panel.add(new JLabel("Nota:"));
        panel.add(txtNota);
        panel.add(new JLabel("Matéria:"));
        panel.add(comboMaterias);
        panel.add(btnSalvarNota);
        panel.add(btnVoltar);

        // Adiciona o painel ao centro do layout principal
        add(panel, BorderLayout.CENTER);
    }
}

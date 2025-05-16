package iba.view;

import iba.controller.MateriaController;
import iba.model.Materia;

import javax.swing.*;
import java.awt.*;

// Painel responsável pela interface de gerenciamento de matérias
public class MateriaPanel extends JPanel {

    private MainFrame mainFrame; // Referência para a janela principal

    public MateriaPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        setLayout(new BorderLayout()); // Define o layout principal como BorderLayout

        // Campos de texto para entrada do nome e código da matéria
        JTextField txtNomeMateria = new JTextField(15);
        JTextField txtCodigoMateria = new JTextField(10);

        // Botões para adicionar matéria e voltar ao menu principal
        JButton btnAdicionarMateria = new JButton("Adicionar Matéria");
        JButton btnVoltar = new JButton("Voltar ao Menu Principal");

        // Ação do botão "Adicionar Matéria"
        btnAdicionarMateria.addActionListener(e -> {
            String nome = txtNomeMateria.getText(); // Lê o nome digitado
            String codigo = txtCodigoMateria.getText(); // Lê o código digitado
            if (!nome.isEmpty() && !codigo.isEmpty()) {
                // Cria e adiciona a matéria se os campos estiverem preenchidos
                Materia materia = new Materia(nome, codigo);
                MateriaController.adicionarMateria(materia);
                JOptionPane.showMessageDialog(this, "Matéria adicionada com sucesso!");
                txtNomeMateria.setText(""); // Limpa os campos após adicionar
                txtCodigoMateria.setText("");
            } else {
                // Mostra mensagem de erro se houver campos vazios
                JOptionPane.showMessageDialog(this, "Preencha todos os campos.");
            }
        });

        // Ação do botão "Voltar ao Menu Principal"
        btnVoltar.addActionListener(e -> mainFrame.voltarAoMenuPrincipal());

        // Painel para organizar os componentes com layout de fluxo (horizontal)
        JPanel panel = new JPanel(new FlowLayout());
        panel.add(new JLabel("Nome da Matéria:")); // Rótulo para nome
        panel.add(txtNomeMateria); // Campo de entrada para nome
        panel.add(new JLabel("Código:")); // Rótulo para código
        panel.add(txtCodigoMateria); // Campo de entrada para código
        panel.add(btnAdicionarMateria); // Botão de adicionar
        panel.add(btnVoltar); // Botão de voltar

        // Adiciona o painel ao centro do layout principal
        add(panel, BorderLayout.CENTER);
    }
}

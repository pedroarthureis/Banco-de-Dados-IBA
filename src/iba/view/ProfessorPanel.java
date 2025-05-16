package iba.view;

import iba.controller.ProfessorController;
import iba.model.Professor;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ProfessorPanel extends JPanel {
   private JTable tabela; // Tabela para exibir os professores
   private DefaultTableModel modelo; // Modelo da tabela para manipular os dados
   private JFrame parentFrame; // Referência da janela que contém o painel

   // Construtor recebe a janela pai para controle de navegação
   public ProfessorPanel(JFrame parentFrame) {
      this.parentFrame = parentFrame;

      setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Layout vertical para os componentes
      modelo = new DefaultTableModel(new Object[]{"Nome", "CPF", "Área"}, 0); // Colunas da tabela
      tabela = new JTable(modelo);
      JScrollPane scroll = new JScrollPane(tabela); // Scroll para a tabela

      // Botões para adicionar, remover e voltar
      JButton btnAdicionar = new JButton("Adicionar Professor");
      JButton btnRemover = new JButton("Remover Selecionado");
      JButton btnVoltar = new JButton("Voltar ao Menu Principal");

      // Ações dos botões
      btnAdicionar.addActionListener(e -> adicionarProfessor());
      btnRemover.addActionListener(e -> removerProfessor());
      btnVoltar.addActionListener(e -> voltarAoMenu());

      // Adiciona componentes ao painel
      add(scroll);
      add(btnAdicionar);
      add(btnRemover);
      add(btnVoltar);

      carregarTabela(); // Preenche a tabela com dados existentes
   }

   // Método para carregar os dados dos professores na tabela
   private void carregarTabela() {
      modelo.setRowCount(0); // Limpa a tabela antes de recarregar
      List<Professor> lista = ProfessorController.getProfessores(); // Obtém a lista atualizada

      // Adiciona cada professor como uma linha na tabela
      for (Professor p : lista) {
         modelo.addRow(new Object[]{p.getNome(), p.getCpf(), p.getArea()});
      }
   }

   // Método para adicionar um novo professor via diálogo
   private void adicionarProfessor() {
      String nome = JOptionPane.showInputDialog("Nome do Professor:");
      String cpf = JOptionPane.showInputDialog("CPF:");
      String area = JOptionPane.showInputDialog("Área de Atuação:");

      // Verifica se todos os campos foram preenchidos
      if (nome != null && cpf != null && area != null) {
         Professor p = new Professor(nome, cpf, area);
         ProfessorController.adicionar(p); // Adiciona o professor ao controlador
         carregarTabela(); // Atualiza a tabela com o novo dado
      }
   }

   // Método para remover o professor selecionado na tabela
   private void removerProfessor() {
      int selected = tabela.getSelectedRow();
      if (selected >= 0) {
         ProfessorController.remover(selected); // Remove pelo índice selecionado
         carregarTabela(); // Atualiza a tabela após remoção
      }
   }

   // Método para voltar ao menu principal
   private void voltarAoMenu() {
      parentFrame.dispose(); // Fecha a janela atual
      new MainFrame().setVisible(true); // Abre uma nova janela do menu principal
   }
}

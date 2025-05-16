package iba.model;

// Classe que representa um professor com nome, CPF e área de atuação
public class Professor {
   private String nome; // Nome do professor
   private String cpf;  // CPF do professor
   private String area; // Área de atuação do professor

   // Construtor que inicializa os atributos nome, cpf e área
   public Professor(String nome, String cpf, String area) {
      this.nome = nome;
      this.cpf = cpf;
      this.area = area;
   }

   // Retorna o nome do professor
   public String getNome() {
      return nome;
   }

   // Retorna o CPF do professor
   public String getCpf() {
      return cpf;
   }

   // Retorna a área de atuação do professor
   public String getArea() {
      return area;
   }

   // Define o nome do professor
   public void setNome(String nome) {
      this.nome = nome;
   }

   // Define o CPF do professor
   public void setCpf(String cpf) {
      this.cpf = cpf;
   }

   // Define a área de atuação do professor
   public void setArea(String area) {
      this.area = area;
   }
}

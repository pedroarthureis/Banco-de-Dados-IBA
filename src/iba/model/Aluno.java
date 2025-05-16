package iba.model;

import java.util.List;

public class Aluno {
   // Atributos privados do aluno
   private String nome;           // Nome do aluno
   private String registro;       // Número de registro do aluno
   private Curso curso;           // Curso ao qual o aluno está vinculado
   private String periodo;        // Período atual do aluno (ex: "1º período")
   private List<Nota> notas;      // Lista de notas do aluno

   // Construtor que inicializa todos os atributos do aluno
   public Aluno(String nome, String registro, Curso curso, String periodo, List<Nota> notas) {
      this.nome = nome;
      this.registro = registro;
      this.curso = curso;
      this.periodo = periodo;
      this.notas = notas;
   }

   // Getter para o nome
   public String getNome() {
      return this.nome;
   }

   // Setter para o nome
   public void setNome(String nome) {
      this.nome = nome;
   }

   // Getter para o registro
   public String getRegistro() {
      return this.registro;
   }

   // Setter para o registro
   public void setRegistro(String registro) {
      this.registro = registro;
   }

   // Getter para o curso
   public Curso getCurso() {
      return this.curso;
   }

   // Setter para o curso
   public void setCurso(Curso curso) {
      this.curso = curso;
   }

   // Getter para o período
   public String getPeriodo() {
      return this.periodo;
   }

   // Setter para o período
   public void setPeriodo(String periodo) {
      this.periodo = periodo;
   }

   // Getter para a lista de notas
   public List<Nota> getNotas() {
      return this.notas;
   }

   // Setter para a lista de notas
   public void setNotas(List<Nota> notas) {
      this.notas = notas;
   }

   // Representação textual do aluno (nome e registro)
   public String toString() {
      return this.nome + " (" + this.registro + ")";
   }
}

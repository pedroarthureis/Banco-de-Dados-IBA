package iba.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// Classe que representa um curso, contendo um nome e uma lista de matérias
public class Curso {
    private String nome; // Nome do curso
    private List<Materia> materias; // Lista de matérias associadas ao curso

    // Construtor que inicializa o nome e a lista de matérias
    public Curso(String nome, List<Materia> materias) {
        this.nome = nome;
        this.materias = materias;
    }

    // Retorna o nome do curso
    public String getNome() {
        return this.nome;
    }

    // Retorna a lista de matérias do curso
    public List<Materia> getMaterias() {
        return this.materias;
    }

    // Define o nome do curso
    public void setNome(String nome) {
        this.nome = nome;
    }

    // Define a lista de matérias do curso
    public void setMaterias(List<Materia> materias) {
        this.materias = materias;
    }

    // Adiciona uma nova matéria à lista de matérias do curso
    public void adicionarMateria(Materia materia) {
        this.materias.add(materia);
    }

    // Retorna uma representação em string do curso (apenas o nome)
    @Override
    public String toString() {
        return nome;  // Sobrescreve o toString para retornar apenas o nome do curso
    }

    // Converte o objeto Curso em um objeto JSON
    public JSONObject toJson() {
        JSONObject obj = new JSONObject(); // Cria o objeto JSON principal
        obj.put("nome", nome); // Adiciona o nome ao JSON
        JSONArray materiasArray = new JSONArray(); // Cria um array JSON para as matérias
        for (Materia m : materias) {
            materiasArray.put(m.toJson()); // Adiciona cada matéria convertida em JSON ao array
        }
        obj.put("materias", materiasArray); // Adiciona o array de matérias ao objeto JSON
        return obj;
    }

    // Constrói um objeto Curso a partir de um objeto JSON
    public static Curso fromJson(JSONObject obj) {
        String nome = obj.getString("nome"); // Obtém o nome do curso do JSON
        JSONArray materiasArray = obj.getJSONArray("materias"); // Obtém o array de matérias do JSON
        List<Materia> materias = new ArrayList<>(); // Cria uma nova lista para armazenar as matérias
        for (int i = 0; i < materiasArray.length(); i++) {
            materias.add(Materia.fromJson(materiasArray.getJSONObject(i))); // Converte cada JSON de matéria em objeto Materia
        }
        return new Curso(nome, materias); // Retorna um novo objeto Curso com os dados obtidos
    }
}

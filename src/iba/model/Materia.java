package iba.model;

import org.json.JSONObject;

// Classe que representa uma matéria com nome e código
public class Materia {
    private String nome;   // Nome da matéria
    private String codigo; // Código da matéria

    // Construtor que inicializa o nome e o código da matéria
    public Materia(String nome, String codigo) {
        this.nome = nome;
        this.codigo = codigo;
    }

    // Retorna o nome da matéria
    public String getNome() {
        return nome;
    }

    // Retorna o código da matéria
    public String getCodigo() {
        return codigo;
    }

    // Retorna uma representação textual da matéria: "nome (código)"
    @Override
    public String toString() {
        return nome + " (" + codigo + ")";
    }

    // Converte a matéria para um objeto JSON
    public JSONObject toJson() {
        JSONObject obj = new JSONObject(); // Cria um novo objeto JSON
        obj.put("nome", nome);             // Adiciona o nome ao JSON
        obj.put("codigo", codigo);         // Adiciona o código ao JSON
        return obj;                        // Retorna o objeto JSON
    }

    // Cria uma instância de Materia a partir de um objeto JSON
    public static Materia fromJson(JSONObject obj) {
        return new Materia(obj.getString("nome"), obj.getString("codigo"));
    }
}

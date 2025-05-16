package iba.model;

import org.json.JSONObject;

// Classe que representa uma nota associada a uma matéria
public class Nota {
    private Materia materia; // Matéria à qual a nota está vinculada
    private double valor;    // Valor numérico da nota

    // Construtor que inicializa a matéria e o valor da nota
    public Nota(Materia materia, double valor) {
        this.materia = materia;
        this.valor = valor;
    }

    // Retorna a matéria associada à nota
    public Materia getMateria() {
        return this.materia;
    }

    // Define a matéria associada à nota
    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    // Retorna o valor da nota
    public double getValor() {
        return this.valor;
    }

    // Define o valor da nota
    public void setValor(double valor) {
        this.valor = valor;
    }

    // Retorna uma representação textual da nota (ex: "Matemática: 8.5")
    @Override
    public String toString() {
        return this.materia.getNome() + ": " + this.valor;
    }

    // Converte a nota para um objeto JSON
    public JSONObject toJson() {
        JSONObject obj = new JSONObject();            // Cria novo objeto JSON
        obj.put("valor", valor);                      // Adiciona o valor da nota
        obj.put("materia", materia.toJson());         // Adiciona a matéria convertida em JSON
        return obj;                                    // Retorna o objeto JSON
    }

    // Cria uma instância de Nota a partir de um objeto JSON
    public static Nota fromJson(JSONObject obj) {
        Materia materia = Materia.fromJson(obj.getJSONObject("materia")); // Converte o JSON da matéria em objeto Materia
        double valor = obj.getDouble("valor");                            // Obtém o valor da nota
        return new Nota(materia, valor);                                  // Retorna nova instância de Nota
    }
}

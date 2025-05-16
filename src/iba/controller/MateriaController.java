package iba.controller;

import iba.model.Materia;
import java.util.ArrayList;
import java.util.List;

public class MateriaController {
    // Lista estática que armazena todas as matérias em memória
    private static List<Materia> materias = new ArrayList<>();

    // Método para adicionar uma nova matéria à lista
    public static void adicionarMateria(Materia materia) {
        materias.add(materia);
    }

    // Método para obter a lista atual de matérias
    public static List<Materia> obterMaterias() {
        return materias;
    }

    // Método para remover uma matéria da lista
    public static void removerMateria(Materia materia) {
        materias.remove(materia);
    }

    // Método para editar (substituir) uma matéria existente por uma nova
    public static void editarMateria(Materia materiaAntiga, Materia materiaNova) {
        int index = materias.indexOf(materiaAntiga); // Procura o índice da matéria antiga
        if (index != -1) {
            materias.set(index, materiaNova); // Substitui a matéria se encontrada
        }
    }
}

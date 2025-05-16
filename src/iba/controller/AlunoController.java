package iba.controller;

import iba.model.Aluno;
import iba.model.Materia;
import iba.model.Nota;

import java.util.ArrayList;
import java.util.List;

public class AlunoController {

    // Lista estática de alunos carregada a partir do JSON
    private static List<Aluno> alunos = JsonManager.carregarAlunos();

    // Retorna a lista de todos os alunos
    public static List<Aluno> getAlunos() {
        return alunos;
    }

    // Adiciona um novo aluno à lista e salva no JSON
    public static void adicionarAluno(Aluno aluno) {
        alunos.add(aluno);
        JsonManager.salvarAlunos(alunos);
    }

    // Atualiza um aluno existente na lista pelo índice e salva no JSON
    public static void atualizarAluno(int index, Aluno alunoAtualizado) {
        alunos.set(index, alunoAtualizado);
        JsonManager.salvarAlunos(alunos);
    }

    // Remove um aluno da lista com base no índice, se for válido, e salva no JSON
    public static void removerAluno(int index) {
        if (index >= 0 && index < alunos.size()) {
            alunos.remove(index);
            JsonManager.salvarAlunos(alunos);
        }
    }

    // Busca e retorna um aluno com base no registro informado
    public static Aluno getAlunoPorRegistro(String registro) {
        for (Aluno a : alunos) {
            if (a.getRegistro().equals(registro)) {
                return a;
            }
        }
        return null; // Retorna null se não encontrar
    }

    // Adiciona uma nota para um aluno identificado pelo registro
    public static void adicionarNota(String registroAluno, Nota nota) {
        Aluno aluno = getAlunoPorRegistro(registroAluno);
        if (aluno != null) {
            aluno.getNotas().add(nota);
            JsonManager.salvarAlunos(alunos);
        }
    }

    // Atualiza uma nota existente para um aluno, com base no código da matéria
    public static void atualizarNota(String registroAluno, Nota notaAtualizada) {
        Aluno aluno = getAlunoPorRegistro(registroAluno);
        if (aluno != null) {
            List<Nota> notas = aluno.getNotas();
            for (int i = 0; i < notas.size(); i++) {
                Nota n = notas.get(i);
                if (n.getMateria().getCodigo().equals(notaAtualizada.getMateria().getCodigo())) {
                    notas.set(i, notaAtualizada);
                    JsonManager.salvarAlunos(alunos);
                    return; // Sai após atualizar a nota
                }
            }
        }
    }

    // Remove uma nota de um aluno com base no código da matéria
    public static void removerNota(String registroAluno, String codigoMateria) {
        Aluno aluno = getAlunoPorRegistro(registroAluno);
        if (aluno != null) {
            aluno.getNotas().removeIf(n -> n.getMateria().getCodigo().equals(codigoMateria));
            JsonManager.salvarAlunos(alunos);
        }
    }

    // Retorna uma nota específica de um aluno com base no código da matéria
    public static Nota getNota(String registroAluno, String codigoMateria) {
        Aluno aluno = getAlunoPorRegistro(registroAluno);
        if (aluno != null) {
            for (Nota n : aluno.getNotas()) {
                if (n.getMateria().getCodigo().equals(codigoMateria)) {
                    return n;
                }
            }
        }
        return null; // Retorna null se a nota não for encontrada
    }

    // Retorna a lista de matérias do curso do aluno
    public static List<Materia> getMateriasDoAluno(String registroAluno) {
        Aluno aluno = getAlunoPorRegistro(registroAluno);
        if (aluno != null && aluno.getCurso() != null) {
            return aluno.getCurso().getMaterias();
        }
        return new ArrayList<>(); // Retorna lista vazia se o aluno ou o curso for nulo
    }

}

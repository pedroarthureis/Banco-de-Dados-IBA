package iba.controller;

import iba.model.Professor;
import iba.util.FileUtil;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class ProfessorController {
   // Nome do arquivo onde os dados dos professores serão armazenados
   private static final String FILE = "professores.json";

   // Lista de professores carregada do arquivo ao iniciar a aplicação
   private static List<Professor> professores = carregar();

   // Método responsável por carregar os dados dos professores do arquivo JSON
   private static List<Professor> carregar() {
      List<Professor> lista = new ArrayList<>();

      // Verifica se o arquivo existe; se não existir, retorna lista vazia
      if (!FileUtil.fileExists(FILE)) return lista;

      try {
         // Lê o conteúdo do arquivo e cria um JSONArray com os dados
         String jsonStr = FileUtil.readFile(FILE);
         JSONArray array = new JSONArray(jsonStr);

         // Itera sobre cada objeto JSON no array e cria instâncias de Professor
         for (int i = 0; i < array.length(); i++) {
            JSONObject obj = array.getJSONObject(i);
            Professor p = new Professor(
               obj.getString("nome"),
               obj.getString("cpf"),
               obj.getString("area")
            );
            lista.add(p); // Adiciona o professor à lista
         }
      } catch (Exception e) {
         e.printStackTrace(); // Em caso de erro, imprime o stack trace
      }

      return lista; // Retorna a lista de professores carregada
   }

   // Retorna a lista atual de professores
   public static List<Professor> getProfessores() {
      return professores;
   }

   // Adiciona um novo professor à lista e salva no arquivo
   public static void adicionar(Professor p) {
      professores.add(p);
      salvar();
   }

   // Remove um professor da lista com base no índice, se for válido
   public static void remover(int index) {
      if (index >= 0 && index < professores.size()) {
         professores.remove(index);
         salvar(); // Atualiza o arquivo após a remoção
      }
   }

   // Salva a lista atual de professores no arquivo JSON
   private static void salvar() {
      JSONArray array = new JSONArray();

      // Converte cada Professor da lista para um JSONObject e adiciona ao array
      for (Professor p : professores) {
         JSONObject obj = new JSONObject();
         obj.put("nome", p.getNome());
         obj.put("cpf", p.getCpf());
         obj.put("area", p.getArea());
         array.put(obj); // Adiciona o objeto ao JSONArray
      }

      try {
         // Escreve o conteúdo formatado do JSONArray no arquivo
         FileUtil.writeFile(FILE, array.toString(4));
      } catch (Exception e) {
         e.printStackTrace(); // Em caso de erro na escrita, imprime o stack trace
      }
   }
}

package iba.controller;

import iba.model.Curso;
import iba.model.Materia;
import iba.util.FileUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class CursoController {
   // Caminho do arquivo JSON onde os cursos são armazenados
   private static final String FILE = "cursos.json";

   // Lista de cursos carregada na inicialização da classe
   private static List<Curso> cursos = carregar();

   // Método responsável por carregar os cursos a partir do arquivo JSON
   private static List<Curso> carregar() {
      List<Curso> lista = new ArrayList();
      
      // Verifica se o arquivo existe; se não, retorna lista vazia
      if (!FileUtil.fileExists("cursos.json")) {
         return lista;
      } else {
         try {
            // Lê o conteúdo do arquivo e converte para JSONArray
            String jsonStr = FileUtil.readFile("cursos.json");
            JSONArray array = new JSONArray(jsonStr);

            // Itera sobre cada objeto JSON para criar os cursos
            for(int i = 0; i < array.length(); ++i) {
               JSONObject obj = array.getJSONObject(i);
               String nome = obj.getString("nome");

               // Lê a lista de matérias do curso
               JSONArray materiasJson = obj.getJSONArray("materias");
               List<Materia> materias = new ArrayList();

               // Itera sobre as matérias e cria objetos Materia
               for(int j = 0; j < materiasJson.length(); ++j) {
                  JSONObject mObj = materiasJson.getJSONObject(j);
                  materias.add(new Materia(mObj.getString("nome"), mObj.getString("codigo")));
               }

               // Adiciona o curso à lista
               lista.add(new Curso(nome, materias));
            }
         } catch (Exception var10) {
            // Imprime o erro no console, se ocorrer exceção
            var10.printStackTrace();
         }

         // Retorna a lista de cursos carregados
         return lista;
      }
   }

   // Retorna a lista atual de cursos
   public static List<Curso> getCursos() {
      return cursos;
   }

   // Adiciona um novo curso à lista e salva no arquivo JSON
   public static void adicionarCurso(Curso curso) {
      cursos.add(curso);
      salvar();
   }

   // Remove um curso da lista pelo índice, se for válido, e salva
   public static void removerCurso(int index) {
      if (index >= 0 && index < cursos.size()) {
         cursos.remove(index);
         salvar();
      }
   }

   // Salva a lista de cursos no arquivo JSON
   private static void salvar() {
      JSONArray array = new JSONArray();
      Iterator var1 = cursos.iterator();

      // Para cada curso, cria um objeto JSON correspondente
      while(var1.hasNext()) {
         Curso curso = (Curso)var1.next();
         JSONObject obj = new JSONObject();
         obj.put("nome", curso.getNome());

         // Converte a lista de matérias do curso para JSON
         JSONArray materias = new JSONArray();
         Iterator var5 = curso.getMaterias().iterator();

         while(var5.hasNext()) {
            Materia m = (Materia)var5.next();
            JSONObject mObj = new JSONObject();
            mObj.put("nome", m.getNome());
            mObj.put("codigo", m.getCodigo());
            materias.put(mObj);
         }

         // Adiciona o array de matérias ao curso
         obj.put("materias", materias);

         // Adiciona o curso ao array principal
         array.put(obj);
      }

      try {
         // Escreve o conteúdo no arquivo com indentação
         FileUtil.writeFile("cursos.json", array.toString(4));
      } catch (Exception var8) {
         // Imprime erro, caso ocorra durante escrita
         var8.printStackTrace();
      }
   }
}

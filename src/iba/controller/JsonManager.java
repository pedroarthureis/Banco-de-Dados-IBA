package iba.controller;

import iba.model.Aluno;
import iba.model.Curso;
import iba.model.Materia;
import iba.model.Nota;
import iba.util.FileUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class JsonManager {
   // Caminho do arquivo onde os dados dos alunos serão armazenados
   private static final String ALUNOS_FILE = "alunos.json";

   // Método responsável por salvar a lista de alunos no arquivo JSON
   public static void salvarAlunos(List<Aluno> alunos) {
      JSONArray array = new JSONArray(); // Array JSON que armazenará os dados de todos os alunos
      Iterator var2 = alunos.iterator();

      while(var2.hasNext()) {
         Aluno a = (Aluno)var2.next();
         JSONObject obj = new JSONObject(); // Objeto JSON para representar um aluno individual
         
         // Adiciona informações básicas do aluno
         obj.put("nome", a.getNome());
         obj.put("registro", a.getRegistro());
         obj.put("periodo", a.getPeriodo());

         // Adiciona informações do curso do aluno
         JSONObject curso = new JSONObject();
         curso.put("nome", a.getCurso().getNome());

         // Adiciona as matérias do curso
         JSONArray materias = new JSONArray();
         Iterator var7 = a.getCurso().getMaterias().iterator();
         while(var7.hasNext()) {
            Materia m = (Materia)var7.next();
            JSONObject mat = new JSONObject();
            mat.put("nome", m.getNome());
            mat.put("codigo", m.getCodigo());
            materias.put(mat);
         }
         curso.put("materias", materias);
         obj.put("curso", curso);

         // Adiciona as notas do aluno
         JSONArray notas = new JSONArray();
         Iterator var13 = a.getNotas().iterator();
         while(var13.hasNext()) {
            Nota nota = (Nota)var13.next();
            JSONObject notaObj = new JSONObject();
            notaObj.put("materia", nota.getMateria().getNome());
            notaObj.put("codigo", nota.getMateria().getCodigo());
            notaObj.put("valor", nota.getValor());
            notas.put(notaObj);
         }
         obj.put("notas", notas);

         // Adiciona o objeto do aluno ao array principal
         array.put(obj);
      }

      try {
         // Escreve os dados no arquivo com indentação de 4 espaços
         FileUtil.writeFile("alunos.json", array.toString(4));
      } catch (IOException var11) {
         // Em caso de erro ao escrever o arquivo, imprime o stack trace
         var11.printStackTrace();
      }
   }

   // Método responsável por carregar a lista de alunos a partir do arquivo JSON
   public static List<Aluno> carregarAlunos() {
      List<Aluno> alunos = new ArrayList();

      // Se o arquivo não existir, retorna uma lista vazia
      if (!FileUtil.fileExists("alunos.json")) {
         return alunos;
      } else {
         try {
            // Lê o conteúdo do arquivo e converte em JSONArray
            String jsonStr = FileUtil.readFile("alunos.json");
            JSONArray array = new JSONArray(jsonStr);

            // Itera sobre cada aluno no array
            for(int i = 0; i < array.length(); ++i) {
               JSONObject obj = array.getJSONObject(i);
               String nome = obj.getString("nome");
               String registro = obj.getString("registro");
               String periodo = obj.getString("periodo");

               // Reconstrói o curso e suas matérias
               JSONObject cursoObj = obj.getJSONObject("curso");
               String cursoNome = cursoObj.getString("nome");
               List<Materia> materias = new ArrayList();
               JSONArray matArray = cursoObj.getJSONArray("materias");
               for(int j = 0; j < matArray.length(); ++j) {
                  JSONObject m = matArray.getJSONObject(j);
                  materias.add(new Materia(m.getString("nome"), m.getString("codigo")));
               }
               Curso curso = new Curso(cursoNome, materias);

               // Reconstrói a lista de notas do aluno
               List<Nota> notas = new ArrayList();
               JSONArray notasArray = obj.getJSONArray("notas");
               for(int j = 0; j < notasArray.length(); ++j) {
                  JSONObject n = notasArray.getJSONObject(j);
                  Materia m = new Materia(n.getString("materia"), n.getString("codigo"));
                  notas.add(new Nota(m, n.getDouble("valor")));
               }

               // Cria o objeto Aluno e adiciona à lista
               alunos.add(new Aluno(nome, registro, curso, periodo, notas));
            }
         } catch (Exception var18) {
            // Em caso de erro na leitura ou parsing do arquivo, imprime o stack trace
            var18.printStackTrace();
         }

         // Retorna a lista de alunos carregada
         return alunos;
      }
   }
}

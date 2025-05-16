package iba.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Paths;

// Classe utilitária para operações com arquivos
public class FileUtil {

   // Lê todo o conteúdo de um arquivo e retorna como String
   public static String readFile(String path) throws IOException {
      return new String(Files.readAllBytes(Paths.get(path)));
   }

   // Escreve o conteúdo (String) em um arquivo no caminho especificado
   public static void writeFile(String path, String content) throws IOException {
      Files.write(Paths.get(path), content.getBytes(), new OpenOption[0]);
   }

   // Verifica se um arquivo existe no caminho especificado
   public static boolean fileExists(String path) {
      return Files.exists(Paths.get(path), new LinkOption[0]);
   }
}

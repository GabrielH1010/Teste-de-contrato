package pact.utils;

import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Utils {

    public static JSONObject lerArquivoJson(String nomeArquivo) {
        String caminho = "src/test/resources/fixtures/" + nomeArquivo;
        try {
            String conteudo = new String(Files.readAllBytes(Paths.get(caminho)));
            return new JSONObject(conteudo);
        } catch (IOException erro) {
            throw new RuntimeException("Erro ao ler o arquivo JSON: " + caminho, erro);
        }
    }
}

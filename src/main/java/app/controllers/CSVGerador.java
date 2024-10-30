package app.controllers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class CSVGerador {
    public static void gerarCsv(Map<String, Map<String, Double>> medias, String caminhoArquivo) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo))) {
            writer.write("Usuário,Critério,Média");
            writer.newLine();

            for (Map.Entry<String, Map<String, Double>> entry : medias.entrySet()) {
                String usuario = entry.getKey();
                Map<String, Double> criterios = entry.getValue();

                for (Map.Entry<String, Double> criterioEntry : criterios.entrySet()) {
                    String criterio = criterioEntry.getKey();
                    Double media = criterioEntry.getValue();
                    writer.write(String.format("%s,%s,%.2f", usuario, criterio, media));
                    writer.newLine();
                }
            }

            System.out.println("Arquivo CSV gerado com sucesso em: " + caminhoArquivo);
        } catch (IOException e) {
            System.err.println("Erro ao gerar o arquivo CSV: " + e.getMessage());
        }
    }
}
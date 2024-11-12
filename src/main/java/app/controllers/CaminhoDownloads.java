package app.controllers;

import java.nio.file.Path;
import java.nio.file.Paths;

public class CaminhoDownloads {

    public static String obterCaminhoDownloads() {
        String os = System.getProperty("os.name").toLowerCase();
        Path caminho;

        if (os.contains("win") || os.contains("mac") || os.contains("nix") || os.contains("nux")) {
            caminho = Paths.get(System.getProperty("user.home"), "Downloads");
        } else {
            caminho = Paths.get(System.getProperty("user.home"), "Documentos");
        }

        return caminho.toString();
    }
}

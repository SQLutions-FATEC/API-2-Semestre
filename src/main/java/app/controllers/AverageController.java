package app.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AverageController {
    @FXML
    private Label nomeLabel;

    public void inicializarDados(String nomeUsuario) {
        nomeLabel.setText("Detalhes do usuário: " + nomeUsuario);
        // Aqui você pode buscar e mostrar mais dados do usuário com base no nome
    }
}

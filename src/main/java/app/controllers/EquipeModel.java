package app.controllers;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class EquipeModel {

    private SimpleStringProperty nome;
    private SimpleStringProperty link_github;

    public EquipeModel(String nome, String link_github){
        this.nome = new SimpleStringProperty(nome);
        this.link_github = new SimpleStringProperty(link_github);
    }

    public String getNome() {
        return nome.get();
    }

    public void setNome(String nome) {
        this.nome = new SimpleStringProperty(nome);
    }

    public String getLink_github() {
        return link_github.get();
    }

    public void setLink_github(String link_github) {
        this.link_github = new SimpleStringProperty(link_github);
    }

}

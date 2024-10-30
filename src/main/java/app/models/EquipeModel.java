package app.models;

import javafx.beans.property.SimpleStringProperty;

public class EquipeModel {

    private SimpleStringProperty nome;
    private SimpleStringProperty github;

    public EquipeModel(String nome, String github){
        this.nome = new SimpleStringProperty(nome);
        this.github = new SimpleStringProperty(github);
    }

    public String getNome() {
        return nome.get();
    }

    public void setNome(String nome) {
        this.nome = new SimpleStringProperty(nome);
    }

    public String getGithub() {
        return github.get();
    }

    public void setGithub(String github) {
        this.github = new SimpleStringProperty(github);
    }
}

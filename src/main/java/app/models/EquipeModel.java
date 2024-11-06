package app.models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class EquipeModel {

    private SimpleIntegerProperty id;
    private SimpleStringProperty nome;
    private SimpleStringProperty github;

    public EquipeModel(int id, String name, String github){
        this.id = new SimpleIntegerProperty(id);
        this.nome = new SimpleStringProperty(name);
        this.github = new SimpleStringProperty(github);
    }

    public int getId() {   return id.get(); }

    public void setId(int id) {  this.id = new SimpleIntegerProperty(id); }

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

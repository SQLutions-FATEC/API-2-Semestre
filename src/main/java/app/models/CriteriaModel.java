package app.models;

import javafx.beans.property.SimpleStringProperty;

public class CriteriaModel {
    private SimpleStringProperty nome;
    private SimpleStringProperty descricao;

    public CriteriaModel(String nome, String descricao){
        this.nome = new SimpleStringProperty(nome);
        this.descricao = new SimpleStringProperty(descricao);
    }

    public String getNome() {
        return nome.get();
    }
    public void setNome(String nome) {
        this.nome = new SimpleStringProperty(nome);
    }

    public String getDescricao() {
        return descricao.get();
    }
    public void setDescricao(String descricao) {
        this.descricao = new SimpleStringProperty(descricao);
    }
}

package app.models;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

public class CriteriaModel {
    private final StringProperty nome;
    private final StringProperty descricao;
    private final BooleanProperty selected;

    public CriteriaModel(String nome, String descricao) {
        this.nome = new SimpleStringProperty(nome);
        this.descricao = new SimpleStringProperty(descricao);
        this.selected = new SimpleBooleanProperty(false);
    }

    public String getNome() {
        return nome.get();
    }
    public StringProperty nomeProperty() {
        return nome;
    }

    public String getDescricao() {
        return descricao.get();
    }
    public StringProperty descricaoProperty() {
        return descricao;
    }

    public boolean isSelected() {
        return selected.get();
    }
    public void setSelected(boolean selected) {
        this.selected.set(selected);
    }
    public BooleanProperty selectedProperty() {
        return selected;
    }
}

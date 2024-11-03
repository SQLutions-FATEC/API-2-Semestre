package app.models;

import javafx.beans.property.*;

import java.time.LocalDateTime;

public class CriteriaModel {
    private int id;
    private final StringProperty nome;
    private final StringProperty descricao;
    private final SimpleObjectProperty<LocalDateTime> deletedAt;
    private final BooleanProperty isDeleted;

    public CriteriaModel(int id, String nome, String descricao) {
        this.id = id;
        this.nome = new SimpleStringProperty(nome);
        this.descricao = new SimpleStringProperty(descricao);
        this.deletedAt = new SimpleObjectProperty<>(null);
        this.isDeleted = new SimpleBooleanProperty(false);
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome.get();
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt.get();
    }
    public BooleanProperty isDeletedProperty() {
        return isDeleted;
    }
    public void setDeletedAt(LocalDateTime dateTime) {
        deletedAt.set(dateTime);
        isDeleted.set(dateTime != null);
    }
    public void clearDeletedAt() {
        deletedAt.set(null);
        isDeleted.set(false);
    }
}

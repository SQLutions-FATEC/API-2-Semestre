package app.models;

import javafx.beans.property.*;

import java.time.LocalDateTime;

public class CriteriaModel {
    private int id;
    private final StringProperty nome;
    private final StringProperty descricao;
    private final SimpleObjectProperty<LocalDateTime> deletedAt;
    private final BooleanProperty isDeleted;

    public CriteriaModel(int id, String name, String description, LocalDateTime deletedAt) {
        this.id = id;
        this.nome = new SimpleStringProperty(name);
        this.descricao = new SimpleStringProperty(description);
        this.deletedAt = new SimpleObjectProperty<>(deletedAt);
        this.isDeleted = new SimpleBooleanProperty(false);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return nome.get();
    }

    public String getDescription() {
        return descricao.get();
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

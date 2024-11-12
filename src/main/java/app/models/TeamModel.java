package app.models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class TeamModel {
    private SimpleIntegerProperty id;
    private SimpleStringProperty name;
    private SimpleStringProperty github;
    private SimpleStringProperty deletedAt;

    public TeamModel(int id, String name, String github) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.github = new SimpleStringProperty(github);
    }

    public Integer getId() {
        return id.get();
    }

    public String getName() {
        return name.get();
    }
    public void setName(String name) {
        this.name = new SimpleStringProperty(name);
    }

    public String getGithub() {
        return github.get();
    }
}

package app.models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.Date;

public class ScoreModel {
    private SimpleIntegerProperty id;
    private SimpleIntegerProperty valor;
    private SimpleIntegerProperty sprint;
    private SimpleIntegerProperty equipe;
    private SimpleObjectProperty data;

    public ScoreModel(int id, int value, int sprintId, int teamId, Date date) {
        this.id = new SimpleIntegerProperty(id);
        this.valor = new SimpleIntegerProperty(value);
        this.sprint = new SimpleIntegerProperty(sprintId);
        this.equipe = new SimpleIntegerProperty(teamId);
        this.data = new SimpleObjectProperty<>(date);

    }

    public int getValue() { return valor.get(); }

    public Date getDate() { return (Date) data.get(); }
}

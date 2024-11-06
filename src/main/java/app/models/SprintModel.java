package app.models;

import javafx.beans.property.*;

import java.util.Date;

public class SprintModel {
    private SimpleIntegerProperty id;
    private SimpleStringProperty description;
    private static ObjectProperty<Date> startDate;
    private static ObjectProperty<Date> endDate;

    public SprintModel(int id, String descricao, Date dataInicio, Date dataFim) {
        this.id = new SimpleIntegerProperty(id);
        this.description = new SimpleStringProperty(descricao);
        this.startDate = new SimpleObjectProperty<>(dataInicio);
        this.endDate = new SimpleObjectProperty<>(dataFim);
    }

    public int getId() { return id.get(); }
    public void setId(int id) { this.id.set(id); }

    public String getDescription() { return description.get(); }

    public static Date getStartDate() { return startDate.get(); }

    public static Date getEndDate() { return endDate.get(); }
}

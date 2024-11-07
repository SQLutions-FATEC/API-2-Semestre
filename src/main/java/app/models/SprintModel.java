package app.models;

import javafx.beans.property.*;

import java.util.Date;

public class SprintModel {
    private SimpleIntegerProperty id;
    private SimpleStringProperty description;
    private ObjectProperty<Date> startDate;
    private ObjectProperty<Date> endDate;

    public SprintModel(int id, String description, Date startDate, Date endDate) {
        this.id = new SimpleIntegerProperty(id);
        this.description = new SimpleStringProperty(description);
        this.startDate = new SimpleObjectProperty<>(startDate);
        this.endDate = new SimpleObjectProperty<>(endDate);
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public static Date getStartDate() {
        return startDate.get();
    }

    public void setStartDate(Date startDate) {
        this.startDate.set(startDate);
    }

    public static getEndDate() {
        return endDate.get();
    }

    public void setEndDate(Date endDate) {
        this.endDate.set(endDate);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public ObjectProperty<Date> startDateProperty() {
        return startDate;
    }

    public ObjectProperty<Date> endDateProperty() {
        return endDate;
    }
}

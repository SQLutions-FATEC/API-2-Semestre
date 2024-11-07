package app.models;

import javafx.beans.property.*;

import java.util.Date;

public class SprintModel {
    private SimpleIntegerProperty id;
    private SimpleStringProperty description;
    private static ObjectProperty<Date> startDate = new SimpleObjectProperty<>();
    private static ObjectProperty<Date> endDate = new SimpleObjectProperty<>();

    public SprintModel(int id, String description, Date startDate, Date endDate) {
        this.id = new SimpleIntegerProperty(id);
        this.description = new SimpleStringProperty(description);
        SprintModel.startDate.set(startDate);
        SprintModel.endDate.set(endDate);
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

    public static void setStartDate(Date startDate) {
        SprintModel.startDate.set(startDate);
    }

    public static Date getEndDate() {
        return endDate.get();
    }

    public static void setEndDate(Date endDate) {
        SprintModel.endDate.set(endDate);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public static ObjectProperty<Date> startDateProperty() {
        return startDate;
    }

    public static ObjectProperty<Date> endDateProperty() {
        return endDate;
    }
}

package app.models;

import javafx.beans.property.SimpleIntegerProperty;

public class PeriodModel {
    private SimpleIntegerProperty id;
    private SimpleIntegerProperty semestre;
    private SimpleIntegerProperty ano;

    public PeriodModel(int id, int semester, int year) {
        this.id = new SimpleIntegerProperty(id);
        this.semestre = new SimpleIntegerProperty(semester);
        this.ano = new SimpleIntegerProperty(year);
    }

    public int getId() { return id.get(); }

    public int getSemester() { return semestre.get(); }

    public int getYear() { return ano.get(); }
}

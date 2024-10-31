package app.models;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class AverageGradeModel {

    private SimpleIntegerProperty idAluno;
    private SimpleIntegerProperty idCriterio;
    private SimpleDoubleProperty mediaNota;

    public AverageGradeModel(int idAluno, int idCriterio, double mediaNota) {
        this.idAluno = new SimpleIntegerProperty(idAluno);
        this.idCriterio = new SimpleIntegerProperty(idCriterio);
        this.mediaNota = new SimpleDoubleProperty(mediaNota);
    }

    public AverageGradeModel(float id, int equipe, String nome, double valor, int semestreResult, int anoResult, String descricao) {
    }

    public int getIdAluno() {
        return idAluno.get();
    }

    public int getIdCriterio() {
        return idCriterio.get();
    }

    public double getMediaNota() {
        return mediaNota.get();
    }
}
package app.models;

import javafx.beans.property.*;

public class GradeModel {

    private SimpleIntegerProperty valor;
    private SimpleIntegerProperty avaliador;
    private SimpleIntegerProperty avaliado;
    private SimpleIntegerProperty criterio;
    private SimpleIntegerProperty periodo;
    private SimpleIntegerProperty sprint;

    public GradeModel(Integer valor, Integer avaliador, Integer avaliado, Integer criterio, Integer periodo, Integer sprint) {

        this.valor = new SimpleIntegerProperty(valor);
        this.avaliador = new SimpleIntegerProperty(avaliador);
        this.avaliado = new SimpleIntegerProperty(avaliado);
        this.criterio = new SimpleIntegerProperty(criterio);
        this.periodo = new SimpleIntegerProperty(criterio);
        this.sprint = new SimpleIntegerProperty(sprint);

    }

//    public int getId() {
//        return id;
//    }

    public Integer getValor() {
        return valor.get();
    }

    public void setValor(Integer valor) {
        this.valor = new SimpleIntegerProperty(valor);
    }

    public Integer getAvaliador() {
        return avaliador.get();
    }

    public void setAvaliador(Integer avaliador) {
        this.avaliador = new SimpleIntegerProperty(avaliador);
    }

    public Integer getAvaliado() {
        return avaliado.get();
    }

    public void setAvaliado(Integer avaliado) {
        this.avaliado = new SimpleIntegerProperty(avaliado);
    }

    public Integer getCriterio() {
        return criterio.get();
    }

    public void setCriterio(Integer criterio) {
        this.criterio = new SimpleIntegerProperty(criterio);
    }

    public Integer getPeriodo() {
        return periodo.get();
    }

    public void setPeriodo(Integer periodo) {
        this.periodo = new SimpleIntegerProperty(periodo);
    }

    public Integer getSprint() {
        return sprint.get();
    }

    public void setSprint(Integer sprint) {
        this.sprint = new SimpleIntegerProperty(sprint);
    }
}

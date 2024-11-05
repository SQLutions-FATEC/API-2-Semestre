package app.models;

import javafx.beans.property.*;

import java.util.Date;

public class SprintModel {
    private SimpleIntegerProperty id;
    private SimpleStringProperty descricao;
    private static ObjectProperty<Date> dataInicio;
    private static ObjectProperty<Date> dataFim;

    public SprintModel(int id, String descricao, Date dataInicio, Date dataFim) {
        this.id = new SimpleIntegerProperty(id);
        this.descricao = new SimpleStringProperty(descricao);
        this.dataInicio = new SimpleObjectProperty<>(dataInicio);
        this.dataFim = new SimpleObjectProperty<>(dataFim);
    }

    public int getId() { return id.get(); }
    public void setId(int id) { this.id.set(id); }

    public String getDescricao() { return descricao.get(); }
    public void setDescricao(String descricao) { this.descricao.set(descricao); }

    public static Date getDataInicio() { return dataInicio.get(); }
    public void setDataInicio(Date dataInicio) { this.dataInicio.set(dataInicio); }

    public static Date getDataFim() { return dataFim.get(); }
    public void setDataFim(Date dataFim) { this.dataFim.set(dataFim); }

    public ObjectProperty<Date> dataInicioProperty() { return dataInicio; }
    public ObjectProperty<Date> dataFimProperty() { return dataFim; }
}

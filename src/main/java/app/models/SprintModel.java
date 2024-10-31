package app.models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SprintModel {

    private SimpleIntegerProperty id;
    private SimpleStringProperty descricao;
    private SimpleDateFormat dataInicio;
    private SimpleDateFormat dataFim;


    public SprintModel(int id, String descricao, Date dataInicio, Date dataFim){
        this.id = new SimpleIntegerProperty(id);
        this.descricao = new SimpleStringProperty(descricao);
        this.dataInicio = new SimpleDateFormat(dataInicio);
        this.dataFim = new SimpleDateFormat(dataFim);
    }

    public int getId() { return id.get(); }

    public void setId(int id) { this.id = new SimpleIntegerProperty(id); }

    public String getDescricao() { return descricao.get(); }

    public void setDescricao(String descricao) { this.descricao = new SimpleStringProperty(descricao); }

    public Date getDataInicio() { return dataInicio.get(); }

    public void setDataInicio(Date dataInicio) { this.dataInicio = new SimpleDateFormat(dataInicio);
    }

    public Date getDataFim() { return dataFim.get(); }

    public void setDataFim(Date dataFim) { this.dataFim = new SimpleDateFormat(dataFim);
    }
}

package app.models;


import javafx.beans.property.SimpleStringProperty;

public class DataModel {

    private SimpleStringProperty ano;
    private SimpleStringProperty semestre;
    private SimpleStringProperty descricao;
    private SimpleStringProperty data_inicio;
    private SimpleStringProperty data_fim;

    public DataModel(String ano, String semestre, String descricao, String data_inicio, String data_fim){
        this.ano = new SimpleStringProperty(ano);
        this.semestre = new SimpleStringProperty(semestre);
        this.descricao = new SimpleStringProperty(descricao);
        this.data_inicio = new SimpleStringProperty(data_inicio);
        this.data_fim = new SimpleStringProperty(data_fim);
    }

    public String getAno(){
        return ano.get();
    }

    public void setAno(String ano){
        this.ano = new SimpleStringProperty(ano);
    }

    public String getSemestre(){
        return semestre.get();
    }

    public void setSemestre(String semestre){
        this.semestre = new SimpleStringProperty(semestre);
    }

    public String getDescricao(){
        return descricao.get();
    }

    public void setDescricao(String descricao){
        this.descricao = new SimpleStringProperty(descricao);
    }

    public String getData_Inicio(){
        return data_inicio.get();
    }

    public void setData_inicio(String data_inicio){
        this.data_inicio = new SimpleStringProperty(data_inicio);
    }

    public String getData_fim(){
        return data_fim.get();
    }
}

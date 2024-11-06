package app.models;

public class AvaliacaoModel {
    private String nome;
    private Integer notas = 0;

    public AvaliacaoModel(String nome, Integer notas) {
        this.nome = nome;
        this.notas = notas;
    }

    public String getNome() {
        return nome;
    }

    public void setNotas(Integer notas) {
        this.notas = notas;
    }

    public Integer getNotas() {
        return notas;
    }

}

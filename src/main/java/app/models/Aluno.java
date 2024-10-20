package app.models;

public class Aluno {
    private String nome;
    private int proatividade;
    private int autonomia;
    private int colaboracao;
    private int entrega;

    public Aluno(String nome, int proatividade, int autonomia, int colaboracao, int entrega) {
        this.nome = nome;
        this.proatividade = proatividade;
        this.autonomia = autonomia;
        this.colaboracao = colaboracao;
        this.entrega = entrega;
    }

    public String getNome() {
        return nome;
    }

    public int getProatividade() {
        return proatividade;
    }

    public void setProatividade(int proatividade) {
        this.proatividade = proatividade;
    }

    public int getAutonomia() {
        return autonomia;
    }

    public void setAutonomia(int autonomia) {
        this.autonomia = autonomia;
    }

    public int getColaboracao() {
        return colaboracao;
    }

    public void setColaboracao(int colaboracao) {
        this.colaboracao = colaboracao;
    }

    public int getEntrega() {
        return entrega;
    }

    public void setEntrega(int entrega) {
        this.entrega = entrega;
    }
}

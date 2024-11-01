package app.models;

import java.util.Map;

public class Aluno {
    private String nome;
    private Map<String, Integer> notas;

    public Aluno(String nome, Map<String, Integer> notas) {
        this.nome = nome;
        this.notas = notas;
    }

    public String getNome() {
        return nome;
    }

    public void setAverage(String criteria, Integer nota) {
        notas.put(criteria, nota);
    }

    public Integer getAverage(String criteria) {
        return notas.getOrDefault(criteria, 0);
    }

}

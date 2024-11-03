package app.models;

import java.util.HashMap;
import java.util.Map;

public class Aluno {
    private String nome;
    private Map<String, Integer> notas;

    public Aluno(String nome, Map<String, Integer> notas) {
        this.nome = nome;
        this.notas = new HashMap<>();
    }

    public String getNome() {
        return nome;
    }

    public void setNotas(String criteria, Integer nota) {
        notas.put(criteria, nota);
    }

    public Integer getNotas(String criteria) {
        return notas.getOrDefault(criteria, 0);
    }

}

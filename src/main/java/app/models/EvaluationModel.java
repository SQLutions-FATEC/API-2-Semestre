package app.models;

import java.util.HashMap;
import java.util.Map;

public class EvaluationModel {
    private String nome;
    private Map<String, Integer> notas;

    public EvaluationModel(String nome, Integer notas) {
        this.nome = nome;
        this.notas = new HashMap<>();
    }

    public String getNome() {
        return nome;
    }

    public void setNotas(String criterio , Integer nota) {
        notas.put(criterio, nota);
    }

    public Map<String, Integer> getNotas() {
        return notas;
    }

    public Integer getNota(String criterio) {
        return notas.getOrDefault(criterio, 0);
    }
}

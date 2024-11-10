package app.models;

import java.util.HashMap;
import java.util.Map;

public class AverageGradeModel {
    private String name;
    private Map<String, Double> averages;

    public AverageGradeModel(String nome) {
        this.name = nome;
        this.averages = new HashMap<>();
    }

    public String getNome() {
        return name;
    }

    public void setAverage(String criteria, Double average) {
        averages.put(criteria, average);
    }

    public Double getAverage(String criteria) {
        return averages.getOrDefault(criteria, 0.0);
    }
}

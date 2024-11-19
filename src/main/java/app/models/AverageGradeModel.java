package app.models;

import java.util.HashMap;
import java.util.Map;

public class AverageGradeModel {
    private String name;
    private Map<String, Integer> averages;

    public AverageGradeModel(String nome) {
        this.name = nome;
        this.averages = new HashMap<>();
    }

    public String getNome() {
        return name;
    }

    public void setAverage(String criteria, Integer average) {
        averages.put(criteria, average);
    }

    public Integer getAverage(String criteria) {
        return averages.getOrDefault(criteria, 0);
    }
}

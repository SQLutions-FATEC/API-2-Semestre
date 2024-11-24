package app.models;

import java.util.HashMap;
import java.util.Map;

public class AverageGradeModel {
    private String name;
    private Map<String, Integer> averages;

    public AverageGradeModel(String name) {
        this.name = name;
        this.averages = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setAverage(String criteria, Integer average) {
        averages.put(criteria, average);
    }

    public Integer getAverage(String criteria) {
        return averages.getOrDefault(criteria, 0);
    }

    public Map<String, Integer> getAverages() {
        return averages;
    };
}

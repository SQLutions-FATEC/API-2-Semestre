package app.models;
import app.models.EvaluationModel;

public class EvaluationModel {
    private String evaluatorName;       // Nome do avaliador
    private int evaluatedStudentId;     // ID do avaliado
    private String criteria;            // Critério de avaliação
    private String sprintDescription;   // Descrição do sprint

    public EvaluationModel(String evaluatorName, int evaluatedStudentId, String criteria, String sprintDescription) {
        this.evaluatorName = evaluatorName;
        this.evaluatedStudentId = evaluatedStudentId;
        this.criteria = criteria;
        this.sprintDescription = sprintDescription;
    }

    // Getters e setters para os campos
    public String getEvaluatorName() {
        return evaluatorName;
    }

    public void setEvaluatorName(String evaluatorName) {
        this.evaluatorName = evaluatorName;
    }

    public int getEvaluatedStudentId() {
        return evaluatedStudentId;
    }

    public void setEvaluatedStudentId(int evaluatedStudentId) {
        this.evaluatedStudentId = evaluatedStudentId;
    }

    public String getCriteria() {
        return criteria;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }

    public String getSprintDescription() {
        return sprintDescription;
    }

    public void setSprintDescription(String sprintDescription) {
        this.sprintDescription = sprintDescription;
    }
}

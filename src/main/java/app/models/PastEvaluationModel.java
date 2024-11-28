package app.models;

public class PastEvaluationModel {
        private String evaluatorName;
        private int evaluatedStudentId;
        private String criteria;
        private String sprintDescription;
        private int nota;

        public PastEvaluationModel(String evaluatorName, int evaluatedStudentId, String criteria, String sprintDescription, int nota) {
            this.evaluatorName = evaluatorName;
            this.evaluatedStudentId = evaluatedStudentId;
            this.criteria = criteria;
            this.sprintDescription = sprintDescription;
            this.nota = nota;
        }

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

    public int getNota() {
        return nota;
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

        public void setNota(int nota){
            this.nota = nota;
        }
}

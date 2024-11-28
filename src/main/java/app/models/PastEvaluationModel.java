package app.models;

public class PastEvaluationModel {
        private String evaluatorStudentName;
        private String evaluatedStudentName;
        private String criteriaName;
        private String sprintDescription;
        private int nota;

        public PastEvaluationModel(String evaluatorStudentName, String evaluatedStudentName, String criteriaName, String sprintDescription, int nota) {
            this.evaluatorStudentName = evaluatorStudentName;
            this.evaluatedStudentName = evaluatedStudentName;
            this.criteriaName = criteriaName;
            this.sprintDescription = sprintDescription;
            this.nota = nota;
        }

        public String getEvaluatorStudentName() {
            return evaluatorStudentName;
        }
        public void setEvaluatorStudentName(String evaluatorName) {
            this.evaluatorStudentName = evaluatorName;
        }

        public String getEvaluatedStudentName() {
            return evaluatedStudentName;
        }
        public void setEvaluatedStudentName(String evaluatedStudentName) {
            this.evaluatedStudentName = evaluatedStudentName;
        }

        public String getCriteriaName() {
            return criteriaName;
        }
        public void setCriteriaName(String criteria) {
            this.criteriaName = criteria;
        }

        public int getNota() {
            return nota;
        }
        public void setNota(int nota){
            this.nota = nota;
        }

        public String getSprintDescription() {
            return sprintDescription;
        }
        public void setSprintDescription(String sprintDescription) {
            this.sprintDescription = sprintDescription;
        }
}

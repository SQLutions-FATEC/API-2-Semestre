package app.models;

public class PastEvaluationModel {
        private String evaluatorName;
        private int evaluatedStudentId;
        private String criteria;
        private String sprintDescription;

        public PastEvaluationModel(String evaluatorName, int evaluatedStudentId, String criteria, String sprintDescription) {
            this.evaluatorName = evaluatorName;
            this.evaluatedStudentId = evaluatedStudentId;
            this.criteria = criteria;
            this.sprintDescription = sprintDescription;
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

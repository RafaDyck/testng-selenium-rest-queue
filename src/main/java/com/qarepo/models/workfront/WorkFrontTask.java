package com.qarepo.models.workfront;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, getterVisibility = JsonAutoDetect.Visibility.NONE, setterVisibility = JsonAutoDetect.Visibility.NONE)
public class WorkFrontTask {

    @JsonProperty(value = "ID")
    private String ID;

    @JsonProperty("name")
    private String name;

    @JsonProperty("objCode")
    private String objCode;

    @JsonProperty("percentComplete")
    private String percentComplete;

    @JsonProperty("plannedCompletionDate")
    private String plannedCompletionDate;

    @JsonProperty("plannedStartDate")
    private String plannedStartDate;

    @JsonProperty("priority")
    private String priority;

    @JsonProperty("progressStatus")
    private String progressStatus;

    @JsonProperty("projectedCompletionDate")
    private String projectedCompletionDate;

    @JsonProperty("projectedStartDate")
    private String projectedStartDate;

    @JsonProperty("status")
    private String status;

    @JsonProperty("taskNumber")
    private String taskNumber;

    @JsonProperty("wbs")
    private String wbs;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getObjCode() {
        return objCode;
    }

    public void setObjCode(String objCode) {
        this.objCode = objCode;
    }

    public String getPercentComplete() {
        return percentComplete;
    }

    public void setPercentComplete(String percentComplete) {
        this.percentComplete = percentComplete;
    }

    public String getPlannedCompletionDate() {
        return plannedCompletionDate;
    }

    public void setPlannedCompletionDate(String plannedCompletionDate) {
        this.plannedCompletionDate = plannedCompletionDate;
    }

    public String getPlannedStartDate() {
        return plannedStartDate;
    }

    public void setPlannedStartDate(String plannedStartDate) {
        this.plannedStartDate = plannedStartDate;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getProgressStatus() {
        return progressStatus;
    }

    public void setProgressStatus(String progressStatus) {
        this.progressStatus = progressStatus;
    }

    public String getProjectedCompletionDate() {
        return projectedCompletionDate;
    }

    public void setProjectedCompletionDate(String projectCompletionDate) {
        this.projectedCompletionDate = projectCompletionDate;
    }

    public String getProjectedStartDate() {
        return projectedStartDate;
    }

    public void setProjectedStartDate(String projectedStartDate) {
        this.projectedStartDate = projectedStartDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTaskNumber() {
        return taskNumber;
    }

    public void setTaskNumber(String taskNumber) {
        this.taskNumber = taskNumber;
    }

    public String getWbs() {
        return wbs;
    }

    public void setWbs(String wbs) {
        this.wbs = wbs;
    }

    @Override
    public String toString() {
        return "WorkFrontTask{" +
                "ID='" + ID + '\'' +
                ", name='" + name + '\'' +
                ", objCode='" + objCode + '\'' +
                ", percentComplete='" + percentComplete + '\'' +
                ", plannedCompletionData='" + plannedCompletionDate + '\'' +
                ", plannedStartDate='" + plannedStartDate + '\'' +
                ", priority='" + priority + '\'' +
                ", progressStatus='" + progressStatus + '\'' +
                ", projectCompletionDate='" + projectedCompletionDate + '\'' +
                ", projectedStartDate='" + projectedStartDate + '\'' +
                ", status='" + status + '\'' +
                ", taskNumber='" + taskNumber + '\'' +
                ", wbs='" + wbs + '\'' +
                '}';
    }
}

package com.qarepo.models.workfront;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(value = "id")
public class WorkFrontProject {

    @JsonProperty("ID")
    private String ID;

    @JsonProperty("name")
    private String name;

    @JsonProperty("objCode")
    private String objCode;

    @JsonProperty("company")
    @JsonAlias("companyID")
    private Company company;

    @JsonProperty("tasks")
    private List<WorkFrontTask> tasks;

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

    public List<WorkFrontTask> getTasks() {
        return tasks;
    }

    public void setTasks(List<WorkFrontTask> tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        return "WorkFrontProject{" +
                "ID='" + ID + '\'' +
                ", name='" + name + '\'' +
                ", objCode='" + objCode + '\'' +
                ", company=" + company +
                ", tasks=" + tasks +
                '}';
    }
}

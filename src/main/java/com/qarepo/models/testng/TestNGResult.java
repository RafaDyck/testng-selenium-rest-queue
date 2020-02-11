package com.qarepo.models.testng;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name="testng_result")
public class TestNGResult {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    private String description;

    private String exceptionMessage;

    private long runTime;

    @Column(name = "parameters", length = 1000)
    private String parameters;

    @ManyToOne
    @JoinColumn(name = "suite_id")
    @JsonIgnore
    private TestNGSuiteResult testNGSuiteResult;

    private String status;

    public TestNGResult() {
    }

    public TestNGResult(String name, String description, String exceptionMessage, long runTime, String parameters) {
        this.name = name;
        this.description = description;
        this.exceptionMessage = exceptionMessage;
        this.runTime = runTime;
        this.parameters = parameters;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    public long getRunTime() {
        return runTime;
    }

    public void setRunTime(long runTime) {
        this.runTime = runTime;
    }

    public String getParameters() {
        return parameters;
    }

    public void setParameters(String parameters) {
        this.parameters = parameters;
    }

    public TestNGSuiteResult getTestNGSuiteResult() {
        return testNGSuiteResult;
    }

    public void setTestNGSuiteResult(TestNGSuiteResult testNGSuiteResult) {
        this.testNGSuiteResult = testNGSuiteResult;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "TestNGResult{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", message='" + exceptionMessage + '\'' +
                ", runTime=" + runTime +
                ", parameters=" + parameters +
                ", testNGSuiteResult=" + testNGSuiteResult +
                ", status='" + status + '\'' +
                '}';
    }
}

package com.qarepo.models.testng;

import javax.persistence.*;
import java.util.List;

@Entity
public class TestNGSuiteResult {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;

    private long formID;

    @OneToMany(cascade = CascadeType.ALL)
    private List<TestNGResult> allTests;

    public TestNGSuiteResult() {
    }

    public TestNGSuiteResult(String name, long formID, List<TestNGResult> allTests) {
        this.name = name;
        this.formID = formID;
        this.allTests = allTests;
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

    public long getFormID() {
        return formID;
    }

    public void setFormID(long formID) {
        this.formID = formID;
    }

    public List<TestNGResult> getAllTests() {
        return allTests;
    }

    public void setAllTests(List<TestNGResult> allTests) {
        this.allTests = allTests;
    }
}

package com.qarepo.models.testng;

import org.testng.xml.XmlClass;

import java.util.List;
import java.util.Map;

public class TestNGSuite {

    private String name;
    private Map<String, String> parameters;
    private List<String> listeners;
    private List<XmlClass> testClassList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }

    public List<String> getListeners() {
        return listeners;
    }

    public void setListeners(List<String> listeners) {
        this.listeners = listeners;
    }

    public List<XmlClass> getTestClassList() {
        return testClassList;
    }

    public void setTestClassList(List<XmlClass> testClassList) {
        this.testClassList = testClassList;
    }

    @Override
    public String toString() {
        return "TestNGSuite{" +
                "name='" + name + '\'' +
                ", parameters=" + parameters +
                ", listeners=" + listeners +
                ", testClassList=" + testClassList +
                '}';
    }

}

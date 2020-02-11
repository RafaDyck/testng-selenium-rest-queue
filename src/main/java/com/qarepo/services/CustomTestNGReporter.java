package com.qarepo.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.*;
import org.testng.xml.XmlSuite;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class CustomTestNGReporter implements IReporter {

    @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
        TestNGService testNGService = new TestNGService();
        ObjectMapper mapper = new ObjectMapper();
        List<JsonNode> nodes = new ArrayList<>();
        for (ISuite suite : suites) {
            nodes.add(mapper.convertValue(testNGService.convertSuiteToJson(suite), JsonNode.class));
        }
        try (FileWriter fileWriter = new FileWriter("./report.json")) {
            for (JsonNode node : nodes) {
                fileWriter.write(mapper.writeValueAsString(node));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

package com.qarepo.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qarepo.models.testng.TestNGResult;
import com.qarepo.models.testng.TestNGSuite;
import com.qarepo.models.testng.TestNGSuiteResult;
import com.qarepo.repository.TestNGResultRepository;
import com.qarepo.repository.TestNGSuiteResultsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.testng.ISuite;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class TestNGService {

    private TestNGSuite testNGSuite;

    @Autowired
    private TestNGSuiteResultsRepository testNGSuiteResultsRepository;

    @Autowired
    private TestNGResultRepository testNGResultRepository;

    public TestNGSuite getTestNGSuite() {
        return this.testNGSuite;
    }

    public void setTestNGSuite(TestNGSuite testNGSuite) {
        this.testNGSuite = testNGSuite;
    }

    public boolean runSuiteAndCheckForErrors() {
        TestNG testng = new TestNG();
        List<XmlSuite> suites = new ArrayList<>();
        suites.add(buildTestNGSuite());
        testng.setXmlSuites(suites);
        testng.setUseDefaultListeners(false);
        testng.run();
        return testng.hasFailure();
    }

    private XmlSuite buildTestNGSuite() {
        XmlSuite suite = new XmlSuite();
        suite.setName(testNGSuite.getName());
        suite.setParallel(XmlSuite.ParallelMode.METHODS);
        suite.setThreadCount(3);
        for (String listener : testNGSuite.getListeners()) {
            suite.addListener(listener);
        }
        XmlTest test = new XmlTest(suite);
        test.setParameters(testNGSuite.getParameters());
        test.setXmlClasses(testNGSuite.getTestClassList());
        return suite;
    }

    public void createBannerHTMLSuite(Map<String, String> parameters) {
        this.testNGSuite = new TestNGSuite();
        testNGSuite.setName("Form Tests");
        List<String> listeners = new ArrayList<>();
        listeners.add("com.qarepo.tests.TestListener");
        listeners.add("com.qarepo.services.CustomTestNGReporter");
        testNGSuite.setListeners(listeners);
        testNGSuite.setParameters(parameters);
        List<XmlClass> testClassList = new ArrayList<>();
        testClassList.add(new XmlClass("com.qarepo.tests.FormTests"));
        testNGSuite.setTestClassList(testClassList);
    }

    public TestNGSuiteResult exportResults() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File resultFile = new File("./report.json");
        TestNGSuiteResult testNGSuiteResult = mapper.readValue(resultFile, TestNGSuiteResult.class);
        JsonNode node = mapper.readTree(resultFile);
        Iterator<JsonNode> resultNodes = node.get("allTests").elements();
        List<TestNGResult> allResults = new ArrayList<>();
        while (resultNodes.hasNext()) {
            TestNGResult result = mapper.convertValue(resultNodes.next(), TestNGResult.class);
            result.setTestNGSuiteResult(testNGSuiteResult);
            allResults.add(result);
        }
        testNGSuiteResult.setAllTests(allResults);
        testNGSuiteResultsRepository.save(testNGSuiteResult);
        resultFile.delete();
        return testNGSuiteResult;
    }

    public JsonNode convertSuiteToJson(ISuite suite) {
        ObjectMapper mapper = new ObjectMapper();
        List<TestNGResult> allTests = new ArrayList<>();
        AtomicReference<String> bannerID = new AtomicReference<>("");
        suite.getResults().entrySet().forEach(e -> {
            ITestContext context = e.getValue().getTestContext();
            bannerID.set(context.getCurrentXmlTest().getAllParameters().get("id"));
            allTests.addAll(getListOfTestResults(context.getFailedTests().getAllResults()));
            allTests.addAll(getListOfTestResults(context.getPassedTests().getAllResults()));
            allTests.addAll(getListOfTestResults(context.getSkippedTests().getAllResults()));
        });
        TestNGSuiteResult testNGSuiteResult = new TestNGSuiteResult();
        testNGSuiteResult.setName(suite.getName());
        testNGSuiteResult.setFormID(Long.valueOf(bannerID.get()));
        testNGSuiteResult.setAllTests(allTests);
        return mapper.convertValue(testNGSuiteResult, JsonNode.class);
    }

    private List<TestNGResult> getListOfTestResults(Set<ITestResult> results) {
        List<TestNGResult> testResults = new ArrayList<>();
        for (ITestResult result : results) {
            TestNGResult testResult = new TestNGResult();
            testResult.setName(result.getName());
            testResult.setDescription(result.getMethod().getDescription());
            testResult.setRunTime(result.getEndMillis() - result.getStartMillis());
            testResult.setParameters(Arrays.deepToString(result.getParameters()));
            testResult.setStatus(getTestStatus(result.getStatus()));
            if (result.getThrowable() != null) {
                testResult.setExceptionMessage(result.getThrowable().getMessage());
            }
            testResults.add(testResult);
        }
        return testResults;
    }

    private String getTestStatus(int statusCode) {
        String status = "UNABLE TO RETRIVE STATUS";
        switch(statusCode) {
            case 1:
                status = "SUCCESS";
                break;
            case 2:
                status = "FAILURE";
                break;
            case 3:
                status = "SKIPPED";
                break;
        }
        return status;
    }

    public TestNGSuiteResult getSuiteResultById(long id) {
        return testNGSuiteResultsRepository.findById(id);
    }

    public TestNGSuiteResult getSuiteResultByFormID(long id) {
        return testNGSuiteResultsRepository.findByFormID(id);
    }
}

package com.qarepo.tests;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataSupplier {
    private static Logger logger = LogManager.getLogger(DataSupplier.class);

    @DataProvider(name = "searchTerms")
    public Object[][] getMultipleSearchTerms(ITestContext context) {
        Map<String, String> parameters = context.getCurrentXmlTest().getAllParameters();
        List<String> allSearchValues = new ArrayList<>();
        allSearchValues.add("youtube");
        allSearchValues.add("google drive");
        allSearchValues.add("google cloud");
        Object[][] testParams = new Object[allSearchValues.size()][2];
        for (int i = 0; i < allSearchValues.size(); i++) {
            testParams[i][0] = parameters.get("browser");
            testParams[i][1] = allSearchValues.get(i);
        }
        return testParams;
    }

    @DataProvider(name = "searchTermsExternalSource")
    public Object[][] multipleSearchTermsFromExternalSource() {
        List<Map<String, String>> testDataFromSource = csvToMap("./src/main/resources/test-data/search-terms.csv");
        Object[][] testDataToTest = new Object[testDataFromSource.size()][];
        for (int i = 0; i < testDataFromSource.size(); i++) {
            List<String> keySet = new ArrayList<>(testDataFromSource.get(i).keySet());
            for (int j = 0; j < keySet.size(); j++) {
                testDataToTest[i] = new String[]{testDataFromSource.get(i).get(keySet.get(j))};
            }
        }
        return testDataToTest;
    }

    public List<Map<String, String>> csvToMap(String fileName) {
        List<Map<String, String>> testDataList = new ArrayList<>();
        try (CSVParser csvParser = new CSVParser(new BufferedReader(new FileReader(new File(fileName))), CSVFormat.DEFAULT)) {
            List<CSVRecord> list = csvParser.getRecords();
            for (int i = 1; i < list.size(); i++) {
                for (int j = 0; j < list.get(i).size(); j++) {
                    Map<String, String> tempMap = new HashMap<>();
                    tempMap.put(list.get(0).get(j), list.get(i).get(j));
                    testDataList.add(tempMap);
                }
            }
        } catch (IOException e) {
            logger.log(Level.ERROR, "File error " + e.toString());
        }
        return testDataList;
    }
}

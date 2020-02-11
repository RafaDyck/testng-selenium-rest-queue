package com.qarepo.services.webactions;

import com.qarepo.driver.WebDriverThreadManager;
import com.qarepo.pageobjects.GoogleResultElements;
import com.qarepo.pageobjects.GoogleSearchElements;
import com.qarepo.models.GoogleSearchResult;
import com.qarepo.driver.WebDriverWaits;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GoogleActions {
    private static Logger logger = LogManager.getLogger(GoogleActions.class);
    private String driverHash = "[WebDriver Hash: " + WebDriverThreadManager.getDriver().hashCode() + "] ";

    private void type_SearchTerm(String searchTerm) {
        logger.info(driverHash + "Type '" + searchTerm + "' in search box");
        WebDriverWaits.findElementWithWait(GoogleSearchElements.textBox_Search()).sendKeys(searchTerm);
    }

    private void click_Search() {
        logger.info(driverHash + "Click Google Search");
        WebDriverWaits.clickElementWithWait(GoogleSearchElements.button_GoogleSearch());
    }

    public void search(String searchTerm) {
        type_SearchTerm(searchTerm);
        click_Search();
    }

    private List<String> getList_Title() {
        List<String> titles = WebDriverWaits.findElementsWithWait(GoogleResultElements.text_SearchResultTitle())
                .stream().map(WebElement::getText).collect(Collectors.toList());
        logger.info(driverHash + "Get list of search result titles. Titles[" + titles + "]");
        return titles;
    }

    private List<String> getList_URL() {
        List<String> URLs = WebDriverWaits.findElementsWithWait(GoogleResultElements.link_SearchResultURL())
                .stream().map(WebElement::getText).collect(Collectors.toList());
        logger.info(driverHash + "Get list of search result URLs. URLs[" + URLs + "]");
        return URLs;
    }

    private List<String> getList_Description() {
        List<String> descriptions = WebDriverWaits.findElementsWithWait(GoogleResultElements.text_SearchResultDescription())
                .stream().map(WebElement::getText).collect(Collectors.toList());
        logger.info(driverHash + "Get list of search result Descriptions. Descriptions[" + descriptions + "]");
        return descriptions;
    }

    public List<GoogleSearchResult> getList_GoogleSearchResults() {
        List<GoogleSearchResult> results = new ArrayList<>();
        List<String> titles = getList_Title();
        List<String> URLs = getList_URL();
        List<String> descriptions = getList_Description();
        if (titles.size() == URLs.size() && titles.size() == descriptions.size()) {
            for (int i = 0; i < titles.size(); i++) {
                GoogleSearchResult result = new GoogleSearchResult(titles.get(i), URLs.get(i), descriptions.get(i));
                results.add(result);
            }
        }
        logger.info(driverHash + "Get list of google search results. GoogleSearchResults[" + results + "]");
        return results;
    }

    public void click_NextPage() {
        logger.info(driverHash + "Click next search results page.");
        WebDriverWaits.clickElementWithWait(GoogleResultElements.button_NextPage());
    }
}

package com.qarepo.driver;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

class WebDriverFactory {
    private static final Logger logger = LogManager.getLogger(WebDriverFactory.class);

    static WebDriver createDriverInstance(final String browser) {
        ChromeDriver driver = null;
        if (browser.equalsIgnoreCase("Google Chrome Headless")) {
            try {
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                options.setHeadless(true);
                options.addArguments("--disable-extensions");
                ChromeDriverService driverService = ChromeDriverService.createDefaultService();
                driver = new ChromeDriver(driverService, options);
                Map<String, Object> commandParams = new HashMap<>();
                commandParams.put("cmd", "Page.setDownloadBehavior");
                Map<String, String> params = new HashMap<>();
                params.put("behavior", "allow");
                params.put("downloadPath", "");
                commandParams.put("params", params);
                ObjectMapper objectMapper = new ObjectMapper();
                HttpClient httpClient = HttpClientBuilder.create().build();
                String command = objectMapper.writeValueAsString(commandParams);
                String u = driverService.getUrl().toString() + "/session/" + driver.getSessionId() + "/chromium/send_command";
                HttpPost request = new HttpPost(u);
                request.addHeader("content-type", "application/json");
                request.setEntity(new StringEntity(command));
                httpClient.execute(request);
                driver.manage().window().maximize();
                driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
                return driver;
            } catch (IOException e) {
                StringWriter sw = new StringWriter();
                e.printStackTrace(new PrintWriter(sw));
                logger.log(Level.ERROR, sw.toString());
            }
        } else if (browser.equalsIgnoreCase("Google Chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            return driver;
        }
        return driver;
    }
}
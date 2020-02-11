package com.qarepo.api;

import com.qarepo.models.testng.TestNGSuiteResult;
import com.qarepo.services.TestNGService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/results", produces = "application/json")
public class TestNGResultsController {

    @Autowired
    private TestNGService testNGService;

    @GetMapping(path = "/{id}")
    public TestNGSuiteResult getBannerSuite(@PathVariable long id) {
        return testNGService.getSuiteResultById(id);
    }

    @GetMapping
    public TestNGSuiteResult getBannerSuiteByBannerID(@RequestParam long bannerID) {
        return testNGService.getSuiteResultByBannerID(bannerID);
    }
}

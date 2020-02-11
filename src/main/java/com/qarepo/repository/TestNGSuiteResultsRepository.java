package com.qarepo.repository;

import com.qarepo.models.testng.TestNGSuiteResult;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestNGSuiteResultsRepository extends PagingAndSortingRepository<TestNGSuiteResult, Long> {

    TestNGSuiteResult save(TestNGSuiteResult testNGSuiteResult);

    TestNGSuiteResult findById(long id);

    TestNGSuiteResult findByBannerID(long bannerID);
}

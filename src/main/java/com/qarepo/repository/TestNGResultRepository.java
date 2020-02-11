package com.qarepo.repository;

import com.qarepo.models.testng.TestNGResult;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestNGResultRepository extends PagingAndSortingRepository<TestNGResult, Long> {

    TestNGResult save(TestNGResult testNGResult);

}

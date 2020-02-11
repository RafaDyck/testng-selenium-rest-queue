package com.qarepo.repository;

import com.qarepo.models.Form;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormRepository extends PagingAndSortingRepository<Form, Long> {

    Form save(Form form);

    Form findById(long id);

}

package com.qarepo.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qarepo.models.Form;
import com.qarepo.repository.FormRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class FormService {

    private Form form;

    @Autowired
    private FormRepository formRepository;

    @Autowired
    private TestNGService testNGService;

    public Form getForm() {
        return form;
    }

    public void setForm(Form form) {
        this.form = form;
    }

    public Form getFormFromDB(long id) {
        return formRepository.findById(id);
    }

    public void saveFormToDB() {
        formRepository.save(form);
    }

    public void runFormTests() {
        testNGService.createBannerHTMLSuite(generateFormTestParameters());
        testNGService.runSuiteAndCheckForErrors();
    }

    private Map<String, String> generateFormTestParameters() {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> parameters = mapper.convertValue(this.form, HashMap.class);
        parameters.put("browser", "Google Chrome Headless");
        parameters.put("baseURL", "https://www.google.com");
        if (parameters.get("id") != null) {
            parameters.replace("id", parameters.get("id"), String.valueOf(parameters.get("id")));
        }
        parameters.put("formValue", "Youtube");
        Map<String, String> newParameters = mapper.convertValue(parameters, HashMap.class);
        return newParameters;
    }

}

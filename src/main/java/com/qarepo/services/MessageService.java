package com.qarepo.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qarepo.models.Form;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MessageService {
    private String message;

    @Autowired
    private FormService formService;

    @Autowired
    private TestNGService testNGService;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void processMessage() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Form banner = mapper.readValue(message, Form.class);
        formService.setForm(banner);
        formService.runFormTests();
        testNGService.exportResults();
    }
}

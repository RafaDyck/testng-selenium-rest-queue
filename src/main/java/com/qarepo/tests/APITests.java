package com.qarepo.tests;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qarepo.workfront.WorkFrontClient;
import com.qarepo.services.WorkFrontService;
import com.qarepo.models.workfront.WorkFrontProject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;

public class APITests {

    @Test
    public void fileUpload_Success() {
        WorkFrontClient client = new WorkFrontClient("");
        File file = new File("");
        String handle = client.uploadFile(file);
        Assert.assertNotNull(handle);
        Assert.assertFalse(handle.isEmpty());
        Assert.assertTrue(handle.length() > 10);
    }

    @Test
    public void workFrontProjectHasTasks() throws Exception {
        WorkFrontClient client = new WorkFrontClient("");
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = (JsonNode) client.getResponse("project", "fields=tasks");
        WorkFrontProject project = mapper.convertValue(node, WorkFrontProject.class);
        WorkFrontService taskService = new WorkFrontService();
        taskService.setWorkFrontProjectDetails(project.getID());
        Assert.assertTrue(taskService.getWorkFrontProject().getTasks().size() > 3);
    }

}

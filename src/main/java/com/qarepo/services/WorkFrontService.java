package com.qarepo.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.qarepo.models.workfront.WorkFrontProject;
import com.qarepo.models.workfront.WorkFrontStatus;
import com.qarepo.models.workfront.WorkFrontTask;
import com.qarepo.workfront.WorkFrontClient;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class WorkFrontService {

    private WorkFrontProject workFrontProject;

    public WorkFrontService() {
    }

    public WorkFrontProject getWorkFrontProject() {
        return workFrontProject;
    }

    public void setWorkFrontProject(WorkFrontProject workFrontProject) {
        this.workFrontProject = workFrontProject;
    }

    public void setWorkFrontProjectDetails(String jobNumber) throws Exception {
        JsonNode node = null;
        WorkFrontClient client = new WorkFrontClient(jobNumber);
        ObjectMapper mapper = new ObjectMapper();
        Object response = client.getResponse("project", "fields=" + getWFApiParameters());
        if (response instanceof String) {
            throw new Exception((String) response);
        } else {
            node = (JsonNode) client.getResponse("project", "fields=" + getWFApiParameters());
            this.workFrontProject = mapper.convertValue(node, WorkFrontProject.class);
            this.workFrontProject = getAllTasksDetailsFromProject();
        }
    }

    private WorkFrontProject getAllTasksDetailsFromProject() {
        List<WorkFrontTask> tasks = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        for (WorkFrontTask workFrontTask : workFrontProject.getTasks()) {
            WorkFrontClient client = new WorkFrontClient();
            client.setJobID(workFrontTask.getID());
            JsonNode node = (JsonNode) client.getResponse("task", "");
            WorkFrontTask task = mapper.convertValue(node, WorkFrontTask.class);
            tasks.add(task);
        }
        workFrontProject.setTasks(tasks);
        return workFrontProject;
    }

    public void updateProjectStatus(String taskName, WorkFrontStatus statusCode) {
        if (taskName != null) {
            ObjectMapper mapper = new ObjectMapper();
            WorkFrontClient client = new WorkFrontClient();
            String encodedURL = "";
            for (WorkFrontTask workFrontTask : workFrontProject.getTasks()) {
                if (workFrontTask.getName().equalsIgnoreCase(taskName)) {
                    workFrontTask.setStatus(statusCode.toString());
                    client.setJobID(workFrontTask.getID());
                    JsonNode node = mapper.convertValue(workFrontTask, JsonNode.class);
                    try {
                        encodedURL = URLEncoder.encode(mapper.writeValueAsString(node), "UTF-8");
                    } catch (JsonProcessingException | UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    client.put("task", "updates=" + encodedURL);
                }
            }
        }
    }

    private String getWFApiParameters() {
        ObjectMapper mapper = new ObjectMapper();
        JavaType javaType = mapper.getTypeFactory().constructType(WorkFrontProject.class);
        BeanDescription beanDescription = mapper.getSerializationConfig().introspect(javaType);
        Set<String> ignoredProperties = mapper.getSerializationConfig().getAnnotationIntrospector().findPropertyIgnorals(beanDescription.getClassInfo()).getIgnored();
        List<BeanPropertyDefinition> properties = beanDescription.findProperties();
        StringBuilder builder = new StringBuilder();
        properties.stream().filter(p -> !ignoredProperties.contains(p.getName())).filter(BeanPropertyDefinition::hasField).forEach(p -> builder.append(p.getName()).append(","));
        String encodedURL = "";
        try {
            encodedURL = URLEncoder.encode(builder.substring(0, (builder.toString().length() - 1)), "UTF-8").replace("+", "%20");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodedURL;
    }
}

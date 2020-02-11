package com.qarepo.api;

import com.qarepo.models.Form;
import com.qarepo.models.workfront.WorkFrontProject;
import com.qarepo.models.workfront.WorkFrontStatus;
import com.qarepo.services.WorkFrontService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.PrintWriter;
import java.io.StringWriter;

@RestController
@RequestMapping(path = "/wf", produces = "application/json")
public class WorkFrontController {

    @Autowired
    private WorkFrontService workFrontService;

    @GetMapping
    public WorkFrontProject getProjectDetails(@RequestParam String projectID) throws Exception {
        workFrontService.setWorkFrontProjectDetails(projectID);
        return workFrontService.getWorkFrontProject();
    }

    @PostMapping(path = "/updateTask")
    public String updateTask(@RequestBody Form form) {
        try {
            workFrontService.setWorkFrontProjectDetails(form.getName());
            workFrontService.updateProjectStatus("Task 1", WorkFrontStatus.COMPLETED);
            workFrontService.updateProjectStatus("Task 2", WorkFrontStatus.COMPLETED);
            return "Task 1 & Task 2 set to COMPLETED for Job " + workFrontService.getWorkFrontProject().getName();
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            return "Failed To Update WF - " + e.getMessage();
        }
    }
}

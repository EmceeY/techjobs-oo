package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.ArrayList;

import static org.launchcode.models.JobFieldType.EMPLOYER;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping( value = "", method = RequestMethod.GET)
    public String index(Model model, @RequestParam int id) {

        Job job = jobData.findById(id);

        model.addAttribute("name", job.getName());
        model.addAttribute("employer", job.getEmployer().getValue());
        model.addAttribute("location", job.getLocation().getValue());
        model.addAttribute("positionType", job.getPositionType().getValue());
        model.addAttribute("coreCompetency", job.getCoreCompetency().getValue());
        model.addAttribute("ID", job.getId());

        // TODO #1 - get the Job with the given ID and pass it into the view

        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @Valid JobForm jobForm, Errors errors) {

        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.

        if (errors.getErrorCount() != 0){

            model.addAttribute("error",errors.getGlobalError());
            return "new-job";
        }
        else {

            String name = jobForm.getName();

            int employerId =  jobForm.getEmployerId();
            int locationId = jobForm.getLocation();
            int positionId = jobForm.getPositionType();
            int coreCompetencyId = jobForm.getCoreCompetency();

            Employer employer1 = jobForm.getEmployers().get(employerId);
            Location location1 = jobForm.getLocations().get(locationId);
            PositionType position1 = jobForm.getPositionTypes().get(positionId);
            CoreCompetency competency1 = jobForm.getCoreCompetencies().get(coreCompetencyId);

            Job newJob = new Job(name, employer1, location1, position1, competency1);

            jobData.add(newJob);

            model.addAttribute("name", name);
            model.addAttribute("employer", employer1);
            model.addAttribute("location", location1);
            model.addAttribute("positionType", position1);
            model.addAttribute("coreCompetency", competency1);

            //add jobForm to collection of jobs


            return "job-detail";
        }
    }

}

package org.launchcode.controllers;

import org.launchcode.models.Employer;
import org.launchcode.models.Job;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model, int id) {

        // TODO #1 - get the Job with the given ID and pass it into the view
        Job aJob = jobData.findById(id);
        model.addAttribute("job", aJob);

        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @Valid JobForm jobForm, Errors errors, RedirectAttributes attributes) {

        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.


        if (errors.hasErrors()) {
            model.addAttribute("Title", "Add Job");
            return "new-job";
        }

        Job newJob = new Job();
        newJob.setName(jobForm.getName());
        newJob.setEmployer(jobData.getEmployers().findById(jobForm.getEmployerId()));
        newJob.setCoreCompetency(jobData.getCoreCompetencies().findById(jobForm.getCoreCompetencyId()));
        newJob.setLocation(jobData.getLocations().findById(jobForm.getLocationId()));
        newJob.setPositionType(jobData.getPositionTypes().findById(jobForm.getPositionTypeId()));
        jobData.add(newJob);
        model.addAttribute("job", newJob);
        int id = newJob.getId();
        attributes.addAttribute("id", id);
        return "redirect:/job";

    }
}

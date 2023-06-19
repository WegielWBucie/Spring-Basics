package io.github.janekkodowanie.Notes.Controller;

import io.github.janekkodowanie.Notes.Logic.ProjectService;
import io.github.janekkodowanie.Notes.Model.Project;
import io.github.janekkodowanie.Notes.Model.ProjectStep;
import io.github.janekkodowanie.Notes.Model.Projection.ProjectWriteModel;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    String showProjects(Model model) {
        model.addAttribute( "project", new ProjectWriteModel());
        return "projects";
    }

    @PostMapping
    String addProject(
            @ModelAttribute("project") @Valid ProjectWriteModel currentProject,
            BindingResult bindingResult,
            Model model) {

        if(bindingResult.hasErrors()) {
            return "projects";
        }

        projectService.save(currentProject);
        model.addAttribute("project", new ProjectWriteModel());
        model.addAttribute("message", "Project added!");
        return "projects";
    }


    @PostMapping(params = "addStep")
    String addProjectStep(@ModelAttribute("project") ProjectWriteModel currentProject) {
        currentProject.getSteps().add(new ProjectStep());
        return "projects";
    }

    @PostMapping("/{ID}")
    String createGroup(
            @ModelAttribute("project") ProjectWriteModel currentProject,
            Model model,
            @PathVariable Long ID,
            @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime deadline,
            int priority) {
        try {
            projectService.createGroup(ID, priority, deadline);
            model.addAttribute("message", "Group successfully added!");
        }
        catch(IllegalArgumentException | IllegalStateException e) {
            model.addAttribute("message", "Error while creating group!");
        }
        return "projects";
    }

    @ModelAttribute("projects")
    List<Project> getProjects() {
        return projectService.readAll();
    }

}

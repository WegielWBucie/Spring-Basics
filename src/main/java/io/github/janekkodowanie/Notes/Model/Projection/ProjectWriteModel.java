package io.github.janekkodowanie.Notes.Model.Projection;

import io.github.janekkodowanie.Notes.Model.Project;
import io.github.janekkodowanie.Notes.Model.ProjectStep;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ProjectWriteModel {
    private String title;
    private String content;
    private List<ProjectStep> steps = new ArrayList<>();

    public ProjectWriteModel() {
        this.steps.add(new ProjectStep());
    }

    public String getTitle() {
        return title;
    }
    public String getContent() {
        return content;
    }

    public List<ProjectStep> getSteps() {
        return steps;
    }

    public void setSteps(List<ProjectStep> steps) {
        this.steps = steps;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Project toProject() {
        var result = new Project();
        result.setTitle(this.title);
        result.setContent(this.content);
        steps.forEach(step -> step.setProject(result));
        result.setSteps(new HashSet<>(steps));
        return result;
    }
}

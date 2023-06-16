package io.github.janekkodowanie.Notes.Model.Projection;

import io.github.janekkodowanie.Notes.Model.Project;
import io.github.janekkodowanie.Notes.Model.ProjectStep;

import java.util.HashSet;
import java.util.List;

public class ProjectWriteModel {
    private String title;
    private String content;
    private List<ProjectStep> steps;

    public ProjectWriteModel() {}

    public ProjectWriteModel(final String title, final String content) {
        this.title = title;
        this.content = content;
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

    public Project toProject() {
        var result = new Project();
        result.setTitle(this.title);
        result.setContent(this.content);
        steps.forEach(step -> step.setProject(result));
        result.setSteps(new HashSet<>(steps));
        return result;
    }
}

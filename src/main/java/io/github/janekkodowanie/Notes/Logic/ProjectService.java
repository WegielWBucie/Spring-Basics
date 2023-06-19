package io.github.janekkodowanie.Notes.Logic;

import io.github.janekkodowanie.Notes.Model.Projection.GroupNoteWriteModel;
import io.github.janekkodowanie.Notes.Model.Projection.GroupReadModel;
import io.github.janekkodowanie.Notes.Model.Projection.GroupWriteModel;
import io.github.janekkodowanie.Notes.Model.Projection.ProjectWriteModel;
import io.github.janekkodowanie.Notes.NoteConfigurationProperties;
import io.github.janekkodowanie.Notes.Model.NoteGroupRepository;
import io.github.janekkodowanie.Notes.Model.Project;
import io.github.janekkodowanie.Notes.Model.ProjectRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ProjectService {

    private final ProjectRepository projectRepository;
    private final NoteGroupRepository noteGroupRepository;
    private final NoteGroupService noteGroupService;
    private final NoteConfigurationProperties config;

    ProjectService(final ProjectRepository projectRepository, final NoteGroupRepository noteGroupRepository, final NoteGroupService service, final NoteConfigurationProperties config) {
        this.projectRepository = projectRepository;
        this.noteGroupRepository = noteGroupRepository;
        this.noteGroupService = service;
        this.config = config;
    }

    public List<Project> readAll() {
        return projectRepository.findAll();
    }

    public Project save(ProjectWriteModel toSave) {
        return projectRepository.save(toSave.toProject());
    }


    public GroupReadModel createGroup(Long projectID, int priority, LocalDateTime expiration) {
        if(!config.getTemplate().isAllowMultipleNotes() && noteGroupRepository.existsByProjectID(projectID)) {
            throw new IllegalStateException("Only 1 group from project is allowed.");
        }

        return projectRepository.findByID(projectID)
                .map(project -> {
                    GroupWriteModel targetGroup = new GroupWriteModel();
                    targetGroup.setTitle(project.getTitle());
                    targetGroup.setContent(project.getContent());
                    targetGroup.setNotes(project.getSteps().stream()
                            .map(projectStep -> {
                                        var note = new GroupNoteWriteModel();
                                        note.setTitle(projectStep.getTitle());
                                        note.setContent(projectStep.getContent());
                                        note.setPriority(priority);
                                        note.setExpiration(expiration);
                                        return note;
                                    }
                            ).collect(Collectors.toSet())
                    );

                    return noteGroupService.createGroup(targetGroup, project);
                }).orElseThrow(() -> new IllegalArgumentException("No project with given ID found."));
    }
}

package io.github.WegielWBucie.Notes.Logic;

import io.github.WegielWBucie.Notes.Model.*;
import io.github.WegielWBucie.Notes.Model.Projection.GroupNoteWriteModel;
import io.github.WegielWBucie.Notes.Model.Projection.GroupReadModel;
import io.github.WegielWBucie.Notes.Model.Projection.GroupWriteModel;
import io.github.WegielWBucie.Notes.NoteConfigurationProperties;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

class ProjectService {

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

    public Project save(Project source) {
        return projectRepository.save(source);
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

                    return noteGroupService.createGroup(targetGroup);
                }).orElseThrow(() -> new IllegalArgumentException("No project with given ID found."));
    }
}

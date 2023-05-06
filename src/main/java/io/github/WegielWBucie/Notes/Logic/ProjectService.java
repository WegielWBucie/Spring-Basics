package io.github.WegielWBucie.Notes.Logic;

import io.github.WegielWBucie.Notes.Model.*;
import io.github.WegielWBucie.Notes.Model.Projection.GroupReadModel;
import io.github.WegielWBucie.Notes.NoteConfigurationProperties;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

class ProjectService {

    private final ProjectRepository projectRepository;
    private final NoteGroupRepository noteGroupRepository;
    private final NoteConfigurationProperties config;

    ProjectService(final ProjectRepository projectRepository, final NoteGroupRepository noteGroupRepository, final NoteConfigurationProperties config) {
        this.projectRepository = projectRepository;
        this.noteGroupRepository = noteGroupRepository;
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

        NoteGroup result = projectRepository.findByID(projectID)
                .map(project -> {
                    NoteGroup targetGroup = new NoteGroup();
                    targetGroup.setTitle(project.getTitle());
                    targetGroup.setContent(project.getContent());
                    targetGroup.setPriority(priority);
                    targetGroup.setNotes(project.getSteps().stream()
                            .map(projectStep -> new Note(
                                    project.getTitle(),
                                    projectStep.getContent(),
                                    priority,
                                    expiration)
                            ).collect(Collectors.toSet())
                    );

                    targetGroup.setProject(project);
                    return noteGroupRepository.save(targetGroup);
                }).orElseThrow(() -> new IllegalArgumentException("No project with given ID found."));

        return new GroupReadModel(result);
    }
}

package io.github.janekkodowanie.Notes.Logic;

import io.github.janekkodowanie.Notes.Model.NoteGroupRepository;
import io.github.janekkodowanie.Notes.Model.NoteRepository;
import io.github.janekkodowanie.Notes.Model.ProjectRepository;
import io.github.janekkodowanie.Notes.NoteConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;

@Configuration
class LogicConfiguration {

    @Bean
    ProjectService projectService(final ProjectRepository repository,
                                  final NoteGroupRepository noteGroupRepository,
                                  final NoteGroupService noteGroupService,
                                  final NoteConfigurationProperties noteConfigurationProperties
    ) {
        return new ProjectService(repository, noteGroupRepository, noteGroupService, noteConfigurationProperties);
    }

    @Bean
    @RequestScope
    NoteGroupService noteGroupService(final NoteGroupRepository noteGroupRepository,
                                      final NoteRepository noteRepository
    ) {
        return new NoteGroupService(noteGroupRepository, noteRepository);
    }
}

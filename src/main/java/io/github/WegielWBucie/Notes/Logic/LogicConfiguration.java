package io.github.WegielWBucie.Notes.Logic;

import io.github.WegielWBucie.Notes.Model.NoteGroupRepository;
import io.github.WegielWBucie.Notes.Model.NoteRepository;
import io.github.WegielWBucie.Notes.Model.ProjectRepository;
import io.github.WegielWBucie.Notes.NoteConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;

@Configuration
class LogicConfiguration {

    @Bean
    ProjectService projectService(final ProjectRepository repository,
                                  final NoteGroupRepository noteGroupRepository,
                                  final NoteConfigurationProperties noteConfigurationProperties
    ) {
        return new ProjectService(repository, noteGroupRepository, noteConfigurationProperties);
    }

    @Bean
    @RequestScope
    NoteGroupService noteGroupService(final NoteGroupRepository noteGroupRepository,
                                      final NoteRepository noteRepository
    ) {
        return new NoteGroupService(noteGroupRepository, noteRepository);
    }
}

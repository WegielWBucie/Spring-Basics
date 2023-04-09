package io.github.WegielWBucie.Notes.Logic;

import io.github.WegielWBucie.Notes.Model.NoteGroup;
import io.github.WegielWBucie.Notes.Model.NoteGroupRepository;
import io.github.WegielWBucie.Notes.Model.ProjectRepository;
import io.github.WegielWBucie.Notes.NoteConfigurationProperties;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProjectServiceTest {

    /* Given -> When -> Then */

    @Test
    @DisplayName("Should throw IllegalStateException when configured to allow only 1 group and group already exists.")
    void createGroup_noMultipleGroupsConfig_And_openGroups_throwsIllegalStateException() {
        /* Given */
        var mockGroupRepository = groupRepositoryReturning(true);
        /* and */
        NoteConfigurationProperties mockConfig = configurationReturning(false);
        /* System under test */
        var toTest = new ProjectService(null, mockGroupRepository, mockConfig);
        /* When */
        var exception = catchThrowable(() -> toTest.createGroup(0L, 0, LocalDateTime.now()));
        /* Then */
        assertThat(exception)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("1 group");
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when configuration OK and no project with given ID.")
    void createGroup_configOk_And_noProject_throwsIllegalArgumentException() {
        /* Given */
        var mockRepository = mock(ProjectRepository.class);
        when(mockRepository.findByID(anyLong())).thenReturn(Optional.empty());
        /* and */
        NoteConfigurationProperties mockConfig = configurationReturning(true);
        /* System under test */
        var toTest = new ProjectService(mockRepository, null, mockConfig);
        /* When */
        var exception = catchThrowable(() -> toTest.createGroup(0L, 0, LocalDateTime.now()));
        /* Then */
        assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("No project");
    }
    @Test
    @DisplayName("Should throw IllegalArgumentException when configured to allow only 1 group and no project with given ID.")
    void createGroup_noMultipleGroupsConfig_And_noProject_throwsIllegalArgumentException() {
        /* Given */
        var mockRepository = mock(ProjectRepository.class);
        when(mockRepository.findByID(anyLong())).thenReturn(Optional.empty());
        /* and */
        NoteGroupRepository mockGroupRepository = groupRepositoryReturning(false);
        /* and */
        NoteConfigurationProperties mockConfig = configurationReturning(true);
        /* System under test */
        var toTest = new ProjectService(mockRepository, null, mockConfig);
        /* When */
        var exception = catchThrowable(() -> toTest.createGroup(0L, 0, LocalDateTime.now()));
        /* Then */
        assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("No project");
    }

    private NoteGroupRepository groupRepositoryReturning(final boolean result) {
        NoteGroupRepository mockGroupRepository = mock(NoteGroupRepository.class);
        when(mockGroupRepository.existsByProjectID(anyLong())).thenReturn(result);
        return mockGroupRepository;
    }

    private NoteConfigurationProperties configurationReturning(final boolean t) {
        var mockTemplate = mock(NoteConfigurationProperties.Template.class);
        when(mockTemplate.isAllowMultipleNotes()).thenReturn(t);
        /* and */
        var mockConfig = mock(NoteConfigurationProperties.class);
        when(mockConfig.getTemplate()).thenReturn(mockTemplate);
        return mockConfig;
    }

    private NoteGroupRepository inMemoryNoteGroupRepository() {
        return new NoteGroupRepository() {
            private Long index;
            private Map<Long, NoteGroup> map = new HashMap<>();

            @Override
            public List<NoteGroup> findAll() {
                return map.values().stream().toList();
            }

            @Override
            public Optional<NoteGroup> findByID(final Long ID) {
                return Optional.ofNullable(map.get(ID));
            }

            @Override
            public NoteGroup save(final NoteGroup entity) {
                if(entity.getID() == 0) {
                    try {
                        NoteGroup.class.getDeclaredField("ID").set(entity, ++index);
                    }
                    catch(NoSuchFieldException | IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
                map.put(entity.getID(), entity);
                return entity;
            }

            @Override
            public boolean existsByProjectID(final Long projectID) {
                return map.values().stream()
                        .anyMatch(noteGroup -> noteGroup.getProject() != null && noteGroup.getProject().getID() == projectID);
            }
        };
    }
}
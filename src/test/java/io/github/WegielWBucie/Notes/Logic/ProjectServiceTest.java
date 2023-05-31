package io.github.WegielWBucie.Notes.Logic;

import io.github.WegielWBucie.Notes.Model.*;
import io.github.WegielWBucie.Notes.Model.Projection.GroupReadModel;
import io.github.WegielWBucie.Notes.NoteConfigurationProperties;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProjectServiceTest {

    /* Given -> When -> Then */

    @Test
    @DisplayName("Should throw IllegalStateException when configured to allow only 1 group and group already exists.")
    void createGroup_noMultipleGroupsConfig_And_groupExists_throwsIllegalStateException() {
        /* Given */
        var mockGroupRepository = groupRepositoryReturning(true);
        /* and */
        NoteConfigurationProperties mockConfig = configurationReturning(false);
        /* System under test */
        var toTest = new ProjectService(null, mockGroupRepository, null, mockConfig);
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
        var toTest = new ProjectService(mockRepository, null, null, mockConfig);
        /* When */
        var exception = catchThrowable(() -> toTest.createGroup(0L, 0, LocalDateTime.now()));
        /* Then */
        assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("No project");
    }

    @Test
    @DisplayName("Should throw IllegalArgumentException when configured to allow only 1 group and no groups and no project with given ID.")
    void createGroup_noMultipleGroupsConfig_And_noGroupsAndNoProject_throwsIllegalArgumentException() {
        /* Given */
        var mockRepository = mock(ProjectRepository.class);
        when(mockRepository.findByID(anyLong())).thenReturn(Optional.empty());
        /* and */
        NoteGroupRepository mockGroupRepository = groupRepositoryReturning(false);
        /* and */
        NoteConfigurationProperties mockConfig = configurationReturning(true);
        /* System under test */
        var toTest = new ProjectService(mockRepository, mockGroupRepository, null, mockConfig);
        /* When */
        var exception = catchThrowable(() -> toTest.createGroup(1L, 0, LocalDateTime.now()));
        /* Then */
        assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("No project");
    }

    @Test
    @DisplayName("Should create and save new group from project.")
    void createGroup_configOk_And_existingProject_createsAndSavesGroup() {
        /* Given */
        var project = projectWith("ProjectTitle", "ProjectContent", Set.of("A", "B", "C", "D"));
        var mockRepository = mock(ProjectRepository.class);
        when(mockRepository.findByID(anyLong())).thenReturn(Optional.of(project));
        /* and */
        var expiration = LocalDate.now().atStartOfDay();
        /* and */
        var inMemoryGroupRepo = inMemoryNoteGroupRepository();
        var serviceWithInMemRepo = dummyGroupService(inMemoryGroupRepo);
        /* and */
        int countBeforeCall = inMemoryGroupRepo.count();
        /* and */
        var mockConfig = configurationReturning(true);
        /* System under test */
        var toTest = new ProjectService(mockRepository, inMemoryGroupRepo, serviceWithInMemRepo, mockConfig);
        /* When */
        GroupReadModel result = toTest.createGroup(1L, 10, expiration);
        /* Then */
        assertThat(result.getTitle()).isEqualTo("ProjectTitle");
        assertThat(result.getContent()).isEqualTo("ProjectContent");
        assertThat(result.getNotes().stream().allMatch(note -> note.getTitle().equals("Title.")));
        assertThat(result.getExpiration().equals(expiration));
        assertThat(countBeforeCall + 1).isEqualTo(inMemoryGroupRepo.count());
    }

    private NoteGroupService dummyGroupService(final InMemoryGroupRepository inMemoryGroupRepo) {
        return new NoteGroupService(inMemoryGroupRepo, null);
    }

    private Project projectWith(String projectTitle, String projectContent, Set<String> contents) {
        Set<ProjectStep> steps = contents.stream()
                .map(content -> {
                    var step = mock(ProjectStep.class);
                    when(step.getTitle()).thenReturn("Title.");
                    when(step.getContent()).thenReturn(content);
                    return step;
                }).collect(Collectors.toSet());

        var result = mock(Project.class);
        when(result.getTitle()).thenReturn(projectTitle);
        when(result.getContent()).thenReturn(projectContent);
        when(result.getSteps()).thenReturn(steps);
        return result;
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

    private InMemoryGroupRepository inMemoryNoteGroupRepository() {
        return new InMemoryGroupRepository();
    }

    private static class InMemoryGroupRepository implements NoteGroupRepository {
        private Long index = 0L;
        private Map<Long, NoteGroup> map = new HashMap<>();

        public int count() {
            return map.values().size();
        }

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
            if(entity.getID() == null) {
                try {
                    Field idField = BaseNote.class.getDeclaredField("ID");
                    idField.setAccessible(true);
                    idField.set(entity, ++index);
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
    }
}
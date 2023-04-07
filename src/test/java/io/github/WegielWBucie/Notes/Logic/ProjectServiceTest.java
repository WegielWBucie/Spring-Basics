package io.github.WegielWBucie.Notes.Logic;

import io.github.WegielWBucie.Notes.Model.NoteGroupRepository;
import io.github.WegielWBucie.Notes.NoteConfigurationProperties;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProjectServiceTest {

    @Test
    @DisplayName("Should throw IllegalStateException when configured to allow only 1 group and group already exists.")
    void createGroup_noMultipleGroupsConfig_And_openGroups_throwsIllegalStateException() {
        /*
          Given
          When
          Then
         */
        
        var mockGroupRepository = mock(NoteGroupRepository.class);
        when(mockGroupRepository.existsByProjectID(anyLong())).thenReturn(true);
        /* and */
        var mockTemplate = mock(NoteConfigurationProperties.Template.class);
        when(mockTemplate.isAllowMultipleNotes()).thenReturn(false);
        /* and */
        var mockConfig = mock(NoteConfigurationProperties.class);
        when(mockConfig.getTemplate()).thenReturn(mockTemplate);

        /* System under test */
        var toTest = new ProjectService(null, mockGroupRepository, mockConfig);

        /* When */
        var exception = catchThrowable(() -> toTest.createGroup(0L, 0, LocalDateTime.now()));
        /* Then */
        assertThat(exception)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("1 group");
    }
}
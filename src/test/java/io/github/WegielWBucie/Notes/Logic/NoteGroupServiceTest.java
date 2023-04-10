package io.github.WegielWBucie.Notes.Logic;

import io.github.WegielWBucie.Notes.Model.NoteGroupRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class NoteGroupServiceTest {

    @Test
    @DisplayName("Should throw IllegalArgumentException when there is no group with given ID.")
    void toggleGroup_noGroup_throwsIllegalArgumentException() {
        /* Given */
        var mockGroupRepository = mock(NoteGroupRepository.class);
        /* System under test */
        var toTest = new NoteGroupService(mockGroupRepository);
        /* When */
        var exception = catchThrowable(() -> toTest.toggleGroup(1L));
        /* Then */
        assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("given ID not found");
    }

    @Test
    @DisplayName("Should toggle and save group.")
    void toggleGroup_groupExists_And_toggleAndSaveWork() {
        /* TODO */
    }
}
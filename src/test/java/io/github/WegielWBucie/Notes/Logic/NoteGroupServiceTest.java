package io.github.WegielWBucie.Notes.Logic;

import io.github.WegielWBucie.Notes.Model.NoteGroupRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class NoteGroupServiceTest {

    @Test
    @DisplayName("Should throw IllegalArgumentException when there is no group with given ID.")
    void toggleGroup_noGroup_throwsIllegalArgumentException() {
        var mockGroupRepository = mock(NoteGroupRepository.class);
        when(mockGroupRepository.existsByProjectID(anyLong())).thenReturn(true);
        /* TODO */
    }


    @Test
    @DisplayName("Should toggle and save group.")
    void toggleGroup_groupExists_And_toggleAndSaveWork() {
        /* TODO */
    }
}
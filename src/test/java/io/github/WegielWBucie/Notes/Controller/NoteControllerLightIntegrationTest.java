package io.github.WegielWBucie.Notes.Controller;

import io.github.WegielWBucie.Notes.Model.BaseNote;
import io.github.WegielWBucie.Notes.Model.Note;
import io.github.WegielWBucie.Notes.Model.NoteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


@WebMvcTest(controllers = {NoteController.class})
@ActiveProfiles("integration")
class NoteControllerLightIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NoteRepository noteRepository;

    @Test
    void httpGet_returnsGivenIDTask() throws Exception {
        /* Given */

        Note note = new Note(
                "ITestTitle", "ITestContent", 1, LocalDateTime.of(2023, 2, 28, 23, 59)
        );
        Field idField = BaseNote.class.getDeclaredField("ID");
        idField.setAccessible(true);
        idField.set(note, 0L);

        when(noteRepository.findById(anyLong())).thenReturn(Optional.of(note));

        /* When + Then */
        mockMvc.perform(get("/notes/0"))
                .andDo(print())
                .andExpect(content().string(containsString("\"title\":\"ITestTitle\"")));
    }
}

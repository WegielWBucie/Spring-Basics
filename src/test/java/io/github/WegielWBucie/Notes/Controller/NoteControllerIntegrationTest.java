package io.github.WegielWBucie.Notes.Controller;

import io.github.WegielWBucie.Notes.Model.Note;
import io.github.WegielWBucie.Notes.Model.NoteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("integration")
@AutoConfigureMockMvc
class NoteControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private NoteRepository noteRepository;

    @Test
    void httpGet_returnsGivenIDTask() throws Exception {
        /* Given */
        Long ID = noteRepository.save(new Note(
                "ITestTitle", "ITestContent", 1, LocalDateTime.of(2023, 2,28, 23,59))
        ).getID();

        /* When + Then */
        mockMvc.perform(get("/notes/" + ID)).andExpect(status().is2xxSuccessful());
    }
}

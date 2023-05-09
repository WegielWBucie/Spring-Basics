package io.github.WegielWBucie.Notes.Controller;

import io.github.WegielWBucie.Notes.Model.Note;
import io.github.WegielWBucie.Notes.Model.NoteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("integration")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NoteControllerE2ETest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    NoteRepository noteRepository;

    @Test
    void httpGet_returnsAllNotes() {
        /* Given */
        noteRepository.save(new Note("TestTitle", "Content1", 1, LocalDateTime.now().plusDays(3)));
        noteRepository.save(new Note("TestTitle2", "Content2", 2, LocalDateTime.now().plusDays(3)));

        /* When */
        Note[] result = restTemplate.getForObject("http://localhost:" + this.port + "/notes", Note[].class);

        /* Then */
        assertThat(result).hasSize(2);
    }

}
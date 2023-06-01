package io.github.WegielWBucie.Notes.Controller;

import io.github.WegielWBucie.Notes.Model.Note;
import io.github.WegielWBucie.Notes.Model.NoteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

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
        int initialSize = noteRepository.findAll().size();
        noteRepository.save(new Note("TestTitle", "Content1", 1, LocalDateTime.now().plusDays(3)));
        noteRepository.save(new Note("TestTitle2", "Content2", 2, LocalDateTime.now().plusDays(3)));
        /* When */
        Note[] result = restTemplate.getForObject("http://localhost:" + this.port + "/notes", Note[].class);

        /* Then */
        assertThat(result).hasSize(initialSize + 2);
    }


    @Test
    void httpGet_returnsGivenIDNote() {
        /* Given */
        Note note = new Note("TestTitle", "Content1", 1, LocalDateTime.now().plusDays(3));
        noteRepository.save(note);

        long ID = noteRepository.findAll().get(0).getID();

        /* When */
        Note result = restTemplate.getForObject("http://localhost:" + this.port + "/notes/" + ID, Note.class);

        /* Then */
        assertThat(result.equals(note));
    }

    @Test
    void httpGet_returnsNull_SuchIDDoesntExist() {
        /* When */
        Note result = restTemplate.getForObject("http://localhost:" + this.port + "/notes/" + "-1", Note.class);

        /* Then */
        assertThat(result).isNull();
    }

    @Test
    void httpPut_editsNote() {
        /* Given */
        Note note = new Note("TestTitle", "Content1", 1, LocalDateTime.now().plusDays(3));
        Note edit = new Note("EditTitle", "EditContent", 1, LocalDateTime.now().plusDays(3));

        long ID = noteRepository.save(note).getID();
        HttpEntity<Note> requestEntity = new HttpEntity<>(edit);

        /* When */
        ResponseEntity<Note> response = restTemplate.exchange(
                "http://localhost:" + this.port + "/notes/" + ID,
                HttpMethod.PUT,
                requestEntity,
                Note.class
        );
        /* Then */

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Optional<Note> result = noteRepository.findById(ID);
        assertThat(result).isNotNull();
        assertThat(result.equals(edit));
    }
}
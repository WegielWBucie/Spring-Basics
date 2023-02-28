package io.github.WegielWBucie.Notes.Controller;

import io.github.WegielWBucie.Notes.Model.Note;
import io.github.WegielWBucie.Notes.Model.NoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
class NoteController {

    private final NoteRepository noteRepository;

    private static final Logger logger = LoggerFactory.getLogger(NoteController.class);

    @Autowired
    NoteController(final NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @GetMapping(path = "/notes/", params = {"!sort", "!page", "!size"})
    ResponseEntity<?> findAllNotes() {
        logger.warn("Exposing all tasks.");
        return ResponseEntity.ok(noteRepository.findAll());
    }

    @GetMapping(path = "/notes")
    ResponseEntity<List<Note>> findALlNotes(Pageable page) {

        return ResponseEntity.ok(noteRepository.findAll(page).getContent());
    }

    @GetMapping(path = "/notes/{ID}")
    ResponseEntity<?> findNoteByID(@PathVariable @Valid final Long ID) {
        if(!noteRepository.existsById(ID)) {
            logger.error("No note with selected ID exists. ( ID = " + ID + " )");
            return ResponseEntity.notFound().build();
        }

        logger.warn("Exposing note: " + ID);
        return ResponseEntity.ok(noteRepository.findById(ID));
    }

    @PutMapping(path = "/notes/{ID}")
    ResponseEntity<?> editNote(@PathVariable @Valid final Long ID, @RequestBody final Note toUpdate) {
        if (!noteRepository.existsById(ID)) {
            logger.error("No note with selected ID exists. ( ID = " + ID + " )");
            return ResponseEntity.notFound().build();
        }

        noteRepository.findById(ID)
                .ifPresent(note -> {
                    note.updateFrom(toUpdate);
                    noteRepository.save(note); /* equivalent to @Transactional */
                });

        return ResponseEntity.noContent().build();
    }

    @PostMapping(path = "/notes")
    ResponseEntity<?> postNote(@RequestBody @Valid Note toPost) {
        Note result = noteRepository.save(toPost);
        URI location = URI.create("/notes/" + result.getId());
        return ResponseEntity.created(location).body(result);
    }

    @Transactional
    @PatchMapping(path = "/notes/{ID}")
    public ResponseEntity<?> incrementPriority(@PathVariable Long ID) {
        if(!noteRepository.existsById(ID)) {
            logger.error("No note with selected ID exists. ( ID = " + ID + " )");
            return ResponseEntity.notFound().build();
        }

        noteRepository.findById(ID)
                        .ifPresent(note -> note.setPriority(note.getPriority() + 1));

        logger.warn("Edited note " + ID + ".");
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path = "/notes/{ID}")
    ResponseEntity<?> deleteNote(@PathVariable @Valid Long ID) {
        try {
            noteRepository.deleteById(ID);
            logger.warn("Removing task: " + ID);

            return ResponseEntity.noContent().build();
        }
        catch(EmptyResultDataAccessException emptyResultDataAccessException) {
            logger.error("No note with selected ID exists. (ID = " + ID + ")");
            return ResponseEntity.notFound().build();
        }
        catch(Exception exception) {
            logger.error(exception.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}

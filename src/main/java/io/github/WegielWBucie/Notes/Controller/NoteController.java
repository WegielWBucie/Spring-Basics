package io.github.WegielWBucie.Notes.Controller;

import io.github.WegielWBucie.Notes.Model.Note;
import io.github.WegielWBucie.Notes.Model.NoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RepositoryRestController
class NoteController {

    private final NoteRepository noteRepository;

    private static final Logger logger = LoggerFactory.getLogger(NoteController.class);

    @Autowired
    NoteController(final NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @GetMapping(path = "/notes")
    ResponseEntity<?> findALlNotes() {
        logger.warn("Exposing all tasks.");
        return ResponseEntity.ok(noteRepository.findAll());
    }

    @GetMapping(path = "/notes/{ID}")
    ResponseEntity<?> findNoteByID(@PathVariable @Valid final Long ID) {
        if(!noteRepository.existsById(ID)) {
            logger.error("No note with ID " + ID + " exists!");
            return ResponseEntity.notFound().build();
        }

        logger.warn("Exposing note: " + ID);
        return ResponseEntity.ok(noteRepository.findById(ID));
    }

    @PutMapping(path = "/notes/{ID}")
    ResponseEntity<?> editNote(@PathVariable @Valid final Long ID) {

        logger.warn("Edit mode:");
        return null;
    }

    @PostMapping(path = "/notes")
    ResponseEntity<?> postNote(@RequestBody @Valid Note toPost) {

        Note result = noteRepository.save(toPost);
        URI location = URI.create("/notes/" + result.getId());
        return ResponseEntity.created(location).body(result);
    }

    @DeleteMapping(path = "/notes/{ID}")
    ResponseEntity<?> deleteNote(@PathVariable @Valid Long ID) {

        try {
            noteRepository.deleteById(ID);
            logger.warn("Removing task: " + ID);

            return ResponseEntity.noContent().build();
        }
        catch(EmptyResultDataAccessException emptyResultDataAccessException) {
            logger.error("No note with ID " + ID + " exists!");
            return ResponseEntity.notFound().build();
        }
        catch(Exception exception) {
            logger.error(exception.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}

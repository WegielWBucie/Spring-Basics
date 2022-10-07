package io.github.WegielWBucie.Notes.Controller;

import io.github.WegielWBucie.Notes.Model.NoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

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
    ResponseEntity<?> findNoteByID(@PathVariable final Long ID) {
        logger.warn("Exposing note: {ID}." + ID);
        return ResponseEntity.ok(noteRepository.findById(ID));
    }



//    @PutMapping(path = "/notes")
//    ResponseEntity<?> addNote() {
//        logger.info("New note has been added.");
//    }



}

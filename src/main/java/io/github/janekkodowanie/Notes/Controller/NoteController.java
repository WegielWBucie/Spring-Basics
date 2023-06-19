package io.github.janekkodowanie.Notes.Controller;

import io.github.janekkodowanie.Notes.Logic.NoteService;
import io.github.janekkodowanie.Notes.Model.Note;
import io.github.janekkodowanie.Notes.Model.NoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(value = "/notes")
class NoteController {

    private final NoteRepository noteRepository;
    private final NoteService noteService;

    private static final Logger logger = LoggerFactory.getLogger(NoteController.class);

    @Autowired
    NoteController(final NoteRepository noteRepository, final NoteService noteService) {
        this.noteRepository = noteRepository;
        this.noteService = noteService;
    }

    @GetMapping(params = {"!sort", "!page", "!size"})
    CompletableFuture<ResponseEntity<List<Note>>> findAllNotes() {
        logger.warn("Exposing all tasks.");
        return noteService.findAllAsync().thenApply(ResponseEntity::ok);
    }

     @GetMapping(path = "/{ID}")
     ResponseEntity<Optional<Note>> findByID(@PathVariable @Valid final Long ID) {
        return ResponseEntity.ok(noteRepository.findById(ID));
    }

     @GetMapping(path = "/search/done")
     ResponseEntity<List<Note>> findALlNotes(@RequestParam(defaultValue = "true") boolean state) {
        return ResponseEntity.ok(noteRepository.findByDone(state));
    }


    @PutMapping(path = "/{ID}")
    ResponseEntity<?> editNote(@PathVariable @Valid final Long ID, @RequestBody final Note toUpdate) {
        if(!noteRepository.existsById(ID)) {
            logger.error("No note with selected ID exists. ( ID = " + ID + " )");
            return ResponseEntity.notFound().build();
        }

        noteRepository.findById(ID)
                .ifPresent(note -> {
                    note.updateFrom(toUpdate);
                    noteRepository.save(note); /* equivalent to @Transactional */
                });

        return ResponseEntity.ok().build();
    }

    @PostMapping
    ResponseEntity<?> postNote(@RequestBody @Valid Note toPost) {
        Note result = noteRepository.save(toPost);
        URI location = URI.create("/notes/" + result.getID());
        return ResponseEntity.created(location).body(result);
    }

    @Transactional
    @PatchMapping(path = "/{ID}")
    public ResponseEntity<?> patch(@PathVariable Long ID) {
        if(!noteRepository.existsById(ID)) {
            logger.error("No note with selected ID exists. ( ID = " + ID + " )");
            return ResponseEntity.notFound().build();
        }

        noteRepository.findById(ID)
                        .ifPresent(note -> note.setDone(!note.isDone()));

        logger.warn("Patched note " + ID + ".");
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/{ID}")
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

    @GetMapping(path = "/search/today")
    ResponseEntity<List<Note>> findNotesForToday() {
        return ResponseEntity.ok(noteRepository.findAll().stream()
                .filter(note -> !note.isDone() && (note.getExpiration() == null || note.getExpiration().
                        isBefore(LocalDateTime.now().plusDays(1)
                                .withHour(0).withMinute(0).withSecond(0)))
                )
                .toList());
    }
}

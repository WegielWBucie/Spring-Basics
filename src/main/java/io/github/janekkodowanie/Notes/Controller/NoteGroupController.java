package io.github.janekkodowanie.Notes.Controller;

import io.github.janekkodowanie.Notes.Logic.NoteGroupService;
import io.github.janekkodowanie.Notes.Model.Note;
import io.github.janekkodowanie.Notes.Model.NoteGroupRepository;
import io.github.janekkodowanie.Notes.Model.NoteRepository;
import io.github.janekkodowanie.Notes.Model.Projection.GroupReadModel;
import io.github.janekkodowanie.Notes.Model.Projection.GroupWriteModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/groups")
public class NoteGroupController {

    NoteGroupService noteGroupService;
    NoteGroupRepository noteGroupRepository;
    NoteRepository noteRepository;

    private static final Logger logger = LoggerFactory.getLogger(NoteGroupController.class);


    public NoteGroupController(NoteGroupRepository noteGroupRepository, NoteRepository noteRepository, NoteGroupService noteGroupService) {
        this.noteGroupService = noteGroupService;
        this.noteGroupRepository = noteGroupRepository;
        this.noteRepository = noteRepository;
    }

    @GetMapping
    ResponseEntity<List<GroupReadModel>> findAllGroups() {
        logger.info("Exposing all groups.");
        return ResponseEntity.ok(noteGroupService.readAll());
    }

    @PostMapping
    ResponseEntity<GroupReadModel> postGroup(@RequestBody @Valid GroupWriteModel toPost) {
        logger.info("Creating new group.");
        GroupReadModel result = noteGroupService.createGroup(toPost);
        return ResponseEntity.created(URI.create("/" + result.getID())).body(result);
    }

    @GetMapping("/{ID}/notes")
    ResponseEntity<List<Note>> findNotesFromGroup(@PathVariable Long ID) {
        logger.info("Exposing all notes from group with ID = " + ID + ".");
        return ResponseEntity.ok(noteRepository.findAllByGroup_ID(ID));
    }

    @PatchMapping("/{ID}")
    ResponseEntity<?> toggleGroup(@PathVariable Long ID) {
        logger.info("Toggling group with ID = " + ID + ".");
        noteGroupService.toggleGroup(ID);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<?> handleIllegalArgument(IllegalArgumentException e) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(IllegalStateException.class)
    ResponseEntity<?> handleIllegalState(IllegalStateException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}


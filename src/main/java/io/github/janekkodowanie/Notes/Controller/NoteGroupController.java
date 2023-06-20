package io.github.janekkodowanie.Notes.Controller;

import io.github.janekkodowanie.Notes.Logic.NoteGroupService;
import io.github.janekkodowanie.Notes.Model.Note;
import io.github.janekkodowanie.Notes.Model.NoteGroupRepository;
import io.github.janekkodowanie.Notes.Model.NoteRepository;
import io.github.janekkodowanie.Notes.Model.Projection.GroupReadModel;
import io.github.janekkodowanie.Notes.Model.Projection.GroupWriteModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Controller
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

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<List<GroupReadModel>> findAllGroups() {
        logger.info("Exposing all groups.");
        return ResponseEntity.ok(noteGroupService.readAll());
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<GroupReadModel> postGroup(@RequestBody @Valid GroupWriteModel toPost) {
        logger.info("Creating new group.");
        GroupReadModel result = noteGroupService.createGroup(toPost);
        return ResponseEntity.created(URI.create("/" + result.getID())).body(result);
    }

    @GetMapping(path = "/{ID}/notes", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<List<Note>> findNotesFromGroup(@PathVariable Long ID) {
        logger.info("Exposing all notes from group with ID = " + ID + ".");
        return ResponseEntity.ok(noteRepository.findAllByGroup_ID(ID));
    }

    @PatchMapping(value = "/{ID}")
    @ResponseBody
    ResponseEntity<?> toggleGroup(@PathVariable Long ID) {
        logger.info("Toggling group with ID = " + ID + ".");
        noteGroupService.toggleGroup(ID);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    ResponseEntity<?> handleIllegalArgument(IllegalArgumentException e) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseBody
    ResponseEntity<?> handleIllegalState(IllegalStateException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ModelAttribute("groups")
    List<GroupReadModel> getGroups() {
        return noteGroupService.readAll();
    }

}


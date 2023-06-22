package io.github.janekkodowanie.Notes.Controller;

import io.github.janekkodowanie.Notes.Logic.NoteGroupService;
import io.github.janekkodowanie.Notes.Model.Note;
import io.github.janekkodowanie.Notes.Model.NoteRepository;
import io.github.janekkodowanie.Notes.Model.Projection.NoteWriteModel;
import io.github.janekkodowanie.Notes.Model.Projection.GroupReadModel;
import io.github.janekkodowanie.Notes.Model.Projection.GroupWriteModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Controller
@IllegalExceptionsProcessing
@RequestMapping("/groups")
public class NoteGroupController {

    NoteGroupService noteGroupService;
    NoteRepository noteRepository;

    private static final Logger logger = LoggerFactory.getLogger(NoteGroupController.class);


    public NoteGroupController(NoteGroupService noteGroupService, NoteRepository noteRepository) {
        this.noteGroupService = noteGroupService;
        this.noteRepository = noteRepository;
    }

    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    String showGroups(Model model) {
        model.addAttribute("group", new GroupWriteModel());
        return "groups";
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.TEXT_HTML_VALUE)
    String addGroup(
            @ModelAttribute("group") @Valid GroupWriteModel currentGroup,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            return "groups";
        }

        noteGroupService.createGroup(currentGroup);
        model.addAttribute("group", new GroupWriteModel());
        model.addAttribute("groups", noteGroupService.readAll());
        model.addAttribute("message", "Group added successfully!");
        return "groups";
    }

    @PostMapping(params = "addNote",
            produces = MediaType.TEXT_HTML_VALUE)
    String addGroupNote(@ModelAttribute("group") GroupWriteModel currentGroup) {
        currentGroup.getNotes().add(new NoteWriteModel());
        return "groups";
    }

    /*##################################################################*/
    /*##################################################################*/

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<List<GroupReadModel>> findAllGroups() {
        logger.info("Exposing all groups.");
        return ResponseEntity.ok(noteGroupService.readAll());
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<GroupReadModel> createGroup(@RequestBody @Valid GroupWriteModel toPost) {
        logger.info("Creating new group.");
        GroupReadModel result = noteGroupService.createGroup(toPost);
        return ResponseEntity.created(URI.create("/" + result.getID())).body(result);
    }

    @GetMapping(path = "/{ID}/notes",
            produces = MediaType.APPLICATION_JSON_VALUE)
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

    /*##################################################################*/
    /*##################################################################*/

    @ModelAttribute("groups")
    List<GroupReadModel> getGroups() {
        return noteGroupService.readAll();
    }
}


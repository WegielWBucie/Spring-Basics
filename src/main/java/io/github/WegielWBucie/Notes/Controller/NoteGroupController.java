package io.github.WegielWBucie.Notes.Controller;

import io.github.WegielWBucie.Notes.Logic.NoteGroupService;
import io.github.WegielWBucie.Notes.Model.Note;
import io.github.WegielWBucie.Notes.Model.NoteGroupRepository;
import io.github.WegielWBucie.Notes.Model.NoteRepository;
import io.github.WegielWBucie.Notes.Model.Projection.GroupReadModel;
import io.github.WegielWBucie.Notes.Model.Projection.GroupWriteModel;
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


    public NoteGroupController(NoteGroupRepository noteGroupRepository, NoteRepository noteRepository, NoteGroupService noteGroupService) {
        this.noteGroupService = noteGroupService;
        this.noteGroupRepository = noteGroupRepository;
        this.noteRepository = noteRepository;
    }

    @GetMapping
    ResponseEntity<List<GroupReadModel>> findAllGroups() {
        return ResponseEntity.ok(noteGroupService.readAll());
    }

    @PostMapping
    ResponseEntity<?> postGroup(@RequestBody @Valid GroupWriteModel toPost) {
        return ResponseEntity.created(URI.create("/")).body(noteGroupService.createGroup(toPost));
    }

    @GetMapping("/{ID}/notes")
    ResponseEntity<List<Note>> findNotesFromGroup(@PathVariable Long ID) {
        return ResponseEntity.ok(noteRepository.findAllByGroup_ID(ID));
    }

    @PatchMapping("/{ID}")
    ResponseEntity<?> toggleGroup(@PathVariable Long ID) {
        noteGroupService.toggleGroup(ID);
        return ResponseEntity.noContent().build();
    }
}


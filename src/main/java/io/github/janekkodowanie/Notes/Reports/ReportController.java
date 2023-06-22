package io.github.janekkodowanie.Notes.Reports;

import io.github.janekkodowanie.Notes.Model.Note;
import io.github.janekkodowanie.Notes.Model.NoteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportController {

    private final NoteRepository noteRepository;
    private final PersistedNoteEventRepository eventRepository;

    public ReportController(NoteRepository noteRepository, PersistedNoteEventRepository eventRepository) {
        this.noteRepository = noteRepository;
        this.eventRepository = eventRepository;
    }

    @GetMapping("/count/{ID}")
    ResponseEntity<NoteWithChangesCount> readNoteWithChangesCount(@PathVariable Long ID) {
        return noteRepository.findById(ID)
                .map(note -> new NoteWithChangesCount(note, eventRepository.findByNoteId(ID)))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    private static class NoteWithChangesCount {
        public String title;
        public String content;
        public boolean done;

        public int changesCount;

        public NoteWithChangesCount(Note note, final List<PersistedNoteEvent> events) {
            title = note.getTitle();
            content = note.getContent();
            done = note.isDone();

            changesCount = events.size();
        }
    }
}

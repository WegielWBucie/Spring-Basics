package io.github.janekkodowanie.Notes.Reports;

import io.github.janekkodowanie.Notes.Model.Event.NoteEvent;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Table(name = "note_events")
class PersistedNoteEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long ID;
    Long noteId;
    String name;
    LocalDateTime occurrence;

    public PersistedNoteEvent() {}

    PersistedNoteEvent(NoteEvent source) {
        noteId = source.getNoteId();
        name = source.getClass().getSimpleName();
        occurrence = LocalDateTime.ofInstant(source.getOccurrence(), ZoneId.systemDefault());
    }
}

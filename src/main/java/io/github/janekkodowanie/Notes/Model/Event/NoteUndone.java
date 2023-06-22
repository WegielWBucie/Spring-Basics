package io.github.janekkodowanie.Notes.Model.Event;

import io.github.janekkodowanie.Notes.Model.Note;

import java.time.Clock;

public class NoteUndone extends NoteEvent {
    NoteUndone(Note source) {
        super(source.getID(), Clock.systemDefaultZone());
    }
}

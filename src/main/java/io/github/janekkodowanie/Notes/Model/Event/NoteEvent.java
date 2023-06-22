package io.github.janekkodowanie.Notes.Model.Event;

import java.time.Clock;
import java.time.Instant;

public abstract class NoteEvent {

    private final int noteId;
    private final Instant occurrence;

    NoteEvent(int noteId, Clock clock) {
        this.noteId = noteId;
        this.occurrence = Instant.now();
    }

    public int getNoteId() {
        return noteId;
    }

    public Instant getOccurrence() {
        return occurrence;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "noteId=" + noteId +
                ", occurrence=" + occurrence +
                '}';
    }
}

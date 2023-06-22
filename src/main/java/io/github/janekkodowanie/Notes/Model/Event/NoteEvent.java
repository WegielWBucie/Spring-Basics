package io.github.janekkodowanie.Notes.Model.Event;

import com.sun.source.util.TaskEvent;
import io.github.janekkodowanie.Notes.Model.Note;

import java.time.Clock;
import java.time.Instant;

public abstract class NoteEvent {

    public static NoteEvent changed(Note source) {
        return source.isDone() ? new NoteDone(source) : new NoteUndone(source);
    }

    private final Long noteId;
    private final Instant occurrence;

    NoteEvent(Long noteId, Clock clock) {
        this.noteId = noteId;
        this.occurrence = Instant.now();
    }

    public Long getNoteId() {
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

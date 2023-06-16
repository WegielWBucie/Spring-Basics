package io.github.janekkodowanie.Notes.Model.Projection;

import io.github.janekkodowanie.Notes.Model.Note;
import io.github.janekkodowanie.Notes.Model.NoteGroup;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class GroupReadModel {
    private Long ID;
    private String title;
    private String content;
    private int priority;
    private boolean done;
    /**
     * Expiration of the latest note in group.
     */
    private LocalDateTime expiration;
    private Set<GroupNoteReadModel> notes;

    public GroupReadModel(NoteGroup source) {
        this.ID = source.getID();
        this.title = source.getTitle();
        this.content = source.getContent();
        this.expiration = source.getNotes().stream()
                .map(Note::getExpiration)
                .filter(Predicate.not(Objects::isNull))
                .max(LocalDateTime::compareTo)
                .orElse(null);

        this.done = source.isDone();

        notes = source.getNotes().stream()
                .map(GroupNoteReadModel::new)
                .collect(Collectors.toSet());

        this.priority = source.getNotes().stream()
                .map(Note::getPriority)
                .min(Integer::compareTo).orElseThrow(RuntimeException::new);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(final String content) {
        this.content = content;
    }

    public void setExpiration(final LocalDateTime expiration) {
        this.expiration = expiration;
    }

    public LocalDateTime getExpiration() {
        return expiration;
    }

    public Set<GroupNoteReadModel> getNotes() {
        return notes;
    }

    public void setNotes(final Set<GroupNoteReadModel> notes) {
        this.notes = notes;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}

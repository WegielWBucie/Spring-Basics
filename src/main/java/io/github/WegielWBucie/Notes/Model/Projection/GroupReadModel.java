package io.github.WegielWBucie.Notes.Model.Projection;

import io.github.WegielWBucie.Notes.Model.Note;
import io.github.WegielWBucie.Notes.Model.NoteGroup;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class GroupReadModel {
    private String title;
    private String content;
    private boolean done;
    /**
     * Expiration of the latest note in group.
     */
    private LocalDateTime expiration;
    private Set<GroupNoteReadModel> notes;

    public GroupReadModel(NoteGroup source) {
        this.title = source.getTitle();
        this.content = source.getContent();
        this.expiration = source.getNotes().stream()
                .map(Note::getExpiration)
                .filter(Predicate.not(Objects::isNull))
                .max(LocalDateTime::compareTo)
                .orElse(null);

        notes = source.getNotes().stream()
                .map(GroupNoteReadModel::new)
                .collect(Collectors.toSet());

        this.done = source.getNotes().stream()
                .allMatch(Note::isDone);
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

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}

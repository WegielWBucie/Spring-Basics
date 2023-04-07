package io.github.WegielWBucie.Notes.Model.Projection;

import io.github.WegielWBucie.Notes.Model.Note;
import io.github.WegielWBucie.Notes.Model.NoteGroup;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public class GroupReadModel {
    private String title;
    private String content;
    /**
     * Expiration of the latest note in group.
     */
    private LocalDateTime expiration;
    private Set<GroupNoteReadModel> notes;

    public GroupReadModel(NoteGroup source) {
        this.title = source.getTitle();
        this.content = source.getContent();
        source.getNotes().stream()
                .map(Note::getExpiration)
                .max(LocalDateTime::compareTo)
                .ifPresent(date -> expiration = date);

        notes = source.getNotes().stream()
                .map(GroupNoteReadModel::new)
                .collect(Collectors.toSet());
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
}

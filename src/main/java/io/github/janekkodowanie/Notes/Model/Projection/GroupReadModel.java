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
     * Deadline of the latest note in group.
     */
    private LocalDateTime deadline;
    private Set<NoteReadModel> notes;

    public GroupReadModel(NoteGroup source) {
        this.ID = source.getID();
        this.title = source.getTitle();
        this.content = source.getContent();
        this.deadline = source.getNotes().stream()
                .map(Note::getDeadline)
                .filter(Predicate.not(Objects::isNull))
                .max(LocalDateTime::compareTo)
                .orElse(null);

        this.done = source.isDone();

        notes = source.getNotes().stream()
                .map(NoteReadModel::new)
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

    public void setDeadline(final LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public Set<NoteReadModel> getNotes() {
        return notes;
    }

    public void setNotes(final Set<NoteReadModel> notes) {
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

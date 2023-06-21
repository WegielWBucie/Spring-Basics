package io.github.janekkodowanie.Notes.Model.Projection;

import io.github.janekkodowanie.Notes.Model.Note;

import java.time.LocalDateTime;

public class NoteReadModel {
    private Long ID;
    private String title;
    private String content;
    private int priority;
    private boolean done;
    private LocalDateTime deadline;

    public NoteReadModel(Note source) {
        this.ID = source.getID();
        this.title = source.getTitle();
        this.content = source.getContent();
        this.priority = source.getPriority();
        this.done = source.isDone();
        this.deadline = source.getDeadline();
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

    public int getPriority() {
        return priority;
    }

    public void setPriority(final int priority) {
        this.priority = priority;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }
}

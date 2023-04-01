package io.github.WegielWBucie.Notes.Model.Projection;

import io.github.WegielWBucie.Notes.Model.Note;

public class GroupNoteReadModel {

    private String title;
    private String content;
    private int priority;

    GroupNoteReadModel(Note source) {
        this.title = source.getTitle();
        this.content = source.getContent();
        this.priority = source.getPriority();
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
}

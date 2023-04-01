package io.github.WegielWBucie.Notes.Model.Projection;

import io.github.WegielWBucie.Notes.Model.Note;

import java.time.LocalDateTime;

public class GroupNoteWriteModel {

    /*
        DTO <-> Data Transfer Object.
     */

    private String title;
    private String content;
    private int priority;
    private LocalDateTime expiration;

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

    public LocalDateTime getExpiration() {
        return expiration;
    }

    public void setExpiration(final LocalDateTime expiration) {
        this.expiration = expiration;
    }

    public Note toNote() {
        return new Note(this.title, this.content, this.priority, this.expiration);
    }
}

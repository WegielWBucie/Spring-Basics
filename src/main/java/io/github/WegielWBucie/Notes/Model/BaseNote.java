package io.github.WegielWBucie.Notes.Model;

import javax.persistence.*;

@MappedSuperclass
abstract class BaseNote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    protected String title;
    protected String content;
    protected int priority;

    public Long getId() {
        return id;
    }

    void setId(final Long id) {
        this.id = id;
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

    public void updateFrom(final Note source) {
        if(source.getTitle() != null)
            this.setTitle(source.getTitle());
        if(source.getContent() != null)
            this.setContent(source.getContent());
        if(source.getPriority() != 0)
            this.setPriority(source.getPriority());
    }
}

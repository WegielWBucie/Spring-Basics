package io.github.WegielWBucie.Notes.Model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "notes")
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @NotBlank(message = "Note content must not be empty or blank.")
    private String content;

    private Long priority;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    void setTitle(final String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    void setContent(final String content) {
        this.content = content;
    }

    public Long getPriority() {
        return priority;
    }

    void setPriority(final Long priority) {
        this.priority = priority;
    }
}

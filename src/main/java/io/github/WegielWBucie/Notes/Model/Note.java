package io.github.WegielWBucie.Notes.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "notes")
class Note {

    @Id
    private Long id;

    private String title;

    @NotBlank(message = "Note content must not be empty or blank.")
    private String content;

    @NotBlank(message = "Note content must not be null.")
    private Long priority;

    Long getId() {
        return id;
    }

    void setId(final Long id) {
        this.id = id;
    }

    String getTitle() {
        return title;
    }

    void setTitle(final String title) {
        this.title = title;
    }

    String getContent() {
        return content;
    }

    void setContent(final String content) {
        this.content = content;
    }

    Long getPriority() {
        return priority;
    }

    void setPriority(final Long priority) {
        this.priority = priority;
    }
}

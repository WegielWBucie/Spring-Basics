package io.github.WegielWBucie.Notes.Model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "NOTES_GROUPS")
public class NoteGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private int priority;

    @Embedded
//    @AttributeOverrides({
//                    @AttributeOverride(column = @Column(name = "updatedOn"), name = "updatedOn")
//            }
//    )
    private Audit audit = new Audit();


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

}

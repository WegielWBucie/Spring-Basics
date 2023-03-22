package io.github.WegielWBucie.Notes.Model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "NOTES")
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private int priority;
    private LocalDateTime expiration;

    @Embedded
//    @AttributeOverrides({
//                    @AttributeOverride(column = @Column(name = "updatedOn"), name = "updatedOn")
//            }
//    )
    private Audit audit = new Audit();

    @ManyToOne
    @JoinColumn(name = "NOTE_GROUP_ID")
    private NoteGroup group;

    NoteGroup getGroup() {
        return group;
    }

    void setGroup(NoteGroup noteGroup) {
        this.group = noteGroup;
    }

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

    public LocalDateTime getExpiration() {
        return expiration;
    }

    public void setExpiration(final LocalDateTime expiration) {
        this.expiration = expiration;
    }

    public void updateFrom(final Note source) {
        if(source.getTitle() != null)
            this.title = source.getTitle();
        if(source.getContent() != null)
            this.content = source.getContent();
        if(source.getPriority() != 0)
            this.priority = source.getPriority();
        if(source.getExpiration() != null)
            this.expiration = source.getExpiration();
        if(source.getGroup() != null)
            this.group = source.getGroup();
    }
}

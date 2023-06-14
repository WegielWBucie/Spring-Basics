package io.github.WegielWBucie.Notes.Model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "NOTES")
public class Note extends BaseNote {

    private LocalDateTime expiration;

    private boolean done;

    @ManyToOne
    @JoinColumn(name = "NOTE_GROUP_ID")
    private NoteGroup group;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(column = @Column(name = "updatedOn"), name = "updatedOn"),
            @AttributeOverride(column = @Column(name = "updatedOn"), name = "updatedOn")
        }
    )
    private final Audit audit = new Audit();

    public Note() {}

    public Note(String title, String content, int priority, LocalDateTime expiration) {
        this(title, content, priority, expiration, null);
    }

    public Note(String title, String content, int priority, LocalDateTime expiration, NoteGroup group) {
        this.title = title;
        this.content = content;
        this.priority = priority;
        this.expiration = expiration;
        if(group != null) {
            this.group = group;
        }
    }

    public LocalDateTime getExpiration() {
        return expiration;
    }
    public void setExpiration(final LocalDateTime expiration) {
        this.expiration = expiration;
    }

    NoteGroup getGroup() {
        return group;
    }

    void setGroup(NoteGroup noteGroup) {
        this.group = noteGroup;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public void updateFrom(final Note source) {
        super.updateFrom(source);
        if(source.getExpiration() != null)
            this.setExpiration(source.getExpiration());
        if(source.getGroup() != null)
            this.setGroup(source.getGroup());
    }
}

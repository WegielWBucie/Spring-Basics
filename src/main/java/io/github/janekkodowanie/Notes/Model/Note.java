package io.github.janekkodowanie.Notes.Model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "NOTES")
public class Note extends BaseNote {

    private LocalDateTime deadline;


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

    public Note(String title, String content, int priority, LocalDateTime deadline) {
        this(title, content, priority, deadline, null);
    }

    public Note(String title, String content, int priority, LocalDateTime deadline, NoteGroup group) {
        this(title, content, priority, deadline, group, false);
    }

    public Note(String title, String content, int priority, LocalDateTime deadline, NoteGroup group, boolean done) {
        this.title = title;
        this.content = content;
        this.priority = priority;
        this.deadline = deadline;
        if(group != null) {
            this.group = group;
        }
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }
    public void setDeadline(final LocalDateTime expiration) {
        this.deadline = expiration;
    }

    NoteGroup getGroup() {
        return group;
    }

    void setGroup(NoteGroup noteGroup) {
        this.group = noteGroup;
    }

    @Override
    public void updateFrom(final Note source) {
        super.updateFrom(source);
        if(source.getDeadline() != null)
            this.setDeadline(source.getDeadline());
        if(source.getGroup() != null)
            this.setGroup(source.getGroup());
    }
}

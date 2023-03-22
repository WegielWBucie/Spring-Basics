package io.github.WegielWBucie.Notes.Model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "NOTE_GROUPS")
public class NoteGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private int priority;

//    @Embedded
//    @AttributeOverrides({
//                    @AttributeOverride(column = @Column(name = "updatedOn"), name = "updatedOn")
//            }
//    )
//    private Audit audit = new Audit();

    @OneToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "group"
    )
    private Set<Note> notes;

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

    public Set<Note> getNotes() {
        return notes;
    }

    void setNotes(final Set<Note> notes) {
        this.notes = notes;
    }
}

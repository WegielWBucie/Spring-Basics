package io.github.WegielWBucie.Notes.Model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "NOTE_GROUPS")
public class NoteGroup extends BaseNote {

    @OneToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            mappedBy = "group"
    )
    private Set<Note> notes;

    @ManyToOne
    @JoinColumn(name = "PROJECT_ID")
    private Project project;

    public NoteGroup() {}

    public Set<Note> getNotes() {
        return notes;
    }

    void setProject(final Project project) {
        this.project = project;
    }

    Project getProject() {
        return project;
    }

    public void setNotes(final Set<Note> notes) {
        this.notes = notes;
    }
}

package io.github.WegielWBucie.Notes.Model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "PROJECTS")
public class Project {

    @OneToMany(mappedBy = "project")
    private Set<NoteGroup> groups;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
    private Set<ProjectStep> steps;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long ID;
    protected String title;
    protected String content;

    Project() {}

    Set<NoteGroup> getGroups() {
        return groups;
    }

    public Set<ProjectStep> getSteps() {
        /*
            Deep copy of steps should be returned,
            TODO;
         */
        return steps;
    }

    void setSteps(final Set<ProjectStep> steps) {
        this.steps = steps;
    }

    void setGroups(final Set<NoteGroup> groups) {
        this.groups = groups;
    }

    public Long getID() {
        return ID;
    }

    void setID(final Long ID) {
        this.ID = ID;
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


}

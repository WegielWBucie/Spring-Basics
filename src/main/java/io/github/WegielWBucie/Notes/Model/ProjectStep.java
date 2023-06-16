package io.github.WegielWBucie.Notes.Model;

import javax.persistence.*;

@Entity
@Table(name = "PROJECT_STEPS")
public class ProjectStep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;
    private String title;
    private String content;

    private int daysToDeadline;

    @ManyToOne
    @JoinColumn(name = "PROJECT_ID")
    private Project project;

    public Long getID() {
        return ID;
    }

    public void setID(final Long ID) {
        this.ID = ID;
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

    Project getProject() {
        return project;
    }

    public void setProject(final Project project) {
        this.project = project;
    }

    public int getDaysToDeadline() {
        return daysToDeadline;
    }

    public void setDaysToDeadline(int daysToDeadline) {
        this.daysToDeadline = daysToDeadline;
    }
}

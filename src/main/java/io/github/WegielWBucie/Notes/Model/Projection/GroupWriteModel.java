package io.github.WegielWBucie.Notes.Model.Projection;

import io.github.WegielWBucie.Notes.Model.NoteGroup;
import io.github.WegielWBucie.Notes.Model.Project;

import java.util.Set;
import java.util.stream.Collectors;

public class GroupWriteModel {
    private String title;
    private String content;

    private Set<GroupNoteWriteModel> notes;

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

    public Set<GroupNoteWriteModel> getNotes() {
        return notes;
    }

    public void setNotes(final Set<GroupNoteWriteModel> notes) {
        this.notes = notes;
    }

    public NoteGroup toGroup(Project project) {
        NoteGroup result = new NoteGroup();
        result.setTitle(this.title);
        result.setContent(this.content);
        result.setNotes(
                notes.stream()
                        .map(source -> source.toNote(result))
                        .collect(Collectors.toSet())
        );
        result.setProject(project);
        return result;
    }

}

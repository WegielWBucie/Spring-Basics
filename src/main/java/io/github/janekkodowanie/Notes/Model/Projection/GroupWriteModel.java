package io.github.janekkodowanie.Notes.Model.Projection;

import io.github.janekkodowanie.Notes.Model.NoteGroup;
import io.github.janekkodowanie.Notes.Model.Project;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GroupWriteModel {
    @NotBlank(message = "Group's title must not be empty")
    private String title;
    @NotBlank(message = "Group's content must not be empty")
    private String content;

    @Valid
    private List<NoteWriteModel> notes = new ArrayList<>();

    public GroupWriteModel() {
        this.notes.add(new NoteWriteModel());
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

    public List<NoteWriteModel> getNotes() {
        return notes;
    }

    public void setNotes(final List<NoteWriteModel> notes) {
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

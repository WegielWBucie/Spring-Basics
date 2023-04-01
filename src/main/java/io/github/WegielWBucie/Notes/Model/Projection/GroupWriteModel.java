package io.github.WegielWBucie.Notes.Model.Projection;

import io.github.WegielWBucie.Notes.Model.NoteGroup;

import java.util.Set;
import java.util.stream.Collectors;

class GroupWriteModel {
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

    public NoteGroup toGroup() {
        NoteGroup result = new NoteGroup();
        result.setTitle(this.title);
        result.setNotes(
                notes.stream()
                        .map(GroupNoteWriteModel::toNote)
                        .collect(Collectors.toSet())
        );
        return result;
    }

}

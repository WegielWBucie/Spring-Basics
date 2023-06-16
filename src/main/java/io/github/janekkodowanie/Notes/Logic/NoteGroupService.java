package io.github.janekkodowanie.Notes.Logic;

import io.github.janekkodowanie.Notes.Model.NoteGroup;
import io.github.janekkodowanie.Notes.Model.NoteGroupRepository;
import io.github.janekkodowanie.Notes.Model.NoteRepository;
import io.github.janekkodowanie.Notes.Model.Project;
import io.github.janekkodowanie.Notes.Model.Projection.GroupReadModel;
import io.github.janekkodowanie.Notes.Model.Projection.GroupWriteModel;

import java.util.List;
import java.util.stream.Collectors;

public class NoteGroupService {
    /**
     *    Intermediate level between
     *    repository & controller.
     */
    private final NoteGroupRepository noteGroupRepository;
    private final NoteRepository noteRepository;

    NoteGroupService(final NoteGroupRepository repository, final NoteRepository noteRepository) {
        this.noteGroupRepository = repository;
        this.noteRepository = noteRepository;
    }

    public GroupReadModel createGroup(final GroupWriteModel source) {
        return createGroup(source, null);
    }

    public GroupReadModel createGroup(GroupWriteModel source, Project project) {
        NoteGroup result = noteGroupRepository.save(source.toGroup(project));
        return new GroupReadModel(result);
    }


    public List<GroupReadModel> readAll() {
        return noteGroupRepository.findAll().stream()
                .map(GroupReadModel::new)
                .collect(Collectors.toList());
    }

//    @Transactional
    public void toggleGroup(final Long groupID) {
        if(noteRepository.existsByDoneIsFalseAndGroup_ID(groupID)) {
            throw new IllegalStateException("Group has undone tasks. Do all the tasks first");
        }

        NoteGroup result = noteGroupRepository.findById(groupID)
                .orElseThrow(() -> new IllegalArgumentException("NoteGroup with given id not found"));
        result.setDone(!result.isDone());
        noteGroupRepository.save(result);
    }


}

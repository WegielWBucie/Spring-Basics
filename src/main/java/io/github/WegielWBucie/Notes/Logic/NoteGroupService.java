package io.github.WegielWBucie.Notes.Logic;

import io.github.WegielWBucie.Notes.Model.NoteGroup;
import io.github.WegielWBucie.Notes.Model.NoteGroupRepository;
import io.github.WegielWBucie.Notes.Model.NoteRepository;
import io.github.WegielWBucie.Notes.Model.Projection.GroupReadModel;
import io.github.WegielWBucie.Notes.Model.Projection.GroupWriteModel;

import java.util.List;
import java.util.stream.Collectors;

public class NoteGroupService {
    /**
     *    Intermediate level between
     *    repository & controller.
     */
    private final NoteGroupRepository noteGroupRepository;

    NoteGroupService(final NoteGroupRepository repository, final NoteRepository noteRepository) {
        this.noteGroupRepository = repository;
    }

    public GroupReadModel createGroup(final GroupWriteModel source) {
        NoteGroup result = noteGroupRepository.save(source.toGroup());
        return new GroupReadModel(result);
    }

    public List<GroupReadModel> readAll() {
        return noteGroupRepository.findAll().stream()
                .map(GroupReadModel::new)
                .collect(Collectors.toList());
    }

//    @Transactional
    public void toggleGroup(final Long groupID) {
        NoteGroup result = noteGroupRepository.findByID(groupID)
                .orElseThrow(() -> new IllegalArgumentException("NoteGroup with given ID not found."));
        result.setTitle("<Closed> " + result.getTitle());
        noteGroupRepository.save(result);
    }

}

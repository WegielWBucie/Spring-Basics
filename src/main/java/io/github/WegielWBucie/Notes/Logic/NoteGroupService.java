package io.github.WegielWBucie.Notes.Logic;

import io.github.WegielWBucie.Notes.Model.NoteGroup;
import io.github.WegielWBucie.Notes.Model.NoteGroupRepository;
import io.github.WegielWBucie.Notes.Model.NoteRepository;
import io.github.WegielWBucie.Notes.Model.Projection.GroupReadModel;
import io.github.WegielWBucie.Notes.Model.Projection.GroupWriteModel;
import io.github.WegielWBucie.Notes.NoteConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequestScope
public class NoteGroupService {
    /**
     *    Intermediate level between
     *    repository & controller.
     */
    private NoteGroupRepository noteGroupRepository;
    private NoteRepository noteRepository;

    NoteGroupService(final NoteGroupRepository repository) {
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

    @Transactional
    public void toggleGroup(final Long groupID) {
        NoteGroup result = noteGroupRepository.findByID(groupID)
                .orElseThrow(() -> new IllegalArgumentException("NoteGroup with given ID not found."));
        result.setTitle("<Closed> " + result.getTitle());
//        noteGroupRepository.save(result);
    }

}

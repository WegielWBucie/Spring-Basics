package io.github.WegielWBucie.Notes.Logic;

import io.github.WegielWBucie.Notes.Model.NoteGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
class TempService {
    @Autowired
    List<String> temp(NoteGroupRepository repository) {
//        FIXME
        return repository.findAll().stream()
                .flatMap(noteGroup -> noteGroup.getNotes().stream())
                .map(note -> note.getTitle())
                .collect(Collectors.toList());
    }
}

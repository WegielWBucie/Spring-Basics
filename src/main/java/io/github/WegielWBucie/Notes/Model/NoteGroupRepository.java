package io.github.WegielWBucie.Notes.Model;

import java.util.List;
import java.util.Optional;

public interface NoteGroupRepository {
    List<NoteGroup> findAll();
    Optional<NoteGroup> findByID(Long ID);

    NoteGroup save(NoteGroup entity);

    boolean existsByProjectID(Long projectID);
}

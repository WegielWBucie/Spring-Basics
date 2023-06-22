package io.github.janekkodowanie.Notes.Model;

import java.util.List;
import java.util.Optional;

public interface NoteGroupRepository {
    List<NoteGroup> findAll();
    Optional<NoteGroup> findById(Long ID);

    NoteGroup save(NoteGroup entity);

    boolean existsByTitle(String title);

    boolean existsByProjectID(Long projectID);
}

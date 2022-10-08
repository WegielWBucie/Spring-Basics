package io.github.WegielWBucie.Notes.Model;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface NoteRepository {

    List<Note> findAll();

    Page<Note> findAll(Pageable pageable);

    Optional<Note> findById(Long ID);

    boolean existsById(Long ID);

    Note save(Note entity);

    void deleteById(Long ID);


}

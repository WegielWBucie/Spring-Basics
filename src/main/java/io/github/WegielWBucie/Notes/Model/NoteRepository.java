package io.github.WegielWBucie.Notes.Model;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface NoteRepository {

    List<Note> findAll();

    Page<Note> findAll(Pageable pageable);

    Optional<Note> findById(Long ID);

    List<Note> findByDone(boolean done);

    List<Note> findByPriority(Long priority);

    boolean existsById(Long ID);

    boolean existsByDoneIsFalseAndGroup_ID(Long ID);

    Note save(Note entity);

    void deleteById(Long ID);

    List<Note> findAllByGroup_ID(Long ID);
}

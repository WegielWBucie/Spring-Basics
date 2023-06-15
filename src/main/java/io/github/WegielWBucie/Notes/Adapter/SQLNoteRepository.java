package io.github.WegielWBucie.Notes.Adapter;

import io.github.WegielWBucie.Notes.Model.Note;
import io.github.WegielWBucie.Notes.Model.NoteRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
interface SQLNoteRepository extends NoteRepository, JpaRepository<Note, Long> {

//    List<Note> findByPriorityOrderByPriority(@Param("priority") final int priority);

    @Override
    @Query(nativeQuery = true, value = "SELECT COUNT (*) > 0 FROM NOTES WHERE ID=:ID")
    boolean existsById(@Param("ID") Long ID);

    @Override
    List<Note> findAllByGroup_ID(Long ID);

}

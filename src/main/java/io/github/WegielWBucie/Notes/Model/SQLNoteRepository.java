package io.github.WegielWBucie.Notes.Model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;


@RepositoryRestResource(path = "notes")
interface SQLNoteRepository extends NoteRepository, JpaRepository<Note, Long> {

    List<Note> findByPriorityOrderByPriority(@Param("priority") final Long priority);

    @Override
    @Query(nativeQuery = true, value = "SELECT COUNT (*) > 0 FROM NOTES WHERE ID=:ID")
    boolean existsById(@Param("ID") Long ID);
}

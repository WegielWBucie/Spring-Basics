package io.github.janekkodowanie.Notes.Reports;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface PersistedNoteEventRepository extends JpaRepository<PersistedNoteEvent, Long> {
    List<PersistedNoteEvent> findByNoteId(Long noteId);
}

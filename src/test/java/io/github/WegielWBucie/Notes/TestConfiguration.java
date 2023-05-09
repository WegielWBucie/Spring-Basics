package io.github.WegielWBucie.Notes;

import io.github.WegielWBucie.Notes.Model.Note;
import io.github.WegielWBucie.Notes.Model.NoteRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.*;

@Configuration
class TestConfiguration {
    @Bean
    @Primary
    @Profile({"integration"})
    NoteRepository testRepo() {
        return new NoteRepository() {
            private Map<Long, Note> notes = new HashMap<>();


            @Override
            public List<Note> findAll() {
                return new ArrayList<>(notes.values());
            }

            @Override
            public Page<Note> findAll(final Pageable pageable) {
                return null;
            }

            @Override
            public Optional<Note> findById(final Long ID) {
                return Optional.ofNullable(notes.get(ID));
            }

            @Override
            public List<Note> findByPriority(final Long priority) {
                return null;
            }

            @Override
            public boolean existsById(final Long ID) {
                return notes.containsKey(ID);
            }

            @Override
            public Note save(final Note entity) {
                return notes.put((long)(notes.size() + 1), entity);
            }

            @Override
            public void deleteById(final Long ID) {

            }
        };
    }
}

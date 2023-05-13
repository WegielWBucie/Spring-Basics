package io.github.WegielWBucie.Notes;

import io.github.WegielWBucie.Notes.Model.BaseNote;
import io.github.WegielWBucie.Notes.Model.Note;
import io.github.WegielWBucie.Notes.Model.NoteRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.util.*;

@Configuration
class TestConfiguration {
    @Bean
    @Primary
    @Profile("!integration")
    DataSource e2eTestDataSource() {
        var result = new DriverManagerDataSource("jdbc:h2:mem:e2eTest;DB_CLOSE_DELAY=-1", "sa", "");
        result.setDriverClassName("org.h2.Driver");
        return result;
    }


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
                Long key = (long) (notes.size() + 1);
                try {
                    Field idField = BaseNote.class.getDeclaredField("ID");
                    idField.setAccessible(true);
                    idField.set(entity, key);
                } catch(NoSuchFieldException | IllegalAccessException e) {
                    e.printStackTrace();
                }

                notes.put(key, entity);
                return notes.get(key);
            }

            @Override
            public void deleteById(final Long ID) {

            }
        };
    }
}

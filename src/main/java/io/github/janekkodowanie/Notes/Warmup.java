package io.github.janekkodowanie.Notes;

import io.github.janekkodowanie.Notes.Model.Note;
import io.github.janekkodowanie.Notes.Model.NoteGroup;
import io.github.janekkodowanie.Notes.Model.NoteGroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class Warmup implements ApplicationListener<ContextRefreshedEvent> {

    private final NoteGroupRepository noteGroupRepository;

    private static final Logger logger = LoggerFactory.getLogger(Warmup.class);

    public Warmup(NoteGroupRepository noteGroupRepository) {
        this.noteGroupRepository = noteGroupRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        logger.info("Application warmup after context refreshed");

        String title = "ApplicationContextEvent";
        if(!noteGroupRepository.existsByTitle(title)) {
            logger.info("No required group found! Adding it!");
            var group = new NoteGroup();
            group.setTitle(title);
            group.setContent("Warmup.");

            group.setNotes(Set.of(
                    new Note("ContexRefreshedEvent", "", 0, null, group),
                    new Note("ContextClosedEvent", "", 0, null, group),
                    new Note("ContextStoppedEvent", "", 0, null, group),
                    new Note("ContextStartedEvent", "", 0, null, group)
            ));

            noteGroupRepository.save(group);
        }
    }


}

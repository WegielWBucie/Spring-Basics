package io.github.janekkodowanie.Notes.Reports;

import io.github.janekkodowanie.Notes.Model.Event.NoteDone;
import io.github.janekkodowanie.Notes.Model.Event.NoteEvent;
import io.github.janekkodowanie.Notes.Model.Event.NoteUndone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
class ChangedNoteEventListener {

    private static final Logger logger = LoggerFactory.getLogger(ChangedNoteEventListener.class);

    private final PersistedNoteEventRepository repository;

    ChangedNoteEventListener(PersistedNoteEventRepository repository) {
        this.repository = repository;
    }

    @EventListener
    public void on(NoteDone event) {
        onChanged(event);
    }

    @EventListener
    public void on(NoteUndone event) {
        onChanged(event);
    }

    private void onChanged(final NoteEvent event) {
        logger.info("Got " + event);
        repository.save(new PersistedNoteEvent(event));
    }


}

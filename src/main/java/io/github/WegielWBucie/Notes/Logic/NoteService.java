package io.github.WegielWBucie.Notes.Logic;

import io.github.WegielWBucie.Notes.Model.Note;
import io.github.WegielWBucie.Notes.Model.NoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class NoteService {
    private static final Logger logger = LoggerFactory.getLogger(NoteService.class);
    private final NoteRepository noteRepository;

    NoteService(final NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Async
    public CompletableFuture<List<Note>> findAllAsync() {
        logger.info("Supply Async.");
        return CompletableFuture.supplyAsync(noteRepository::findAll);
    }

}

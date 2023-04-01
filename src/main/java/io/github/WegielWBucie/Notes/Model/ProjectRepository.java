package io.github.WegielWBucie.Notes.Model;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository {
    List<Project> findAll();
    Optional<Project> findByID(Long ID);

    Project save(Project entity);
}

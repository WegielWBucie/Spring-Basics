package io.github.WegielWBucie.Notes.Adapter;

import io.github.WegielWBucie.Notes.Model.Project;
import io.github.WegielWBucie.Notes.Model.ProjectRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface SQLProjectRepository extends ProjectRepository, JpaRepository<Project, Long> {
    @Override
    @Query("")
    List<Project> findAll();
}

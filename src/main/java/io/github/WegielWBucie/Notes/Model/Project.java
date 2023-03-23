package io.github.WegielWBucie.Notes.Model;

import javax.persistence.*;

@Entity
@Table(name = "PROJECTS")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long ID;
    protected String title;
    protected String content;
}

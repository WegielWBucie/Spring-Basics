package io.github.WegielWBucie.Notes.Model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

class ProjectSteps {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long ID;
    protected String title;
    protected String content;

    @Column(name = "PROJECT_ID")
    protected Long projectID;
}

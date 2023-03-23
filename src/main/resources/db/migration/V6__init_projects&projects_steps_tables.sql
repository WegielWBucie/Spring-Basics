create table PROJECTS(
    id long primary key auto_increment,
    title varchar(100) not null,
    content varchar(100) not null
);
alter table NOTE_GROUPS add column project_id long null;
alter table NOTE_GROUPS
    add foreign key (project_id) references PROJECTS (id);

create table PROJECT_STEPS(
    id long primary key auto_increment,
    title varchar(100) not null,
    content varchar(100) not null,
    project_id long null
);
alter table PROJECT_STEPS
    add foreign key (project_id) references PROJECTS (id);
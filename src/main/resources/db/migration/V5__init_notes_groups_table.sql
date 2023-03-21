create table NOTE_GROUPS(
    id long primary key auto_increment,
    title varchar(100) not null,
    content varchar(100) not null,
    priority int
);
alter table NOTES add column note_group_id int null;
alter table NOTES
    add foreign key (note_group_id) references NOTE_GROUPS (id);

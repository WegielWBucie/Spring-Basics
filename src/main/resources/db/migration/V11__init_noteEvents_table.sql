drop table if exists NOTE_EVENTS;
create table NOTE_EVENTS(
    id bigint primary key auto_increment,
    note_id bigint,
    occurrence datetime,
    name varchar(30)
)
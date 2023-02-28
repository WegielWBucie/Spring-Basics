drop table if exists NOTES;
create table NOTES(
    id long primary key auto_increment,
    title varchar(100) not null,
    content varchar(100) not null,
    priority int
)
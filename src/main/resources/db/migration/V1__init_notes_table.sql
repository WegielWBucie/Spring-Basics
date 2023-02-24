drop table if exists notes;
create table notes(
    id int primary key auto_increment,
    title varchar(100) not null,
    content varchar(100) not null,
    priority int
)
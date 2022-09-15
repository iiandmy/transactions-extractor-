create table if not exists files
(
    id varchar(40) NOT NULL,
    name varchar(40) not null,
    contentType varchar(40) not null,
    size bigint not null,
    constraint files_pk primary key (id)
);


create table if not exists operator
(
    id varchar(40) NOT NULL,
    login varchar(40) not null,
    password varchar(40) not null,
    permission varchar(40) not null,
    constraint operator_pk primary key (id)
);

alter table transactions
    add column operator_id varchar(40);

alter table transactions
    add constraint operator_id foreign key (operator_id)
    references operator (id) match simple
    on update cascade
    on delete no action;

insert into operator (ID, login, password, permission) values ('aaaaaaaa-1111-0000-111a-aaa0000a1a11', 'test', 'password', '')
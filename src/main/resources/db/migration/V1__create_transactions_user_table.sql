create table if not exists customer
(
    id varchar(40) not null,
    first_name varchar(40),
    last_name varchar(40),
    email varchar(40),
    constraint user_pkey primary key (id)
    );

create table if not exists transactions
(
    id varchar(40) not null,
    constraint transactions_pkey
    primary key (id),
    user_id varchar(40) not null,
    time_date varchar(40) not null,
    amount bigint not null,
    currency varchar(10) not null,
    status boolean not null,

    constraint user_id foreign key (user_id)
    references customer (id) match simple
    on update cascade
    on delete no action
    );


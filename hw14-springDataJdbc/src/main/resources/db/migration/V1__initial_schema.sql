create table address
(
    id          bigserial       not null primary key,
    street      varchar(255)    not null
);


create table client
(
    id         bigserial        not null primary key,
    name       varchar(50)      not null,
    address_id bigint
        constraint fk_client_address references address(id)
);

create table phone
(
    id          bigserial       not null primary key,
    number      varchar(50)     not null,
    client_id   bigint
        constraint fk_client_phone references client(id)
);
-- create user table
create table "user" (
    id BIGSERIAL primary key,
    username varchar(100) not null,
    password varchar(100) not null,
    email varchar(255) not null,
    created_at TIMESTAMP not null
);

-- create role table
create table role (
    id SERIAL primary key,
    name varchar(100) not null
);

-- create user_detail
create table user_detail (
    id BIGSERIAL primary key,
    first_name varchar (100) not null,
    last_name varchar (100) not null,
    age int not null,
    birth_day DATE not null,
    user_id int not null
);
alter table user_detail add constraint fk_user_detail_ref_user foreign key (user_id)
    references "user" (id) on delete restrict on update restrict;

-- create table user_role
create table user_role (
    id serial primary key,
    active boolean not null,
    created_at timestamp not null,
    user_id integer,
    role_id integer
);

alter table user_role add constraint fk_user_role_ref_user foreign key (user_id)
    references "user" (id) on delete restrict on update restrict;

alter table user_role add constraint fk_user_role_ref_role foreign key (role_id)
    references role (id) on delete restrict on update restrict;



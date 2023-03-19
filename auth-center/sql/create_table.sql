create table system_permission
(
    id             int auto_increment
        primary key,
    isDel          tinyint      null,
    permissionName varchar(255) null
);

create table system_role
(
    id       int auto_increment
        primary key,
    isDel    tinyint      null,
    roleName varchar(255) null
);

create table system_role_permission
(
    role_id       int not null,
    permission_id int not null,
    constraint FK801uq3st7cvfp8blsb7vfl4fk
        foreign key (permission_id) references system_permission (id),
    constraint FKp1u97cwl4kh8wboa58dyippik
        foreign key (role_id) references system_role (id)
);

create table system_user
(
    id                    bigint auto_increment
        primary key,
    accountNonExpired     tinyint      null,
    accountNonLocked      tinyint      null,
    addr                  varchar(255) null,
    age                   int          null,
    card                  varchar(255) null,
    createTime            datetime(6)  null,
    credentialsNonExpired tinyint      null,
    email                 varchar(255) null,
    enabled               tinyint      null,
    isDel                 tinyint      null,
    married               tinyint      null,
    password              varchar(255) null,
    sex                   varchar(255) null,
    tel                   varchar(255) null,
    username              varchar(255) null
);

create table system_user_role
(
    user_id bigint not null,
    role_id int    not null,
    constraint FKkc6ik04bm9v9kldgbt3kkgfac
        foreign key (user_id) references system_user (id),
    constraint FKnp61n3syn415rmbwvhnw87u3a
        foreign key (role_id) references system_role (id)
);


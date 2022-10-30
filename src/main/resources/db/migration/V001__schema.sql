drop table if exists t_order_coffee;
drop table if exists t_coffee;
drop table if exists t_order;
drop table if exists t_user;

create table t_order_coffee
(
    coffee_order_id bigint not null,
    items_id        bigint not null
);

create table t_coffee
(
    id          bigint auto_increment,
    create_time timestamp,
    update_time timestamp,
    name        varchar(255),
    price       bigint,
    primary key (id)
);

create table t_order
(
    id          bigint auto_increment,
    create_time timestamp,
    update_time timestamp,
    customer    varchar(255),
    waiter      varchar(255),
    barista     varchar(255),
    discount    integer,
    total       bigint,
    state       integer,
    primary key (id)
);

create table t_user
(
    id                bigint not null,
    email             varchar(255),
    name              varchar(255),
    registration_date datetime(6),
    primary key (id)
);

alter table t_order_coffee
    add constraint fk_coffee_id foreign key (items_id) references t_coffee (id);

alter table t_order_coffee
    add constraint fk_order_id foreign key (coffee_order_id) references t_order (id);
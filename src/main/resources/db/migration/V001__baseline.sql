create table pigs
(
    id         bigserial
        primary key,
    created_at timestamp with time zone,
    updated_at timestamp with time zone,
    deleted_at timestamp with time zone,
    weight     bigint,
    name       text,
    last_grow  timestamp with time zone,
    user_id    bigint
);

create index idx_pigs_deleted_at
    on pigs (deleted_at);

create table users
(
    id          bigserial
        primary key,
    created_at  timestamp with time zone,
    updated_at  timestamp with time zone,
    deleted_at  timestamp with time zone,
    telegram_id bigint,
    name        text,
    wins_count  bigint
);

create index idx_users_deleted_at
    on users (deleted_at);


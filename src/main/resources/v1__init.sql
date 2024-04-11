create table if not exists users
(
    spotify_id           varchar(50) not null primary key,
    user_name            varchar(50),
    country              char(2),
    is_explicit_filtered boolean,
    avatar_url           varchar(100),
    product              varchar(20)
);
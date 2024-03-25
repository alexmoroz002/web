create table if not exists users
(
    id bigserial primary key not null,
    spotify_id varchar(50) not null,
    name varchar(50),
    country char(2),
    is_explicit bool,
    avatar_url varchar(100),
    product varchar(20),
    access_token varchar(256),
    refresh_token varchar(256)
)
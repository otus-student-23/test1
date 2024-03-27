drop table if exists book_comment;
drop table if exists book;
drop table if exists author;
drop table if exists genre;
drop table if exists users;

create table if not exists author(
    id uuid primary key,
    name varchar(100) not null,
    constraint key_author_name unique (name)
);

create table if not exists genre(
    id uuid primary key,
    name varchar(100) not null,
    constraint key_genre_name unique (name)
);

create table if not exists book(
    id uuid primary key,
    name varchar(100) not null,
    author_id uuid not null references author(id),
    genre_id uuid not null references genre(id),
    unique (name, author_id)
);

create table if not exists book_comment(
    id uuid primary key,
    book_id uuid not null references book(id),
    comment varchar(255) not null
);

create table if not exists users (
    id uuid primary key,
    username varchar(100) not null,
    password varchar(255) not null,
    constraint key_username unique (username)
);
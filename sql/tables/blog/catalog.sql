drop table if exists blog.catalog;

create table blog.catalog
(
    id        bigserial primary key,
    "tieuDe"  varchar(500),
    url       varchar(1000),
    "ngayTao" timestamp default now()
)
drop table if exists tham_so;

create table tham_so
(
    id           bigserial    not null primary key,
    khoa         varchar(100) not null,
    "kieuDuLieu" varchar(100) not null default 'String',
    "giaTri"     varchar(500),
    "isEnable"   boolean               default false
)
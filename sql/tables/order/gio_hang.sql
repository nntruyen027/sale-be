drop table if exists "order".gio_hang;

create table "order".gio_hang
(
    id       bigserial primary key,
    "userId" bigint unique references auth.users (id) on delete cascade
)
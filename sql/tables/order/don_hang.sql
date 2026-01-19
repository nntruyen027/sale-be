drop table if exists "order".don_hang;

create table "order".don_hang
(
    id               bigserial primary key,
    "userId"         bigint references auth.users (id),
    "diaChiSnapshot" jsonb,
    "tongTien"       numeric(12, 2),
    "trangThai"      varchar(50),
    "ngayTao"        timestamp default now()
)
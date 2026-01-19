drop table if exists "order".don_hang_ct;

create table "order".don_hang_ct
(
    id                 bigserial primary key,
    "donHangId"        bigint references "order".don_hang (id) on delete cascade,
    "bienThenSnapshot" jsonb,
    gia                numeric(12, 2),
    "soLuong"          int
)
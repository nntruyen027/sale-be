drop table if exists "order".gio_hang_ct;

create table "order".gio_hang_ct
(
    id          bigserial primary key,
    "gioHangId" bigint references "order".gio_hang (id) on delete cascade,
    "bienTheId" bigint references product.bien_the (id) on delete cascade,
    "soLuong"   int not null default 1,
    unique ("gioHangId", "bienTheId")
)
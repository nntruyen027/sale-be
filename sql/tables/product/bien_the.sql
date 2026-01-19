drop table if exists product.bien_the;

create table product.bien_the
(
    id          bigserial primary key,
    "sanPhamId" bigint references product.san_pham (id) on delete cascade,
    sku         varchar(100) unique,
    "hinhAnh"   varchar(500),
    "mauSac"    varchar(50),
    "kichCo"    varchar(50),
    gia         numeric(12, 2) not null,
    "tonKho"    int            not null default 0
)


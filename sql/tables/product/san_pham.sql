drop table if exists product.san_pham;

create table product.san_pham
(
    id        bigserial primary key,
    "loaiId"  bigint references product.loai (id),
    ten       varchar(255) not null,
    "hinhAnh" varchar(500),
    "moTa"    text,
    "ngayTao" timestamp default now()
)
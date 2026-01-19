drop table if exists product.loai;

create table product.loai
(
    id         bigserial primary key,
    ten        varchar(255) not null,
    "parentId" bigint references product.loai
);
drop table if exists config.thong_tin_he_thong;

create table config.thong_tin_he_thong
(
    id          bigserial primary key,
    "ten"       varchar(500),
    "hinhAnh"   varchar(500),
    url         varchar(500),
    "noiDung"   text,
    "thuTu"     int unique,
    "laMacDinh" boolean default false
);

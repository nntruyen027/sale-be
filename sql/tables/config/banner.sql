drop table if exists config.banner;

create table config.banner
(
    id          bigserial primary key,
    "hinhAnh"   varchar(500),
    url         varchar(500),
    "thuTu"     int unique,
    "laMacDinh" boolean default false
)
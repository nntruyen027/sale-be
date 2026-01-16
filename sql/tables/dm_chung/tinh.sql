CREATE TABLE dm_chung.tinh
(
    id     BIGSERIAL PRIMARY KEY,
    ten    VARCHAR(120) NOT NULL,
    ghiChu VARCHAR(500)
);


select dm_chung.fn_lay_tat_ca_tinh('', null)
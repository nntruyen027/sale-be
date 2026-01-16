DROP VIEW IF EXISTS dm_chung.v_xa;


CREATE OR REPLACE VIEW dm_chung.v_xa AS
SELECT x.id,
       x.ten,
       x.ghiChu    as "ghiChu",
       x.tinhid,
       to_jsonb(t) as tinh
FROM dm_chung.xa x
         LEFT JOIN dm_chung.v_tinh t ON t.id = x.tinhid;


select dm_chung.fn_lay_tat_ca_xa();
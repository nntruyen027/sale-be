DROP VIEW IF EXISTS dm_chung.v_tinh;

CREATE OR REPLACE VIEW dm_chung.v_tinh AS
SELECT t.id,
       t.ten,
       t.ghichu as "ghiChu"
FROM dm_chung.tinh t;
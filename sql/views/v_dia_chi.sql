drop view if exists auth.v_dia_chi;

create view auth.v_dia_chi as
select a.id,
       a."chiTiet",
       a."dinhVi",
       a."isDefault",
       to_jsonb(x) as xa,
       a."userId"
from auth.dia_chi a
         left join dm_chung.v_xa x on x.id = a."xaId"

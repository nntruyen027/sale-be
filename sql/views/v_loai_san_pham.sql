drop view if exists product.v_loai;

create view product.v_loai as
select l.id,
       l.ten,
       l."hinhAnh",
       l."parentId",
       to_jsonb(vl) as "parent"
from product.loai l
         left join product.loai vl on l."parentId" = vl.id;
drop view if exists blog.v_chuyen_muc;

create view blog.v_chuyen_muc as
select l.id,
       l.ten,
       l.slug,
       l."parentId",
       to_jsonb(vl) as "parent"
from blog.chuyen_muc l
         left join blog.chuyen_muc vl on l."parentId" = vl.id;
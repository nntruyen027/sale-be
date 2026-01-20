drop view if exists product.v_bien_the;

create view product.v_bien_the as
select bt.*,
       jsonb_build_object(
               'id', sp.id,
               'ten', sp.ten,
               'moTa', sp."moTa",
               'hinhAnh', sp."hinhAnh",
               'ngayTao', sp."ngayTao",
               'loai', sp.loai
       ) as "sanPham"
from product.bien_the bt
         left join product.v_san_pham sp on bt."sanPhamId" = sp.id

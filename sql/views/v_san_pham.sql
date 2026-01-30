DROP VIEW IF EXISTS product.v_san_pham;

CREATE OR REPLACE VIEW product.v_san_pham AS
SELECT sp.id,
       sp.ten,
       sp."moTa",
       sp."hinhAnh",
       sp."loaiId",
       sp."ngayTao",

       COALESCE(
                       jsonb_agg(
                       jsonb_build_object(
                               'id', bt.id,
                               'sku', bt.sku,
                               'mauSac', bt."mauSac",
                               'kichCo', bt."kichCo",
                               'gia', bt.gia,
                               'hinhAnh', bt."hinhAnh",
                               'tonKho', bt."tonKho"
                       )
                                ) FILTER (WHERE bt.id IS NOT NULL),
                       '[]'::jsonb
       )                        AS "bienThe",
       jsonb_build_object(
               'id', l.id,
               'ten', l.ten,
               'hinhAnh', l."hinhAnh",
               'parent', l.parent
       )                        AS loai,
       coalesce(min(bt.gia), 0) as gia,
       sp.slug
FROM product.san_pham sp
         LEFT JOIN product.v_loai l ON sp."loaiId" = l.id
         LEFT JOIN product.bien_the bt ON bt."sanPhamId" = sp.id

GROUP BY sp.id,
         sp.ten,
         sp."moTa",
         sp."hinhAnh",
         sp."loaiId",
         sp."ngayTao",
         l.id,
         l.ten,
         l."hinhAnh",
         l."parent";

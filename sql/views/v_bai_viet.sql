DROP VIEW IF EXISTS blog.v_bai_viet;

CREATE VIEW blog.v_bai_viet AS
SELECT b.id,
       b."tieuDe",
       b.slug,
       b."tomTat",
       b."noiDung",
       b."tacGia",
       b."trangThai",
       b."luotXem",
       b."ngayTao",
       b."ngayCapNhat",
       b."nguoiDang",
       b."chuyenMucId",
       b."hinhAnh",

    /* ===== CHUYÊN MỤC ===== */
       CASE
           WHEN cm.id IS NULL THEN NULL
           ELSE jsonb_build_object(
                   'id', cm.id,
                   'ten', cm.ten,
                   'slug', cm.slug,
                   'parentId', cm."parentId"
                )
           END AS "chuyenMuc",

    /* ===== HASHTAGS ===== */
       COALESCE(
                       jsonb_agg(
                       jsonb_build_object(
                               'id', h.id,
                               'ten', h.ten,
                               'slug', h.slug
                       )
                                ) FILTER (WHERE h.id IS NOT NULL),
                       '[]'::jsonb
       )       AS hashtags

FROM blog.bai_viet b
         LEFT JOIN blog.chuyen_muc cm
                   ON cm.id = b."chuyenMucId"
         LEFT JOIN blog.bai_viet_hashtag bvh
                   ON b.id = bvh."baiVietId"
         LEFT JOIN blog.hashtag h
                   ON h.id = bvh."hashtagId"

GROUP BY b.id,
         cm.id,
         cm.ten,
         cm.slug,
         cm."parentId";

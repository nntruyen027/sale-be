DROP FUNCTION IF EXISTS blog.fn_lay_bai_viet_lien_quan;

CREATE FUNCTION blog.fn_lay_bai_viet_lien_quan(
    p_id BIGINT
)
    RETURNS JSONB
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_so_bai_viet BIGINT;
BEGIN
    -- 1️⃣ Lấy số bài liên quan (mặc định 5)
    SELECT COALESCE(ts."giaTri"::BIGINT, 5)
    INTO v_so_bai_viet
    FROM tham_so ts -- chỉnh schema nếu cần
    WHERE ts.khoa = 'SO_BAI_LIEN_QUAN';

    -- 2️⃣ Trả danh sách bài viết liên quan
    RETURN (SELECT COALESCE(jsonb_agg(to_jsonb(x)), '[]'::jsonb)
            FROM (SELECT v.*
                  FROM blog.v_bai_viet v
                  WHERE v.id <> p_id
                    AND v."trangThai" = 'PUBLIC'
                    AND v."chuyenMucId" = (SELECT bv."chuyenMucId"
                                           FROM blog.bai_viet bv
                                           WHERE bv.id = p_id)
                  ORDER BY v."ngayTao" DESC
                  LIMIT v_so_bai_viet) x);
END;
$$;

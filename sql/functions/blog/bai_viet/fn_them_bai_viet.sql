DROP FUNCTION IF EXISTS blog.fn_them_bai_viet;

CREATE FUNCTION blog.fn_them_bai_viet(
    p_tieu_de VARCHAR,
    p_slug VARCHAR,
    p_tom_tat TEXT,
    p_noi_dung TEXT,
    p_chuyen_muc_id BIGINT,
    p_tac_gia VARCHAR,
    p_trang_thai VARCHAR,
    p_nguoi_dang BIGINT
)
    RETURNS jsonb
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_bai_viet_id BIGINT;
    v_data        jsonb;
BEGIN
    if exists(select * from blog.bai_viet where lower(slug) = lower(p_slug)) then
        raise 'Slug % đã tồn tại', p_slug;
    end if;

    -- 1. Thêm bài viết
    INSERT INTO blog.bai_viet ("tieuDe",
                               slug,
                               "tomTat",
                               "noiDung",
                               "chuyenMucId",
                               "tacGia",
                               "trangThai",
                               "nguoiDang")
    VALUES (p_tieu_de,
            p_slug,
            p_tom_tat,
            p_noi_dung,
            p_chuyen_muc_id,
            p_tac_gia,
            COALESCE(p_trang_thai, 'DRAFT'),
            p_nguoi_dang)
    RETURNING id INTO v_bai_viet_id;


    select to_jsonb(r)
    into v_data
    from (select * from blog.v_bai_viet where id = v_bai_viet_id) r;

    return v_data;

END;
$$;

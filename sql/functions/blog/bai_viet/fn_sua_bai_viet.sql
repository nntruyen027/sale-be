DROP FUNCTION IF EXISTS blog.fn_sua_bai_viet;

CREATE FUNCTION blog.fn_sua_bai_viet(
    p_id BIGINT,
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
declare
    v_data jsonb;
BEGIN
    UPDATE blog.bai_viet
    SET "tieuDe"      = p_tieu_de,
        slug          = p_slug,
        "tomTat"      = p_tom_tat,
        "noiDung"     = p_noi_dung,
        "chuyenMucId" = p_chuyen_muc_id,
        "tacGia"      = p_tac_gia,
        "trangThai"   = UPPER(COALESCE(p_trang_thai, "trangThai")),
        "nguoiDang"   = p_nguoi_dang,
        "ngayCapNhat" = now()
    WHERE id = p_id;

    IF NOT FOUND THEN
        RAISE EXCEPTION 'Không tìm thấy bài viết id=%', p_id;
    END IF;

    select to_jsonb(r)
    into v_data
    from (select * from blog.v_bai_viet where id = p_id) r;

    return v_data;

EXCEPTION
    WHEN unique_violation THEN
        RAISE EXCEPTION 'Slug "% " đã tồn tại', p_slug
            USING ERRCODE = '23505';
END;
$$;

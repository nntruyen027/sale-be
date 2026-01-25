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
    p_nguoi_dang BIGINT,
    p_hinh_anh varchar
)
    RETURNS jsonb
    LANGUAGE plpgsql
AS
$$
declare
    v_data          jsonb;
    v_current_image varchar;
BEGIN
    select "hinhAnh" into v_current_image from blog.bai_viet where id = p_id;

    if v_current_image is not null or v_current_image != p_hinh_anh then
        delete from files where url = v_current_image;
    end if;

    UPDATE blog.bai_viet
    SET "tieuDe"      = p_tieu_de,
        slug          = p_slug,
        "tomTat"      = p_tom_tat,
        "noiDung"     = p_noi_dung,
        "chuyenMucId" = p_chuyen_muc_id,
        "tacGia"      = p_tac_gia,
        "trangThai"   = UPPER(COALESCE(p_trang_thai, "trangThai")),
        "nguoiDang"   = p_nguoi_dang,
        "hinhAnh"     = p_hinh_anh,
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

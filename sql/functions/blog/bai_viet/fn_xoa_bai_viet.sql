DROP FUNCTION IF EXISTS blog.fn_xoa_bai_viet;

CREATE FUNCTION blog.fn_xoa_bai_viet(
    p_id BIGINT
)
    RETURNS BOOLEAN
    LANGUAGE plpgsql
AS
$$
declare
    v_current_image varchar;
BEGIN
    select "hinhAnh" into v_current_image from blog.bai_viet where id = p_id;

    if v_current_image is not null then
        delete from files where url = v_current_image;
    end if;
    DELETE
    FROM blog.bai_viet
    WHERE id = p_id;

    IF NOT FOUND THEN
        RAISE EXCEPTION 'Không tìm thấy bài viết id=%', p_id;
    END IF;

    RETURN FOUND;
END;
$$;

DROP FUNCTION IF EXISTS blog.fn_xoa_bai_viet;

CREATE FUNCTION blog.fn_xoa_bai_viet(
    p_id BIGINT
)
    RETURNS BOOLEAN
    LANGUAGE plpgsql
AS
$$
BEGIN
    DELETE
    FROM blog.bai_viet
    WHERE id = p_id;

    IF NOT FOUND THEN
        RAISE EXCEPTION 'Không tìm thấy bài viết id=%', p_id;
    END IF;

    RETURN FOUND;
END;
$$;

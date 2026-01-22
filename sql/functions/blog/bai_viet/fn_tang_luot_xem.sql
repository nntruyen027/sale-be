DROP FUNCTION IF EXISTS blog.fn_tang_luot_xem;

CREATE FUNCTION blog.fn_tang_luot_xem(
    p_id BIGINT
)
    RETURNS BIGINT
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_luot_xem BIGINT;
BEGIN
    UPDATE blog.bai_viet
    SET "luotXem" = COALESCE("luotXem", 0) + 1
    WHERE id = p_id
      AND "trangThai" <> 'DELETED'
    RETURNING "luotXem" INTO v_luot_xem;

    IF NOT FOUND THEN
        RAISE EXCEPTION 'Không tìm thấy bài viết id=%', p_id;
    END IF;

    RETURN v_luot_xem;
END;
$$;

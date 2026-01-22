DROP FUNCTION IF EXISTS blog.fn_lay_bai_viet;

CREATE FUNCTION blog.fn_lay_bai_viet(
    p_id BIGINT
)
    RETURNS JSONB
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_result JSONB;
BEGIN
    select (r)
    into v_result
    from (select *
          from blog.v_bai_viet
          where id = p_id) r;

    IF v_result IS NULL THEN
        RAISE EXCEPTION 'Không tìm thấy bài viết id=%', p_id;
    END IF;

    RETURN v_result;
END;
$$;

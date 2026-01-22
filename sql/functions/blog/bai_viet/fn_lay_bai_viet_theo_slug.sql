DROP FUNCTION IF EXISTS blog.fn_lay_bai_viet_theo_slug;

CREATE FUNCTION blog.fn_lay_bai_viet_theo_slug(
    p_slug varchar
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
          where lower(unaccent(slug)) = lower(unaccent(p_slug))) r;

    IF v_result IS NULL THEN
        RAISE EXCEPTION 'Không tìm thấy bài viết slug=%', p_slug;
    END IF;

    RETURN v_result;
END;
$$;

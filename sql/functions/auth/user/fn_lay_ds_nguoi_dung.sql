DROP FUNCTION IF EXISTS auth.fn_lay_ds_nguoi_dung;

CREATE FUNCTION auth.fn_lay_ds_nguoi_dung(
    p_search VARCHAR DEFAULT NULL,
    p_page INT DEFAULT 1,
    p_limit INT DEFAULT 10
)
    RETURNS JSONB
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_offset      INT := (p_page - 1) * p_limit;
    v_total       BIGINT;
    v_total_pages INT;
    v_data        JSONB;
BEGIN
    -- Tổng số bản ghi
    SELECT COUNT(*)
    INTO v_total
    FROM auth.users u
    WHERE (
              p_search IS NULL OR p_search = ''
                  OR unaccent(lower(u."hoTen")) LIKE '%' || unaccent(lower(p_search)) || '%'
                  OR u.email ILIKE '%' || p_search || '%'
                  OR u.username ILIKE '%' || p_search || '%'
              );

    v_total_pages := util.fn_tinh_total_page(v_total, p_limit);

    -- Data
    SELECT COALESCE(jsonb_agg(to_jsonb(u)), '[]'::jsonb)
    INTO v_data
    FROM auth.v_users_full u
    WHERE (
              p_search IS NULL OR p_search = ''
                  OR unaccent(lower(u."hoTen")) LIKE '%' || unaccent(lower(p_search)) || '%'
                  OR u.email ILIKE '%' || p_search || '%'
                  OR u.username ILIKE '%' || p_search || '%'
              )
    LIMIT p_limit OFFSET v_offset;

    RETURN jsonb_build_object(
            'data', v_data,
            'page', p_page,
            'size', p_limit,
            'totalPages', v_total_pages,
            'totalElements', v_total
           );
END;
$$;

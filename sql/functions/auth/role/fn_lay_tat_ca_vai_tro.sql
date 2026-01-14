DROP FUNCTION IF EXISTS auth.fn_lay_tat_ca_vai_tro;

CREATE OR REPLACE FUNCTION auth.fn_lay_tat_ca_vai_tro(
    p_search VARCHAR(100),
    p_page   INT default 1,
    p_size   INT default 10
)
    RETURNS json
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_offset        INT := (p_page - 1) * p_size;
    v_total         BIGINT;
    v_total_pages   INT;
    v_data          json;
BEGIN
    -- Tổng số bản ghi
    SELECT COUNT(*)
    INTO v_total
    FROM auth.v_role r
    WHERE
        p_search IS NULL
       OR p_search = ''
       OR unaccent(lower(r.name)) LIKE '%' || unaccent(lower(p_search)) || '%'
       OR unaccent(lower(r.code)) LIKE '%' || unaccent(lower(p_search)) || '%';

    v_total_pages := CEIL(v_total::NUMERIC / p_size);

    -- Dữ liệu trang hiện tại
    SELECT COALESCE(json_agg(to_jsonb(r)), '[]'::json)
    INTO v_data
    FROM (
             SELECT *
             FROM auth.v_role r
             WHERE
                 p_search IS NULL
                OR p_search = ''
                OR unaccent(lower(r.name)) LIKE '%' || unaccent(lower(p_search)) || '%'
                OR unaccent(lower(r.code)) LIKE '%' || unaccent(lower(p_search)) || '%'
             ORDER BY r.name
             OFFSET v_offset
                 LIMIT p_size
         ) r;

    -- Trả đúng format PageResponse
    RETURN json_build_object(
            'data', v_data,
            'page', p_page,
            'size', p_size,
            'totalPages', v_total_pages,
            'totalElements', v_total
           );
END;
$$;

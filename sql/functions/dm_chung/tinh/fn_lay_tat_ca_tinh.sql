DROP FUNCTION IF EXISTS dm_chung.fn_lay_tat_ca_tinh;
CREATE OR REPLACE FUNCTION dm_chung.fn_lay_tat_ca_tinh(
    p_search VARCHAR(500),
    p_page INT DEFAULT 1,
    p_limit INT DEFAULT 10
)
    RETURNS jsonb
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_offset      INT := (p_page - 1) * p_limit;
    v_total       BIGINT;
    v_total_pages INT;
    v_result      jsonb;
BEGIN
    SELECT count(*)
    INTO v_total
    FROM dm_chung.v_tinh t
    WHERE p_search IS NULL
       OR p_search = ''
       OR unaccent(lower(t.ten)) LIKE '%' || unaccent(lower(p_search)) || '%';

    v_total_pages := util.fn_tinh_total_page(v_total, p_limit);

    SELECT COALESCE(jsonb_agg(to_jsonb(r)), '[]'::jsonb)
    INTO v_result
    FROM (SELECT *
          FROM dm_chung.v_tinh t
          WHERE p_search IS NULL
             OR p_search = ''
             OR unaccent(lower(t.ten)) LIKE '%' || unaccent(lower(p_search)) || '%'
          ORDER BY t.ten
          OFFSET v_offset LIMIT p_limit) r;

    RETURN jsonb_build_object(
            'data', v_result,
            'page', p_page,
            'size', p_limit,
            'totalPages', v_total_pages,
            'totalElements', v_total
           );
END;
$$;

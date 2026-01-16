DROP FUNCTION IF EXISTS auth.fn_lay_tat_ca_quyen;

CREATE OR REPLACE FUNCTION auth.fn_lay_tat_ca_quyen(
    p_search VARCHAR(100),
    p_page INT DEFAULT 1,
    p_limit INT DEFAULT 10
)
    RETURNS jsonb
    LANGUAGE plpgsql
AS
$$
DECLARE
    result       jsonb;
    v_total      int;
    v_offset     int;
    v_total_page int;
BEGIN
    IF p_page < 1 THEN p_page := 1; END IF;
    IF p_limit < 1 THEN p_limit := 10; END IF;

    v_offset := (p_page - 1) * p_limit;

    SELECT count(*)
    INTO v_total
    FROM auth.v_permission p
    WHERE p_search IS NULL
       OR p_search = ''
       OR unaccent(lower(p.code)) LIKE '%' || unaccent(lower(p_search)) || '%';

    v_total_page := util.fn_tinh_total_page(
            p_total_records := v_total,
            p_page_size := p_limit
                    );

    SELECT COALESCE(
                   jsonb_agg(p),
                   '[]'::jsonb
           )
    INTO result
    FROM (SELECT *
          FROM auth.v_permission p
          WHERE p_search IS NULL
             OR p_search = ''
             OR unaccent(lower(p.code)) LIKE '%' || unaccent(lower(p_search)) || '%'
          ORDER BY p.code
          OFFSET v_offset LIMIT p_limit) p;

    RETURN jsonb_build_object(
            'data', result,
            'page', p_page,
            'size', p_limit,
            'totalPages', v_total_page,
            'totalElements', v_total
           );
END;
$$;


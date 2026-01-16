DROP FUNCTION IF EXISTS dm_chung.fn_lay_tat_ca_xa;
CREATE OR REPLACE FUNCTION dm_chung.fn_lay_tat_ca_xa(
    p_search VARCHAR(500) default null,
    p_tinh_id BIGINT default null,
    p_page INT DEFAULT 1,
    p_limit INT DEFAULT 10
)
    RETURNS json
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_data        json;
    v_total       BIGINT;
    v_total_pages INT;
    v_offset      INT;
BEGIN
    IF p_page < 1 THEN p_page := 1; END IF;
    IF p_limit <= 0 THEN p_limit := 10; END IF;

    v_offset := (p_page - 1) * p_limit;

    SELECT count(*)
    INTO v_total
    FROM dm_chung.v_xa x
    WHERE (p_search IS NULL OR p_search = ''
        OR unaccent(lower(x.ten)) LIKE '%' || unaccent(lower(p_search)) || '%')
      AND (p_tinh_id IS NULL OR x.tinhid = p_tinh_id);

    v_total_pages := util.fn_tinh_total_page(v_total, p_limit);

    SELECT COALESCE(json_agg(to_jsonb(r)), '[]'::json)
    INTO v_data
    FROM (SELECT *
          FROM dm_chung.v_xa x
          WHERE (p_search IS NULL OR p_search = ''
              OR unaccent(lower(x.ten)) LIKE '%' || unaccent(lower(p_search)) || '%')
            AND (p_tinh_id IS NULL OR x.tinhid = p_tinh_id)
          ORDER BY x.tinhid, x.ten
          OFFSET v_offset LIMIT p_limit) r;

    RETURN json_build_object(
            'data', v_data,
            'page', p_page,
            'size', p_limit,
            'totalPages', v_total_pages,
            'totalElements', v_total
           );
END;
$$;


select dm_chung.fn_lay_tat_ca_xa()
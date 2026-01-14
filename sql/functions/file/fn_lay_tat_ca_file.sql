DROP FUNCTION IF EXISTS fn_lay_tat_ca_file;

CREATE OR REPLACE FUNCTION fn_lay_tat_ca_file(
    p_search VARCHAR,
    p_page INT DEFAULT 1,
    p_limit INT DEFAULT 10
)
    RETURNS jsonb
    LANGUAGE plpgsql
AS
$$
declare
    v_data        jsonb;
    v_total       int;
    v_total_pages int;
BEGIN
    select count(*)
    into v_total
    from v_file f
    WHERE p_search IS NULL
       OR p_search = ''
       OR (
        public.unaccent(lower(f.file_name)) LIKE '%' || public.unaccent(lower(p_search)) || '%'
        );

    v_total_pages := util.fn_tinh_total_page(p_total_records := v_total, p_page_size := p_limit);


    SELECT json_agg(to_jsonb(f))
    FROM v_file f
    WHERE p_search IS NULL
       OR p_search = ''
       OR (
        public.unaccent(lower(f.file_name)) LIKE '%' || public.unaccent(lower(p_search)) || '%'
        )
    ORDER BY f.file_name
    OFFSET (p_page - 1) * p_limit LIMIT p_limit;

    RETURN json_build_object(
            'data', v_data,
            'page', p_page,
            'size', p_limit,
            'totalPages', v_total_pages,
            'totalElements', v_total
           );
END;
$$;

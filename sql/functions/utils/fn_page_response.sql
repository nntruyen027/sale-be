drop function if exists util.fn_page_response;

CREATE OR REPLACE FUNCTION util.fn_page_response(
    p_data JSONB,
    p_page INT,
    p_limit INT,
    p_total BIGINT
)
    RETURNS JSON
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_total_pages INT;
BEGIN
    v_total_pages := CEIL(p_total::NUMERIC / p_limit);

    RETURN json_build_object(
            'data', p_data,
            'page', p_page,
            'size', p_limit,
            'totalPages', v_total_pages,
            'totalElements', p_total
           );
END;
$$;

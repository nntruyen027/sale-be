drop function if exists util.fn_tinh_total_page;

CREATE OR REPLACE FUNCTION util.fn_tinh_total_page(
    p_total_records BIGINT,
    p_page_size INT
)
    RETURNS INT
    LANGUAGE plpgsql
    IMMUTABLE
AS
$$
BEGIN
    IF p_page_size IS NULL OR p_page_size <= 0 THEN
        RAISE EXCEPTION 'page_size phải > 0';
    END IF;

    IF p_total_records IS NULL OR p_total_records = 0 THEN
        RETURN 0;
    END IF;

    RETURN CEILING(p_total_records::NUMERIC / p_page_size);
END;
$$;

DROP FUNCTION IF EXISTS auth.fn_lay_permission_theo_code;

CREATE OR REPLACE FUNCTION auth.fn_lay_permission_theo_code(
    p_code  VARCHAR(100),
    p_page  INT DEFAULT 1,
    p_size  INT DEFAULT 10
)
    RETURNS json
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_offset        INT := (p_page - 1) * p_size;
    v_total         BIGINT;
    v_data          json;
BEGIN
    -- Kiểm tra tồn tại
    SELECT COUNT(*)
    INTO v_total
    FROM auth.permissions
    WHERE code = p_code;

    IF v_total = 0 THEN
        RAISE EXCEPTION 'Permission với mã % không tồn tại.', p_code;
    END IF;

    -- Lấy data
    SELECT COALESCE(json_agg(to_jsonb(p)), '[]'::json)
    INTO v_data
    FROM (
             SELECT *
             FROM auth.v_permission
             WHERE code = p_code
             OFFSET v_offset
                 LIMIT p_size
         ) p;

    -- Trả JSON phân trang
    RETURN json_build_object(
            'data', v_data,
            'page', p_page,
            'size', p_size,
            'totalPages', CEIL(v_total::numeric / p_size),
            'totalElements', v_total
           );
END;
$$;

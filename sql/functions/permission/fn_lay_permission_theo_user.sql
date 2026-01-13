DROP FUNCTION IF EXISTS auth.fn_lay_permission_theo_user;

CREATE OR REPLACE FUNCTION auth.fn_lay_permission_theo_user(
    p_user_id BIGINT,
    p_page    INT DEFAULT 1,
    p_size    INT DEFAULT 10
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
    -- Đếm tổng permission DISTINCT
    SELECT COUNT(DISTINCT p.code)
    INTO v_total
    FROM auth.user_roles ur
             JOIN auth.role_permissions rp ON ur.role_id = rp.role_id
             JOIN auth.permissions p ON rp.permission_id = p.id
    WHERE ur.user_id = p_user_id;

    -- Lấy data phân trang
    SELECT COALESCE(json_agg(code), '[]'::json)
    INTO v_data
    FROM (
             SELECT DISTINCT p.code
             FROM auth.user_roles ur
                      JOIN auth.role_permissions rp ON ur.role_id = rp.role_id
                      JOIN auth.permissions p ON rp.permission_id = p.id
             WHERE ur.user_id = p_user_id
             ORDER BY p.code
             OFFSET v_offset
                 LIMIT p_size
         ) t;

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

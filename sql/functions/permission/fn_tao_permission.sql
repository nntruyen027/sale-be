DROP FUNCTION IF EXISTS auth.fn_tao_permission;

CREATE OR REPLACE FUNCTION auth.fn_tao_permission(
    p_code VARCHAR(100)
)
    RETURNS json
    LANGUAGE plpgsql
AS
$$
DECLARE
    result json;
BEGIN
    IF EXISTS (
        SELECT 1 FROM auth.permissions WHERE code = p_code
    ) THEN
        RAISE EXCEPTION 'Permission với mã % đã tồn tại', p_code;
    END IF;

    INSERT INTO auth.permissions(code)
    VALUES (p_code);

    SELECT to_jsonb(v)
    INTO result
    FROM auth.v_permission v
    WHERE v.code = p_code
    LIMIT 1;

    RETURN coalesce(result, '{}'::json);
END;
$$;

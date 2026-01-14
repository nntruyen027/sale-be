DROP FUNCTION IF EXISTS auth.fn_lay_nguoi_dung_theo_id;

CREATE OR REPLACE FUNCTION auth.fn_lay_nguoi_dung_theo_id(
    p_id BIGINT
)
    RETURNS JSONB
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_result JSONB;
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM auth.users
        WHERE id = p_id
    ) THEN
        RAISE EXCEPTION 'Người dùng với id % không tồn tại', p_id;
    END IF;

    SELECT to_jsonb(u)
    INTO v_result
    FROM auth.v_users_full u
    WHERE u.id = p_id
    LIMIT 1;

    RETURN v_result;
END;
$$;

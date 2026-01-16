DROP FUNCTION IF EXISTS auth.fn_cap_nhat_nguoi_dung;

CREATE OR REPLACE FUNCTION auth.fn_cap_nhat_nguoi_dung(
    p_user_id BIGINT,
    p_ho_ten VARCHAR(500),
    p_avatar VARCHAR(500),
    p_email VARCHAR(500),
    p_is_active BOOLEAN DEFAULT true
)
    RETURNS JSONB
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_result JSONB;
BEGIN
    IF NOT EXISTS (SELECT 1 FROM auth.users WHERE id = p_user_id) THEN
        RAISE EXCEPTION 'User ID % không tồn tại', p_user_id;
    END IF;

    UPDATE auth.users
    SET "hoTen"    = p_ho_ten,
        avatar     = p_avatar,
        email      = p_email,
        "isActive" = p_is_active
    WHERE id = p_user_id;

    -- Trả về user dạng JSON
    SELECT to_jsonb(v)
    INTO v_result
    FROM auth.v_users_full v
    WHERE v.id = p_user_id
    LIMIT 1;

    RETURN v_result;
END;
$$;

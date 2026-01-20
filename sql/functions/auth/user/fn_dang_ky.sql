DROP FUNCTION IF EXISTS auth.fn_dang_ky;

CREATE FUNCTION auth.fn_dang_ky(
    p_username varchar,
    p_password varchar,
    p_ho_ten varchar,
    p_email VARCHAR
)
    RETURNS jsonb
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_user_id BIGINT;
    v_data    jsonb;
BEGIN
    IF EXISTS (SELECT 1 FROM auth.users WHERE username = p_username) THEN
        RAISE EXCEPTION 'Username % đã tồn tại', p_username;
    END IF;

    INSERT INTO auth.users(username, password, "hoTen", email)
    VALUES (p_username, p_password, p_ho_ten, p_email)
    RETURNING id INTO v_user_id;

    SELECT to_jsonb(u)
    INTO v_data
    FROM auth.v_users_full u
    WHERE u.id = v_user_id
    LIMIT 1;

    RETURN v_data;

END;
$$;

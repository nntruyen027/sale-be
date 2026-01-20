DROP FUNCTION IF EXISTS auth.fn_dang_ky;

CREATE FUNCTION auth.fn_dang_ky(
    p_id bigint,
    p_username TEXT,
    p_password TEXT,
    p_ho_ten TEXT,
    p_email VARCHAR
)
    RETURNS jsonb
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_data jsonb;
BEGIN
    if p_password is null or p_password = '' then
        update auth.users
        set "hoTen" = p_ho_ten,
            email   = p_email
        where id = p_id;
    end if;

    if not (p_password is null or p_password = '') then
        update auth.users
        set "hoTen"  = p_ho_ten,
            password = p_password,
            email    = p_email
        where id = p_id;
    end if;

    SELECT to_jsonb(u)
    INTO v_data
    FROM auth.v_users_full u
    WHERE u.id = p_id
    LIMIT 1;

    RETURN v_data;

END;
$$;

DROP FUNCTION IF EXISTS auth.fn_sua_nguoi_dung;

CREATE FUNCTION auth.fn_sua_nguoi_dung(
    p_id bigint,
    p_username varchar,
    p_password varchar,
    p_ho_ten varchar,
    p_email varchar
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

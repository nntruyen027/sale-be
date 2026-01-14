DROP FUNCTION IF EXISTS auth.fn_lay_quan_tri_theo_username;

CREATE OR REPLACE FUNCTION auth.fn_lay_quan_tri_theo_username(
    p_username VARCHAR
)
    RETURNS jsonb
AS
$$
declare
    v_result jsonb;
BEGIN
    IF NOT EXISTS (select 1 from auth.users u where u.username = p_username) THEN
        RAISE EXCEPTION 'Quản trị viên với tên đăng nhập: % không tồn tại.', p_username;
    END IF;

    SELECT to_jsonb(u)
    into v_result
    FROM auth.v_users_full u
    WHERE u.username = p_username
    LIMIT 1;

    return v_result;
END;
$$ LANGUAGE plpgsql;

DROP FUNCTION IF EXISTS auth.fn_lay_nguoi_dung_theo_username;

CREATE OR REPLACE FUNCTION auth.fn_lay_nguoi_dung_theo_username(
    p_username VARCHAR
)
    RETURNS jsonb
AS
$$
    declare
        v_re jsonb;
BEGIN

        SELECT to_jsonb(u) into v_re
        FROM auth.v_users_full u
        WHERE username = p_username
        LIMIT 1;

        return v_re;
END;
$$ LANGUAGE plpgsql;

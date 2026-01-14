DROP FUNCTION IF EXISTS auth.fn_lay_thong_tin_auth_theo_username;

CREATE OR REPLACE FUNCTION auth.fn_lay_thong_tin_auth_theo_username(
    p_username VARCHAR
)
    RETURNS jsonb
AS
$$
    declare
        v_re jsonb;
BEGIN
    SELECT to_jsonb(u) into v_re FROM auth.v_thong_tin_auth u WHERE u.username = p_username LIMIT 1;
    return v_re;
end;

$$ LANGUAGE plpgsql;
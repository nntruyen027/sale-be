DROP FUNCTION IF EXISTS auth.fn_lay_thong_tin_auth_theo_id;

CREATE OR REPLACE FUNCTION auth.fn_lay_thong_tin_auth_theo_id(
    p_id BIGINT
)
    RETURNS jsonb
AS
$$
    declare
        v_re jsonb;
BEGIN
    SELECT to_jsonb(u) into v_re FROM auth.v_thong_tin_auth u WHERE u.id = p_id LIMIT 1;
    return v_re;
end;

$$ LANGUAGE plpgsql;


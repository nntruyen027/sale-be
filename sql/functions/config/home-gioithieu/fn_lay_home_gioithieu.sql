DROP FUNCTION IF EXISTS config.fn_lay_home_gioithieu;

CREATE FUNCTION config.fn_lay_home_gioithieu(
)
    RETURNS jsonb
    LANGUAGE plpgsql
AS
$$
declare
    v_data jsonb;
BEGIN
    select to_jsonb(r)
    into v_data
    from (select *
          from config.thong_tin_he_thong
          where ten = 'home_gioithieu'
          limit 1) r;

    RETURN v_data;
END;
$$;

DROP FUNCTION IF EXISTS config.fn_lay_home_chucnang;

CREATE FUNCTION config.fn_lay_home_chucnang(
)
    RETURNS jsonb
    LANGUAGE plpgsql
AS
$$
declare
    v_data jsonb;
BEGIN
    select to_jsonb(r."noiDung")
    into v_data
    from (select *
          from config.thong_tin_he_thong
          where ten = 'home_chucnang'
          limit 1) r;

    RETURN v_data;
END;
$$;

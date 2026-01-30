DROP FUNCTION IF EXISTS config.fn_lay_chung_header;

CREATE FUNCTION config.fn_lay_chung_header(
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
          where ten = 'chung_header'
          limit 1) r;

    RETURN v_data;
END;
$$;

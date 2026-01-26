drop function if exists config.fn_lay_ds_banner;

create function config.fn_lay_ds_banner(
)
    returns jsonb
as
$$
declare
    v_data jsonb;

begin
    select jsonb_agg(to_jsonb(r))
    into v_data
    from (select *
          from config.thong_tin_he_thong l
          where l.ten = 'banner'
          order by "thuTu") r;
    return coalesce(v_data, '[]'::jsonb);
end;
$$ language plpgsql;
drop function if exists auth.fn_lay_ds_dia_chi_ca_nhan;

create function auth.fn_lay_ds_dia_chi_ca_nhan(
    p_user_id bigint
)
    returns jsonb
as
$$
declare
    v_data jsonb;
begin
    select to_jsonb(a) into v_data from auth.v_dia_chi a where a."userId" = p_user_id;

    return v_data;

end;
$$ language plpgsql;
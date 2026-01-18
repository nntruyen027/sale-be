drop function if exists auth.fn_sua_dia_chi_ca_nhan;

create function auth.fn_sua_dia_chi_ca_nhan(
    p_id bigint,
    p_xa_id bigint,
    p_chi_tiet varchar,
    p_user_id bigint,
    p_dinh_vi varchar default null,
    p_is_default boolean default false
)
    returns jsonb
as
$$
declare
    v_data jsonb;
begin
    update auth.dia_chi
    set "xaId"      = p_xa_id,
        "chiTiet"   = p_chi_tiet,
        "dinhVi"    = p_dinh_vi,
        "isDefault" = p_is_default
    where "userId" = p_user_id
      and id = p_id;


    select to_jsonb(a) into v_data from auth.v_dia_chi a where a.id = p_id;

    return v_data;

end;
$$ language plpgsql;